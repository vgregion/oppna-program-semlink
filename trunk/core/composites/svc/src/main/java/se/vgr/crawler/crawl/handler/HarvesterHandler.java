package se.vgr.crawler.crawl.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.MessageFormat;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;

import org.ontoware.rdf2go.RDF2Go;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.ModelSet;
import org.ontoware.rdf2go.model.node.URI;

import org.semanticdesktop.aperture.accessor.DataObject;
import org.semanticdesktop.aperture.accessor.FileDataObject;
import org.semanticdesktop.aperture.accessor.RDFContainerFactory;
import org.semanticdesktop.aperture.crawler.Crawler;
import org.semanticdesktop.aperture.crawler.CrawlerHandler;
import org.semanticdesktop.aperture.crawler.ExitCode;
import org.semanticdesktop.aperture.extractor.Extractor;
import org.semanticdesktop.aperture.extractor.ExtractorException;
import org.semanticdesktop.aperture.extractor.ExtractorFactory;
import org.semanticdesktop.aperture.extractor.ExtractorRegistry;

import org.semanticdesktop.aperture.extractor.FileExtractor;
import org.semanticdesktop.aperture.extractor.FileExtractorFactory;

import org.semanticdesktop.aperture.extractor.util.ThreadedExtractorWrapper;
import org.semanticdesktop.aperture.mime.identifier.MimeTypeIdentifier;
import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;
import org.semanticdesktop.aperture.rdf.RDFContainer;
import org.semanticdesktop.aperture.rdf.impl.RDFContainerImpl;
import org.semanticdesktop.aperture.subcrawler.SubCrawler;
import org.semanticdesktop.aperture.subcrawler.SubCrawlerException;
import org.semanticdesktop.aperture.subcrawler.SubCrawlerFactory;
import org.semanticdesktop.aperture.subcrawler.SubCrawlerRegistry;
import org.semanticdesktop.aperture.subcrawler.impl.DefaultSubCrawlerRegistry;
import org.semanticdesktop.aperture.util.IOUtil;

import org.semanticdesktop.aperture.vocabulary.NIE;

import se.vgr.crawler.index.IndexConstants;
import se.vgr.crawler.index.IndexHelper;
import se.vgr.crawler.index.LuceneIndex;
import se.vgr.crawler.vocabulary.DC;
import se.vgr.crawler.vocabulary.SKOS;
import se.vgr.crawler.vocabulary.VGR;
import se.vgr.crawler.client.KeyWordClient;
import se.vgr.crawler.dao.LinkDao;
import se.vgr.crawler.entity.Link;
import se.vgr.crawler.extractor.CustomExtractorFactory;
import se.vgr.metaservice.schema.node.v2.NodeListType;
import se.vgr.metaservice.schema.node.v2.NodeType;
import se.vgr.metaservice.schema.node.v2.SynonymsListType;

/**
 * <p>A crawler handler</p>
 * @author Johan Säll Larsson
 */

public class HarvesterHandler implements CrawlerHandler, RDFContainerFactory {
	
	private static Logger log = Logger.getLogger(HarvesterHandler.class);
	
	private LuceneIndex luceneIndex;
    protected int nrObjects;
    private String currentURL;
    private ExitCode exitCode;
    private static MimeTypeIdentifier mimeTypeIdentifier = new MagicMimeTypeIdentifier();
    private ExtractorRegistry extractorRegistry;
    private ModelSet modelSet;
    private LinkDao linkDao;
    private KeyWordClient keyWordClient;

    private SubCrawlerRegistry subCrawlerRegistry = new DefaultSubCrawlerRegistry();
    
    public HarvesterHandler() {}
    
    /**
     * Calls garbage collector from time to time
     * @param i
     */
    
    private void garbageCollect(int i){
    	if ( nrObjects % 300 == 0 ) {
            System.gc();
        }
    }
    
    /**
     * Process the data object
     * @param dataCrawler
     * @param object
     */
    
    private void processDataObject(Crawler dataCrawler, DataObject object) {
        
    	garbageCollect(nrObjects++);

        if (object instanceof FileDataObject) {

            process((FileDataObject) object, dataCrawler);
            extract((FileDataObject) object);
        }
		
        object.dispose();
        
    }
    

	private void extract(FileDataObject object){
    	
    	RDFContainer container = object.getMetadata();       
        
    	if ( keyWordClient != null ) {
    		
	    	Collection<?> subjects = container.getAll(DC.subject);
	    	
	    	String textContent = "";
	    	String textTitle = "";
	    	
	    	for ( Object obj : subjects ) {
	    		textContent += obj.toString() + " ";
	    	}
	    	
	    	Collection<?> titles = container.getAll(DC.title);
	    	for ( Object obj : titles ) {
	    		textTitle = obj.toString();
	    	}
	    	
	    	List<NodeType> response = keyWordClient.getKeyWords(textTitle, textContent);
	    	
	    	if ( response != null ) {
		    	
	    		for ( NodeType node : response ) {
	    			
	    			container.add(SKOS.prefLabel, node.getName());
	    			
	    			// add synonyms
	    			SynonymsListType synonymListType = node.getSynonyms();
	    			List<String> synonyms = synonymListType.getSynonym();
	    			
	    			for ( String synonym : synonyms ) {
	    				container.add(SKOS.altLabel, synonym);
	    			}
	    			
	    			// add parents
	    			NodeListType parentList = node.getParents();
	    			List<NodeType> parents = parentList.getNode();
	    			
	    			for ( NodeType parent : parents ) {
	    				container.add(VGR.structure, parent.getSourceId());
	    			}
	    			
	    		}
	    		
	    	}
    	
    	}
		
		if ( luceneIndex != null ) {
			
			Model model = container.getModel();
			
			try {
				IndexHelper.index(luceneIndex, object.getID(), model, false);
			} catch ( NullPointerException e ) {
				e.printStackTrace();
			}
			
		}
		
    }
	
	
    protected void process(FileDataObject object, Crawler crawler) {

        URI id = object.getID();
        
        InputStream stream = getInputStream(id, object);
        String mime = getMimeType(id, stream);
        
        if (mime != null) {
        	
        	try {
				stream.reset();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
        	RDFContainer metadata = object.getMetadata();
            metadata.add(NIE.mimeType, mime);
            
            if ( applyExtractor(id, stream, mime, metadata) ) {    
            	
            	return;
            	
            } else if ( applyFileExtractor(object, id, mime, metadata) ) {
            	
            	return;
            	
            } else {
            	
            	applySubCrawler(id, stream, mime, object, crawler);
            	
            }
            
        } else {
        	log.warn("Mimetype is null");
        }
        
    }
    
    private InputStream getInputStream(URI id, FileDataObject object){
    	
    	int minimumArrayLength = mimeTypeIdentifier.getMinArrayLength();
        InputStream retval = null;
        
        try {
        	retval = object.getContent();
        	retval.mark(minimumArrayLength + 10);
		} catch (IOException e) {
			log.info(MessageFormat.format("Unable to receive an input stream {0}", id));
		}
		
		return retval;
		
    }
    
    private String getMimeType(URI id, InputStream contentStream){
    	
    	int minimumArrayLength = mimeTypeIdentifier.getMinArrayLength();
        String retval = null;
		
        try {
	        byte[] bytes = IOUtil.readBytes(contentStream, minimumArrayLength);
	        retval = mimeTypeIdentifier.identify(bytes, null, id);			
		} catch (IOException e) {
			log.info(MessageFormat.format("Unable to identify mime type at {0}", id));;
		}

        return retval;
    }


	private boolean applyExtractor(URI id, InputStream contentStream, String mimeType, RDFContainer metadata) {

        Set<?> extractors = extractorRegistry.getExtractorFactories(mimeType);
        
        if (!extractors.isEmpty()) {
        	
            ExtractorFactory factory = (ExtractorFactory) extractors.iterator().next();
            Extractor extractor = factory.get();
            ThreadedExtractorWrapper threadedExtractor = new ThreadedExtractorWrapper(extractor);
        	
            Charset cs = null;
        	
            try{
        		cs = Charset.forName(metadata.getString(NIE.characterSet));
        	} catch (Exception e){}
            	
            try {
				threadedExtractor.extract(id, contentStream, cs, mimeType, metadata);
			} catch (ExtractorException e) {
				e.printStackTrace();
			}
        
		    return true;
		    
        } else {
            return false;
        }
            
    }

    private boolean applyFileExtractor(FileDataObject object, URI id, String mimeType, RDFContainer metadata) {
        
    	Set<?> fileextractors = extractorRegistry.getFileExtractorFactories(mimeType);
    	 
    	if ( !fileextractors.isEmpty() ) {
            
    		FileExtractorFactory factory = (FileExtractorFactory) fileextractors.iterator().next();
            FileExtractor extractor = factory.get();
            File originalFile = object.getFile();
            
            if ( originalFile != null ) {
            	
                try {
					extractor.extract(id, originalFile, null, mimeType, metadata);
				} catch (ExtractorException e) {
					e.printStackTrace();
				}
				
                return true;
                
            } else {
                
            	File tempFile = null;
				
                try {
					tempFile = object.downloadContent();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

                try {
                	
                	if ( tempFile != null ) {
                		extractor.extract(id, tempFile, null, mimeType, metadata);
                		return true;
                	}
                	
                } catch (ExtractorException e) {
					e.printStackTrace();
				} finally {
                    
					if (tempFile != null) {
                        tempFile.delete();
                    }
					
                }
            }
            
        }
        	
        return false;
        
    }

	private boolean applySubCrawler(URI id, InputStream contentStream, String mimeType, DataObject object, Crawler crawler) {
        
    	Set<?> subCrawlers = subCrawlerRegistry.get(mimeType);
    	
        if (!subCrawlers.isEmpty()) {
            
        	SubCrawlerFactory factory = (SubCrawlerFactory) subCrawlers.iterator().next();
            SubCrawler subCrawler = factory.get();

            try {
				crawler.runSubCrawler(subCrawler, object, contentStream, null, mimeType);
			} catch (SubCrawlerException e) {
				e.printStackTrace();
			}
			
            return true;
            
        } else {
            return false;
        }
        
    }

    /**
     * <p>Clears all information within the database and Lucene index</p>
     * FIXME This is being called by depth.. which means that it will clear when the depth changes!
     * @param id
     */
    
    private void clear(String id) {
    	
		try {	
			URI uri = modelSet.createURI(id);
			modelSet.removeModel(uri);
		} catch (Exception e) {
			log.error(MessageFormat.format("Unable to clear context {0}, {1}", id, e));
		}


		try {
			luceneIndex.delete(new Term(IndexConstants.UID_FIELD, id));
		} catch (IOException e) {
			log.error(MessageFormat.format("Unable to delete {0}, {1}", id, e));
		}

    }
    
    /**
     * <p>Sets the links to inactive</p>
     * @param url
     */
    
    private void disableLink(String url) {
    	
    	Link link = new Link();
    	link.setUrl(url);
    	link.setActive(false);
    	
    	if ( linkDao.exist(link) ) {
    		linkDao.update(link);
    	}
    	
    }
    
    /**
     * This method gets called when the crawl has been started
     * @param crawler the crawler that started the crawl.
     */
    
    public void crawlStarted(Crawler crawler) {
    	log.info(MessageFormat.format("Crawler started crawling {0}", crawler.getDataSource().getID()));
    }

    /**
     * This method gets called when the crawler has began accessing an object.
     * @param crawler the crawler
     * @param url the URI of the object
     */
    
    public void accessingObject(Crawler crawler, String url) {
        this.currentURL = url;
    }

    /**
     * This method gets called when the crawler has encountered a new DataObject
     * @param dataCrawler the crawler
     * @param object the DataObject
     */
    
    public void objectNew(Crawler dataCrawler, DataObject object) {
    	processDataObject(dataCrawler, object);
    }
    
    /**
     * This method gets called when the crawler has encountered an object that has been modified
     * @param dataCrawler the crawler
     * @param object the DataObject
     */
    
    public void objectChanged(Crawler dataCrawler, DataObject object) {
    	log.info(MessageFormat.format("Object changed at URL {0}", object.getID()));
    	clear(object.getID().toString());
        processDataObject(dataCrawler, object);
    }
    
    /**
     * This method gets called when the crawler has encountered an object that has not been modified.
     * @param crawler the crawler
     * @param url the URI of the object.
     */
    
    public void objectNotModified(Crawler crawler, String url) {
    	this.currentURL = url;
    	log.info(MessageFormat.format("Object not modified at URL {0}", url));
    }

    /**
     * This method gets called when the crawler has encountered an object that has been removed from the data
     * source
     * @param dataCrawler the crawler
     * @param url the URI of the DataObject
     */
    
    public void objectRemoved(Crawler dataCrawler, String url) {
        
    	log.debug(MessageFormat.format("Object removed at URL {0}", url));
    	this.currentURL = url;
    	disableLink(url);
        clear(url);
        
    }
    
    /**
     * This method gets called when the crawler started clearing the data source.
     * @param crawler the crawler
     */
    
    public void clearStarted(Crawler crawler) {
    	log.debug("Clear started");
    }

    /**
     * This method gets called when the crawler clears an object
     * @param crawler the crawler
     * @param url the URI of the data object
     */
    
    public void clearingObject(Crawler crawler, String url) {
    	log.debug("Clear object");
    }

    /**
     * This method gets called when the crawler has finished clearing
     * @param crawler the crawler
     * @param exitCode the exitCode
     */
    
    public void clearFinished(Crawler crawler, ExitCode exitCode) {
    	log.debug("Clear finished");
    }
    
    /**
     * This method gets called when the crawler finishes crawling a data source
     * @param crawler the crawler
     * @param code the exit code.
     */
    
    public void crawlStopped(Crawler crawler, ExitCode code) {
        
    	this.exitCode = code;
        
		try {
			luceneIndex.optimize();
		} catch (IOException e) {
			log.warn(MessageFormat.format("Unable to close optimize index {0}", e));
		}
		
    }

    /**
     * Returns an RDFContainerFactory
     * @param crawler the Crawler that requests and RDFContainer
     * @param url the URL 
     * @return an RDFContainerFactory
     */
    
    public RDFContainerFactory getRDFContainerFactory(Crawler crawler, String url) {
        return this;
    }

    /**
     * Returns an RDFContainer for a particular uri
     * @param uri the URIs
     * @return an RDFContainer for a particular URI
     */
    
    public RDFContainer getRDFContainer(URI uri) {
    	Model model = ( modelSet == null ) ? RDF2Go.getModelFactory().createModel() : modelSet.getModel(uri);
        model.open();
        return new RDFContainerImpl(model, uri);
    }
    
    /**
     * @return Returns the currentURL.
     */
    
    public String getCurrentURL() {
        return currentURL;
    }
 
    /**
     * @return Returns the exitCode.
     */
    
    public ExitCode getExitCode() {
        return exitCode;
    }

    /**
     * @return Returns the nrObjects.
     */
    
    public int getNrObjects() {
        return nrObjects;
    }
    
    /**
     * Sets the index for Lucene
     * @param index
     */
    
    public void setLuceneIndex(LuceneIndex luceneIndex){
    	this.luceneIndex = luceneIndex;
    }
    
    public void setExtractorRegistry(CustomExtractorFactory extractorRegistry){
    	this.extractorRegistry = extractorRegistry;
    }

	public void setModelSet(ModelSet modelSet) {
		this.modelSet = modelSet;
	}

	public ModelSet getModelSet() {
		return modelSet;
	}

	public void setLinkDao(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

	public void setKeyWordClient(KeyWordClient keyWordClient) {
		this.keyWordClient = keyWordClient;
	}

}
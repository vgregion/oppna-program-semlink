package se.vgr.crawler.crawl;

import java.io.IOException;
import java.io.InputStream;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.ModelSet;
import org.ontoware.rdf2go.model.node.impl.URIImpl;
import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.rdf2go.RepositoryModelSet;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;

import org.openrdf.rio.RDFFormat;
import org.openrdf.rio.RDFParseException;

import org.semanticdesktop.aperture.accessor.AccessData;
import org.semanticdesktop.aperture.accessor.base.ModelAccessData;
import org.semanticdesktop.aperture.accessor.impl.DefaultDataAccessorRegistry;

import org.semanticdesktop.aperture.crawler.Crawler;
import org.semanticdesktop.aperture.crawler.CrawlerFactory;
import org.semanticdesktop.aperture.crawler.impl.DefaultCrawlerRegistry;
import org.semanticdesktop.aperture.crawler.web.WebCrawler;
import org.semanticdesktop.aperture.datasource.DataSource;

import org.semanticdesktop.aperture.hypertext.linkextractor.impl.DefaultLinkExtractorRegistry;
import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;

import se.vgr.crawler.crawl.handler.HarvesterHandler;
import se.vgr.crawler.entity.Source;
import se.vgr.crawler.index.LuceneIndex;

import se.vgr.crawler.repository.RepositoryManager;

import se.vgr.crawler.vocabulary.DC;
import se.vgr.crawler.vocabulary.DCTERMS;

/**
 * <p>Harvester</p>
 * @author Johan Säll Larsson
 */

public class Harvester {
	
	private static Logger log = Logger.getLogger(Harvester.class);
	
	private static final String ACCESS_DATA_SUFFIX = "-access";
	private static final String MESH_RDF_SCHEMA = "meshschema.rdfs";
	
	private RepositoryManager repositoryManager;
	private HarvesterHandler harvesterHandler;
	private Source source;
	private DataSource dataSource;
	private Repository repository;
	private Repository accessRepository;
	
	private AccessData accessData;
	
	private boolean inference = true;
	private boolean crawling;
	private Map<String, Float> fieldBoosts;
	
	private Crawler crawler;
    private LuceneIndex luceneIndex;
	
    public Harvester() {}

    /**
     * <p>Starts the crawl</p>
     * @param fullRecrawl
     */
    
	public synchronized void crawl(boolean fullRecrawl) {

		crawling = true;
		
		// remove previous crawl results when in full re-crawl mode
		if (fullRecrawl) {
			clear();
		}
		
		RepositoryConnection connection = null;
		Model model = null;
		ModelSet modelSet = null;
		
		try {
			
			// To be able to actually remember anything from a crawl we need to wrap the access
			// repository in a ModelAccessData. This will speed up the crawls if meta tag modified date
			// is set in the file to be harvested
			
			model = new RepositoryModel(new URIImpl(source.getRootUrl()),accessRepository);
	        model.open();
	        accessData = new ModelAccessData(model);
	        
	        modelSet = new RepositoryModelSet(repository);
	        modelSet.open();
	        
	        harvesterHandler.setLuceneIndex(luceneIndex);
	        harvesterHandler.setModelSet(modelSet);
	        
			Set<?> factories = new DefaultCrawlerRegistry().get(dataSource.getType());
			if (factories.isEmpty()) {
				return;
			}
			
			CrawlerFactory factory = (CrawlerFactory)factories.iterator().next();
			crawler = factory.getCrawler(dataSource);

			crawler.setDataAccessorRegistry(new DefaultDataAccessorRegistry());
			crawler.setAccessData(accessData);
			crawler.setCrawlerHandler(harvesterHandler);
			
			if ( crawler instanceof WebCrawler ) {
				WebCrawler webCrawler = (WebCrawler)crawler;
				webCrawler.setMimeTypeIdentifier(new MagicMimeTypeIdentifier());
				webCrawler.setLinkExtractorRegistry(new DefaultLinkExtractorRegistry());
			}

			crawler.crawl();

			log.info(MessageFormat.format("Crawlreport: {0}", crawler.getCrawlReport()));
			
			crawler = null;
			
		} catch ( Exception e ) {
			
			log.error(MessageFormat.format("Failed to crawl", e.getMessage()));
			
		} finally {
			
			close(connection);
			
			if ( model != null ) {
				model.close();
			}
			
			if ( modelSet != null ) {
				modelSet.close();
			}
			
			dataSource.dispose();
			
		}
	}

 
	public synchronized void initialize() throws IOException, RepositoryException, RepositoryConfigException, RDFParseException {
	
		luceneIndex.setIndexDirectory(source.getUniqueRepositoryName());
        luceneIndex.setFieldBoosts(fieldBoosts);
		
		repositoryManager.addRepository(source.getUniqueRepositoryName());
		repository = repositoryManager.getRepository(source.getUniqueRepositoryName());

		accessRepository = repositoryManager.addDataRepository(source.getUniqueRepositoryName(), ACCESS_DATA_SUFFIX);
		accessRepository.initialize();
		
		RepositoryConnection connection = repository.getConnection();
		
		if ( inference && connection != null ) { 
			
			InputStream result = ClassLoader.getSystemResourceAsStream(MESH_RDF_SCHEMA);
			
			if ( result != null ) {
				
				try {
					connection.add(result, "mesh", RDFFormat.RDFXML);
					connection.commit();
					result.close();
				} catch ( NullPointerException e ) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		// Add default name space prefix definitions	

		try {
			
			connection.setAutoCommit(false);
			connection.setNamespace("dc", DC.NS);
			connection.setNamespace("dcterms", DCTERMS.NS);
			connection.commit();
		
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			connection.close();
		}
		
	}
	
	/**
	 * Clears all results from the databases
	 * @throws IOException
	 */
	
	private void clear(){

		try {
			
			RepositoryConnection connection = repository.getConnection();
			
			try {
				
				connection.clear();
				connection.commit();
				luceneIndex.clear();
				
			} finally {
				
				close(connection);
				
			}
			
		} catch (Exception e) {
			
			log.error(MessageFormat.format("Unable to clear crawl results", e.getMessage()));
			
		}
		
		try {
			
			RepositoryConnection connection = accessRepository.getConnection();
			
			try {
				
				connection.clear();
				connection.commit();
				
			} finally {
				
				close(connection);
				
			}
			
		} catch (RepositoryException e) {
			log.error(MessageFormat.format("Unable to clear crawl results", e.getMessage()));
		}

	}

	private void close(RepositoryConnection connection) {
		
		if (connection != null) {
			
			try {
				connection.close();
			} catch (Exception e) {
				log.error(MessageFormat.format("Unable to close repository Connection", e.getMessage()));
			}
			
		}
		
	}
	
	public void setSource (Source source){
		this.source = source;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setCrawling(boolean crawling) {
		this.crawling = crawling;
	}

	public boolean isCrawling() {
		return crawling;
	}
	
	/* Spring setters */

	public void setHarvesterHandler(HarvesterHandler harvesterHandler) {
		this.harvesterHandler = harvesterHandler;
	}

	public void setRepositoryManager(RepositoryManager repositoryManager) {
		this.repositoryManager = repositoryManager;
	}

	public void setInference(boolean inference) {
		this.inference = inference;
	}

	public void setFieldBoosts(Map<String, Float> fieldBoosts) {
		this.fieldBoosts = fieldBoosts;
	}

	public void setLuceneIndex (LuceneIndex luceneIndex){
		this.luceneIndex = luceneIndex;
	}
	
}

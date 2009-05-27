package com.m1.semanticapp.harvester;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ontoware.rdf2go.exception.ModelException;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.ModelSet;
import org.ontoware.rdf2go.model.node.URI;
import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.rdf2go.RepositoryModelSet;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.repository.sail.SailRepository;
import org.openrdf.sail.nativerdf.NativeStore;
import org.semanticdesktop.aperture.accessor.AccessData;
import org.semanticdesktop.aperture.accessor.base.ModelAccessData;
import org.semanticdesktop.aperture.accessor.impl.DefaultDataAccessorRegistry;
import org.semanticdesktop.aperture.crawler.web.WebCrawler;
import org.semanticdesktop.aperture.datasource.config.DomainBoundaries;
import org.semanticdesktop.aperture.datasource.config.RegExpPattern;
import org.semanticdesktop.aperture.datasource.config.SubstringCondition;
import org.semanticdesktop.aperture.datasource.config.SubstringPattern;
import org.semanticdesktop.aperture.datasource.web.WebDataSource;
import org.semanticdesktop.aperture.hypertext.linkextractor.impl.DefaultLinkExtractorRegistry;
import org.semanticdesktop.aperture.mime.identifier.magic.MagicMimeTypeIdentifier;
import org.semanticdesktop.aperture.rdf.RDFContainer;
import org.semanticdesktop.aperture.rdf.impl.RDFContainerFactoryImpl;

import com.m1.semanticapp.taxonomy.TaxonomyClient;

public class Harvester {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private static final String REPOSITORY_DIR_NAME = "repositories";
	
    private boolean includeEmbeddedResources;

    private HarvesterHandler handler;
    private boolean verbose = true;
    private boolean identifyingMimeType;
    private boolean extractingContents;
    
    private AccessData accessData;
    private URI accessDataContext;

    // variables from class harvesterSite
	private String startUrl;
	private String siteName;
	private int depth;
	private String includePattern;
	private String excludePattern;
	private Repository dataRepository;
	private Repository accessRepository;
	
	private String repositoryDir; // path to respository directory
	private String repositoryName; // name of the respository
	
	private String accessDataDir; // path to data respository directory
	private String accessDataName; // name of the data respository
	
	//just for confirming the crawler has done its job, true=done its job, false = failed
	private boolean crawlSuccess = false;
    
	private TaxonomyClient taxonomyClient;
	
    public Harvester() throws Exception{
    	
    }
    
	/*
	 * Start the crawl
	 */
    
    public void crawl() throws ModelException, RepositoryException, RepositoryConfigException {

        RDFContainerFactoryImpl factory = new RDFContainerFactoryImpl();
        RDFContainer config = factory.newInstance(getSiteName());
        
        WebDataSource source = new WebDataSource();
        source.setConfiguration(config);
        source.setRootUrl(getStartUrl());
        source.setIncludeEmbeddedResources(getIncludeEmbeddedResources());
        
        if (getDepth() >= 0) {
            source.setMaximumDepth(getDepth());
        }

        source.setDomainBoundaries(initDomainBoundaries());

        WebCrawler crawler = new WebCrawler();
        crawler.setDataSource(source);
        crawler.setDataAccessorRegistry(new DefaultDataAccessorRegistry());
        crawler.setMimeTypeIdentifier(new MagicMimeTypeIdentifier());
        crawler.setLinkExtractorRegistry(new DefaultLinkExtractorRegistry());
        crawler.setCrawlerHandler(initHarvesterHandler());
        crawler.setAccessData(initAccessData());
        
        crawler.crawl();
        setCrawlSuccess(true);
    }
    
    /*
     * Setup the DomainBoundaries
     */
    
    private DomainBoundaries initDomainBoundaries(){
        
    	DomainBoundaries domainBoundaries = new DomainBoundaries();
    	
        if(getIncludePattern()!= null){
	        
        	try {
        		domainBoundaries.addIncludePattern(new RegExpPattern(getIncludePattern()));
	        }
	        catch (PatternSyntaxException e) {
	        	logger.error("Illegal regular expression: " + getIncludePattern());
	        }
	        
	        try {
	        	domainBoundaries.addExcludePattern(new RegExpPattern(getExcludePattern()));
	        }
	        catch (PatternSyntaxException e) {
	        	logger.error("Illegal regular expression: " + getExcludePattern());
	        }
        }
        

        if (domainBoundaries.getIncludePatterns().isEmpty() && domainBoundaries.getExcludePatterns().isEmpty()) {
            
        	logger.debug("The boundaries are still empty so init with start URL path to prevent infinite crawling");
        	String includePath = getStartUrl();

            if (includePath.startsWith("http") || includePath.startsWith("https") || includePath.startsWith("file:")) {
                
            	try {
                    URL url = new URL(includePath);
                    String path = url.getPath();
                    int lastPathSep = Math.max(path.lastIndexOf('/'), path.lastIndexOf('\\'));
                    
                    if (lastPathSep >= 0) {
                        path = path.substring(0, lastPathSep + 1);
                    }

                    includePath = new URL(url, path).toExternalForm();
                }
                catch (MalformedURLException e) {
                	logger.error("Invalid URL: " + includePath);
                }
            }

            domainBoundaries.addIncludePattern(new SubstringPattern(includePath, SubstringCondition.STARTS_WITH));

        }

        return domainBoundaries;
        
    }

    /*
     * Setup the Handler
     */
    
    private HarvesterHandler initHarvesterHandler() throws RepositoryException, RepositoryConfigException {
    	
    	String dirPath = getRepositoryDir()+REPOSITORY_DIR_NAME+"/"+getRepositoryName();
		File repositoryDir = new File(dirPath);
		
        NativeStore nativeStore = new NativeStore(repositoryDir, "spoc,posc,cspo");
        setDataRepository(new SailRepository(nativeStore));
        getDataRepository().initialize();
        ModelSet modelSet = new RepositoryModelSet(getDataRepository());
        modelSet.open();
        handler = new HarvesterHandler(getIdentifyingMimeType(), getExtractingContents(), verbose, getTaxonomyClient());
        handler.setModelSet(modelSet);        
        return handler;
    
    }
    
    /*
     * Setup the AccessData
     */
    
    private AccessData initAccessData() throws RepositoryException{
    	
    	String dirPath = getAccessDataDir()+REPOSITORY_DIR_NAME+"/"+getAccessDataName();
		File repositoryDir = new File(dirPath);
		
        NativeStore nativeStore = new NativeStore(repositoryDir, "spoc,posc,cspo");
        setAccessRepository(new SailRepository(nativeStore));
		
        getAccessRepository().initialize();
    	
        Model model = new RepositoryModel(getAccessDataContext(), getAccessRepository());
        model.open();
        accessData = new ModelAccessData(model);
    	return accessData;
    }
    
    public void killConnection() throws RepositoryException{
    	this.dataRepository.getConnection().close();
    	this.accessRepository.getConnection().close();
    }
    /*
     * SETTERS AND GETTERS
     */
    
    public boolean getIncludeEmbeddedResources() {
        return includeEmbeddedResources;
    }

    public void setIncludeEmbeddedResources(boolean includeEmbeddedResources) {
        this.includeEmbeddedResources = includeEmbeddedResources;
    }

    public boolean getIdentifyingMimeType() {
        return identifyingMimeType;
    }

    public void setIdentifyingMimeType(boolean identifyingMimeType) {
        this.identifyingMimeType = identifyingMimeType;
    }

    public boolean getExtractingContents() {
        return extractingContents;
    }

    public void setExtractingContents(boolean extractingContents) {
        this.extractingContents = extractingContents;
    }

    public AccessData getAccessData() {
        return accessData;
    }

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getStartUrl() {
		return startUrl;
	}  
    
	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}  
	
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setIncludePattern(String includePattern) {
		this.includePattern = includePattern;
	}

	public String getIncludePattern() {
		return includePattern;
	}

	public void setExcludePattern(String excludePattern) {
		this.excludePattern = excludePattern;
	}

	public String getExcludePattern() {
		return excludePattern;
	}

	public void setAccessDataContext(URI accessDataContext) {
		this.accessDataContext = accessDataContext;
	}

	public URI getAccessDataContext() {
		return accessDataContext;
	}

	public void setCrawlSuccess(boolean crawlSuccess) {
		this.crawlSuccess = crawlSuccess;
	}

	public boolean isCrawlSuccess() {
		return crawlSuccess;
	}

	public void setRepositoryDir(String repositoryDir) {
		this.repositoryDir = repositoryDir;
	}

	public String getRepositoryDir() {
		return repositoryDir;
	}

	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}

	public String getRepositoryName() {
		return repositoryName;
	}

	public void setAccessDataDir(String accessDataDir) {
		this.accessDataDir = accessDataDir;
	}

	public String getAccessDataDir() {
		return accessDataDir;
	}

	public void setAccessDataName(String accessDataName) {
		this.accessDataName = accessDataName;
	}

	public String getAccessDataName() {
		return accessDataName;
	}

	public void setAccessRepository(Repository accessRepository) {
		this.accessRepository = accessRepository;
	}

	public Repository getAccessRepository() {
		return accessRepository;
	}
	
	public void setDataRepository(Repository dataRepository) {
		this.dataRepository = dataRepository;
	}

	public Repository getDataRepository() {
		return dataRepository;
	}

	public void setTaxonomyClient(TaxonomyClient taxonomyClient) {
		this.taxonomyClient = taxonomyClient;
	}

	public TaxonomyClient getTaxonomyClient() {
		return taxonomyClient;
	}


}

package se.vgr.crawler.service;

import java.io.IOException;

import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.rio.RDFParseException;
import org.semanticdesktop.aperture.datasource.config.DomainBoundaries;

import org.semanticdesktop.aperture.datasource.config.SubstringCondition;
import org.semanticdesktop.aperture.datasource.config.SubstringPattern;
import org.semanticdesktop.aperture.datasource.web.WebDataSource;
import org.semanticdesktop.aperture.rdf.RDFContainer;
import org.semanticdesktop.aperture.rdf.impl.RDFContainerFactoryImpl;

import se.vgr.crawler.crawl.Harvester;
import se.vgr.crawler.entity.Source;
import se.vgr.crawler.service.HarvesterService;

/**
 * <p>Service for crawl</p>
 * @author Johan Säll Larsson
 */

public class HarvesterService {
	
	private Harvester harvester;
	
	public HarvesterService(){}
	
	public synchronized void startWebCrawl(Source source) throws RepositoryException, RepositoryConfigException, RDFParseException, IOException{
		
		DomainBoundaries boundaries = new DomainBoundaries();
        
		try {
			
			if ( source.getWhiteListPattern() != null ) {
				boundaries.addIncludePattern( new SubstringPattern(source.getWhiteListPattern(), SubstringCondition.STARTS_WITH) );
			}
			
			if ( source.getBlackListPattern() != null ) {
				boundaries.addExcludePattern( new SubstringPattern(source.getBlackListPattern(), SubstringCondition.STARTS_WITH) );
			}
			
		} catch ( Exception e ) {
			
		}
		
		RDFContainerFactoryImpl factory = new RDFContainerFactoryImpl();
        RDFContainer RDFconfig = factory.newInstance("urn:"+source.getUniqueRepositoryName());
       
        WebDataSource dataSource = new WebDataSource();
	    
        dataSource.setConfiguration(RDFconfig);
        dataSource.setRootUrl(source.getRootUrl());
        dataSource.setIncludeEmbeddedResources(false);
        dataSource.setMaximumDepth(source.getDepth());
		dataSource.setDomainBoundaries(boundaries);
		
		harvester.setSource(source);
		harvester.setDataSource(dataSource);
		harvester.initialize();
		boolean fullRecrawl = ( source.getFullReCrawl() != null ) ? source.getFullReCrawl() : true;
		harvester.crawl(fullRecrawl);
		
	}

	public void setHarvester(Harvester harvester) {
		this.harvester = harvester;
	}
	
}
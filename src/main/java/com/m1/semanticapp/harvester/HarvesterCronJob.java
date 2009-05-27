package com.m1.semanticapp.harvester;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.m1.semanticapp.repository.RdfRepository;


public class HarvesterCronJob {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private HarvesterManager harvesterManager;
	private RdfRepository rdfRepository;
	
	
	public void startWork()throws Exception{
		//retrieve list of sites to crawl
		List<HarvesterSite> site = harvesterManager.getAllSites();
		
		//setup and crawl the sites in the list
		for(HarvesterSite s: site){
			logger.debug("GETTING JOB: " + s.getStartUrl());
			Harvester harvester = new Harvester();

	    	harvester.setRepositoryDir(rdfRepository.getRepositoryDir());
	        harvester.setRepositoryName(rdfRepository.getRepositoryName());
	        harvester.setAccessDataDir(rdfRepository.getAccessDataDir());
	        harvester.setAccessDataName(rdfRepository.getAccessDataName());
			harvester.setAccessDataContext(null);
			harvester.setDepth(s.getDepth());
			harvester.setExcludePattern(s.getExcludePattern());
			harvester.setExtractingContents(true);
			harvester.setIdentifyingMimeType(true);
			harvester.setIncludePattern(s.getIncludePattern());
			harvester.setSiteName(s.getSiteName());
			harvester.setStartUrl(s.getStartUrl());
			harvester.crawl();
			
			// check if it successfully crawled the site
			if(!harvester.isCrawlSuccess()){
				logger.warn("Crawling for site: "+harvester.getSiteName()+" failed");
			}

			// we need to force a close 
			if(harvester.getDataRepository().getConnection()!=null){
				harvester.getDataRepository().getConnection().close();
				harvester.getDataRepository().shutDown();
				harvester.setDataRepository(null);
			}

			if(harvester.getAccessRepository().getConnection()!=null){
				harvester.getAccessRepository().getConnection().close();
				harvester.getAccessRepository().shutDown();
				harvester.setAccessRepository(null);
			}
			harvester = null;
		}
	}
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	public void setHarvesterManager(HarvesterManager harvesterManager) {
		this.harvesterManager = harvesterManager;
	}

	public void setRdfRepository(RdfRepository rdfRepository) {
		this.rdfRepository = rdfRepository;
	}
}

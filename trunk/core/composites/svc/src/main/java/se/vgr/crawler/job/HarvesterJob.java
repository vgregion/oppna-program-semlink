package se.vgr.crawler.job;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;

import se.vgr.crawler.entity.Source;
import se.vgr.crawler.service.HarvesterService;
import se.vgr.crawler.service.SourceService;

/**
 * Crawl job
 * @author Johan Säll Larsson
 */

public class HarvesterJob {

	private Logger log = Logger.getLogger(HarvesterJob.class);
	private SourceService sourceService;
	private HarvesterService harvesterService;
	
	/**
	 * Constructor
	 */
	
	public HarvesterJob() {}

	/**
	 * Starts the job
	 * @see services-jobs.xml targetMethod
	 */
	
	public void startWork(){
		
		log.info("Starting crawl job");
		
		List<Source> sources = sourceService.getAll();
		
		for ( Source source : sources ) {
			
			log.info("Starting crawl for: "+source.getRootUrl());
			
			try {
				
				harvesterService.startWebCrawl(source);
			
			} catch (Exception e) {
			
				e.printStackTrace();
				log.error(MessageFormat.format("Error in crawl job {0} : {1}", e.getMessage(), e.getStackTrace()));
			
			}
			
			/*
			Thread thread = new Thread() {
			    
				public void run() {
			    	
					try {
						log.info("New thread for: "+source.getRootUrl());
						harvesterService.startWebCrawl(source);
					} catch (Exception e) {
						e.printStackTrace();
						log.error(MessageFormat.format("Error in crawl job {0} : {1}", e.getMessage(), e.getStackTrace()));
					}
					
			    }
				
			};
			
			thread.setPriority(Thread.MIN_PRIORITY);
			thread.start();
			*/
		}
		
	}
	
	/* Setter for Spring */

	public void setSourceService(SourceService sourceService) {
		this.sourceService = sourceService;
	}

	public void setHarvesterService(HarvesterService harvesterService) {
		this.harvesterService = harvesterService;
	}
	
}

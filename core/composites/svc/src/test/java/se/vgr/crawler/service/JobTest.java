package se.vgr.crawler.service;

import se.vgr.crawler.AbstractDataAccess;
import se.vgr.crawler.job.HarvesterJob;

public class JobTest extends AbstractDataAccess {

	private HarvesterJob harvesterJob;

	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		harvesterJob = (HarvesterJob) applicationContext.getBean("system.harvesterJob");
	}
	
	public void testJob(){
		harvesterJob.startWork();
	}
	

}

package se.vgr.crawler.service;

import org.apache.log4j.Logger;

import se.vgr.crawler.AbstractDataAccess;
import se.vgr.crawler.dao.Status;

public class HttpStatusServiceTest extends AbstractDataAccess {

	private HttpStatusService httpStatusService;
	protected Logger log = Logger.getLogger(HttpStatusServiceTest.class);
	
	private static String URL = "http://www.thesemanticweb.se";
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		httpStatusService = (HttpStatusService) applicationContext.getBean("httpStatusService");
	}
	
	public void testUrlStatus() {
		Status status = httpStatusService.urlStatus(URL);
		log.debug("Sending back: "+status);
	}

}

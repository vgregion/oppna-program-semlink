package se.vgr.crawler.service;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;

import se.vgr.crawler.dao.LinkDao;
import se.vgr.crawler.dao.Status;
import se.vgr.crawler.entity.Link;
import se.vgr.crawler.status.HttpStatus;
import se.vgr.crawler.status.StatusCode;

/**
 * <p>Http status service</p>
 * @author Johan Säll Larsson
 */

public class HttpStatusService {

	protected Logger log = Logger.getLogger(HttpStatusService.class);
	private HttpStatus httpStatus;
	private LinkDao linkDao;
	
	public HttpStatusService() {}

	/**
	 * <p>Asserts the status of an url, updates the database and returns a <code>Status</code></p>
	 * @param url
	 * @return <code>Status.OK</code>
	 */
	
	public Status urlStatus(String url) {
		
		List<Link> links = linkDao.getByUrl(url);
		
		if ( links != null && links.size() > 0 ) {
			
			for ( Link link : links ) {
				
				Integer responseCode = httpStatus.getResponseCode(link.getRelatedLink());
				String responseMessage = StatusCode.statusCodes.get(responseCode);
				log.debug(MessageFormat.format("Response code for {0} is {1}", link.getRelatedLink(), responseCode));
				
				if ( responseCode != null ) {
					
					link.setResponseCode(responseCode);
					link.setResponseMessage(responseMessage);
					
					if ( responseCode >= 200 && responseCode < 300 ) {
						link.setActive(true);
					} else {
						link.setActive(false);
					}
					
					linkDao.updateRelatedLink(link);
					
				}
				
			}
			

		}
		
		return Status.OK;
		
	}
	
	/* Setter for Spring */
	
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public void setLinkDao(LinkDao linkDao) {
		this.linkDao = linkDao;
	}
	
}

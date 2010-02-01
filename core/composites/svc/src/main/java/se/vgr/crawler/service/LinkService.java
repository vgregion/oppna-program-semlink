package se.vgr.crawler.service;

import java.util.List;

import se.vgr.crawler.dao.LinkDao;
import se.vgr.crawler.dao.Status;
import se.vgr.crawler.entity.Link;

/**
 * <p>Link service</p>
 * @author Johan Säll Larsson
 */

public class LinkService {

	private LinkDao linkDao;
	
	public LinkService() {}
	
	/**
	 * <p>Returns all <code>Link</code> entities</p>
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getAll(){
		return linkDao.getAll();
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified URL</p>
	 * @param url
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getByUrl(String url){
		return linkDao.getByUrl(url);
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified related link</p>
	 * @param relatedLink
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getByRelatedLink(String relatedLink){
		return linkDao.getByRelatedLink(relatedLink);
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities which is active</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getByActive(boolean active){
		return linkDao.getByActive(active);
	}
	
	/**
	 * <p>Adds a <code>Link</code> to the repository if it does not already exists</p>
	 * @param link
	 * @return Status.OK, Status.EXIST
	 */
	
	public Status add(Link link){
		Integer status = linkDao.add(link);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.EXIST;
		}
	}
	
	/**
	 * <p>Adds a <code>List</code> of <code>Link</code> to the repository if it does not already exists</p>
	 * @param links
	 * @return Status.OK
	 */	
	
	public Status add(List<Link> links){
		linkDao.add(links);
		return Status.OK; 
	}
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified URL</p>
	 * @param url
	 * @return Status
	 * 		   - Status.OK, Status.FAIL 
	 */
	
	public Status deleteByUrl(String url){
		Integer status = linkDao.deleteByUrl(url);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.FAIL;
		}
	}
	
	/**
	 * <p>Deletes <code>Link</code> entities by its active status</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return Status
	 * 		   - Status.OK, Status.FAIL 
	 */
	
	public Status deleteByActive(boolean active){
		Integer status = linkDao.deleteByActive(active);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.FAIL;
		}
	}
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified id</p>
	 * @param id
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status deleteById(Integer id){
		Integer status = linkDao.deleteById(id);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.FAIL;
		}
	}
	
	/**
	 * <p>Updates a <code>Link</code></p>
	 * @param link
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status update(Link link){
		Integer status = linkDao.update(link);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.FAIL;
		}
	}
	
	/**
	 * <p>Updates a related <code>Link</code></p>
	 * @param link
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status updateRelatedLink(Link link){
		Integer status = linkDao.updateRelatedLink(link);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.FAIL;
		}
	}

	/* Setter for Spring */
	
	public void setLinkDao(LinkDao linkDao) {
		this.linkDao = linkDao;
	}

}

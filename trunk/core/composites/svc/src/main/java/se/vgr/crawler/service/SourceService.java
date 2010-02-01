package se.vgr.crawler.service;

import java.util.List;

import se.vgr.crawler.dao.SourceDao;
import se.vgr.crawler.dao.Status;
import se.vgr.crawler.entity.Source;

/**
 * <p>Source service</p>
 * @author Johan Säll Larsson
 */

public class SourceService {

	private SourceDao sourceDao;
	
	public SourceService() {}

	/**
	 * <p>Returns all <code>Source</code> entities</p>
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	public List<Source> getAll(){
		return sourceDao.getAll();
	}
	
	/**
	 * <p>Returns all <code>Source</code> entities by id</p>
	 * @param id
	 * @return A <code>Source</code> object
	 */
	
	public Source getById(Integer id) {
		return sourceDao.getById(id);
	}
	
	/**
	 * <p>Returns all <code>Source</code> entities with the <code>URL</code></p>
	 * @param rootUrl
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	public List<Source> getByRootUrl(String rootUrl) {
		return sourceDao.getByRootUrl(rootUrl);		
	}
	
	/**
	 * <p>Adds a <code>Source</code> to the repository if it does not already exists</p>
	 * @param source
	 * @return Status
	 * 		   - Status.OK or Status.EXIST
	 */
	
	public Status add(Source source) {
		int status = sourceDao.add(source);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.EXIST;
		}
		
	}
	
	/**
	 * <p>Deletes a <code>Source</code> entity with the specified <code>id</code></p>
	 * @param id
	 * @return Status
	 * 		   - Status.OK or Status.FAIL
	 */
	
	public Status delete(Integer id) {
		int status = sourceDao.delete(id);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.FAIL;
		}
	}
	
	/**
	 * <p>Updates a <code>Source</code></p>
	 * @param source
	 * @return Status
	 * 		   - Status.OK or Status.FAIL
	 */
	
	public Status update(Source source){
		int status = sourceDao.update(source);
		if ( status > 0 ) {
			return Status.OK;
		} else {	
			return Status.FAIL;
		}	
	}
	
	/* Setter for Spring */
	
	public void setSourceDao(SourceDao sourceDao) {
		this.sourceDao = sourceDao;
	}
	
}

package se.vgr.crawler.dao;

import java.util.List;

import se.vgr.crawler.entity.Link;

/**
 * Interface for persistence classes.
 * @author Johan Säll Larsson
 */

public interface LinkDao {

	/**
	 * <p>Returns all <code>Link</code> entities</p>
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getAll();
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified URL</p>
	 * @param url
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getByUrl(String url);
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified related link</p>
	 * @param relatedLink
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getByRelatedLink(String relatedLink);
	
	/**
	 * <p>Returns all <code>Link</code> entities which is active</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getByActive(boolean active);
	
	/**
	 * <p>Adds a <code>Link</code> to the repository if it does not already exists</p>
	 * @param link
	 */
	
	public int add(Link link);
	
	/**
	 * <p>Adds a <code>List</code> of <code>Link</code> to the repository if it does not already exists</p>
	 * @param links
	 */	
	
	public void add(List<Link> links);
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified URL</p>
	 * @param url
	 * @return the number of <code>Links</code> deleted 
	 */
	
	public int deleteByUrl(String url);
	
	/**
	 * <p>Deletes <code>Link</code> entities by its active status</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return the number of <code>Links</code> deleted 
	 */
	
	public int deleteByActive(boolean active);
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified id</p>
	 * @param id
	 * @return the number of <code>Links</code> deleted 
	 */
	
	public int deleteById(Integer id);
	
	/**
	 * <p>Updates a <code>Link</code></p>
	 * @param link
	 * @return the number of <code>Links</code> updated 
	 */
	
	public int update(Link link);
	
	/**
	 * <p>Updates a related <code>Link</code></p>
	 * @param link
	 * @return the number of <code>Links</code> updated 
	 */
	
	public int updateRelatedLink(Link link);
	
	/**
	 * <p>Returns if <code>Link</code> exists</p>
	 * @param link
	 * @return <code>true</code> if exists
	 */
	
	public boolean exist(Link link);
	
	
}

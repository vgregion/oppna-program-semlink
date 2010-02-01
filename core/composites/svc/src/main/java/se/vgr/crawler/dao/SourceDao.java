package se.vgr.crawler.dao;

import java.util.List;

import se.vgr.crawler.entity.Source;

/**
 * Interface for persistence classes.
 * @author Johan Säll Larsson
 */

public interface SourceDao {

	/**
	 * <p>Returns all <code>Source</code> entities</p>
	 * @return A <code>List</code> of <code>Source</code> objects
	 */

	public List<Source> getAll();
	
	/**
	 * <p>Returns all <code>Source</code> entities by id</p>
	 * @param id
	 * @return A <code>Source</code> object
	 */

	public Source getById(Integer id);	
	
	/**
	 * <p>Returns all <code>Source</code> entities with the <code>URL</code></p>
	 * @param rootUrl
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	public List<Source> getByRootUrl(String rootUrl);
	
	/**
	 * <p>Adds a <code>Source</code> to the repository if it does not already exists</p>
	 * @param source
	 * @return int
	 */
	
	public int add(Source source);
	
	/**
	 * <p>Deletes a <code>Source</code> entity with the specified <code>id</code></p>
	 * @param id
	 * @return the number of <code>Sources</code> deleted 
	 */
	
	public int delete(Integer url);
	
	/**
	 * <p>Updates a <code>Source</code></p>
	 * @param source
	 * @return the number of <code>Sources</code> updated 
	 */
	
	public int update(Source source);
}

package se.vgr.crawler.intsvc;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import se.vgr.crawler.dao.Status;
import se.vgr.crawler.domain.LuceneHit;
import se.vgr.crawler.entity.Link;
import se.vgr.crawler.entity.Source;

/**
 * External service interface used as a proxy
 * @author Johan Säll Larsson
 */

@WebService
public interface ISemanticService {

	/**
	 * <p>Returns all <code>Link</code> entities</p>
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinks();
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified URL</p>
	 * @param url
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinksByUrl(@WebParam(name="url")String url);
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified related link</p>
	 * @param relatedLink
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinksByRelated(@WebParam(name="relatedLink")String relatedLink);
	
	/**
	 * <p>Returns all <code>Link</code> entities which is active</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinksByActive(@WebParam(name="active")boolean active);
	
	/**
	 * <p>Adds a <code>Link</code> to the repository if it does not already exists</p>
	 * @param link
	 * @return Status.OK, Status.EXIST
	 */
	
	public Status addLink(@WebParam(name="link")Link link);
	
	/**
	 * <p>Adds a <code>List</code> of <code>Link</code> to the repository if it does not already exists</p>
	 * @param links
	 * @return Status.OK
	 */	
	
	public Status addLinkList(@WebParam(name="links")List<Link> links);
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified URL</p>
	 * @param url
	 * @return Status
	 * 		   - Status.OK, Status.FAIL 
	 */
	
	public Status deleteLinkByUrl(@WebParam(name="id")String url);
	
	/**
	 * <p>Deletes <code>Link</code> entities by its active status</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return Status
	 * 		   - Status.OK, Status.FAIL 
	 */
	
	public Status deleteLinkByActive(@WebParam(name="active")boolean active);
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified id</p>
	 * @param id
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status deleteLinkById(@WebParam(name="id")Integer id);
	
	/**
	 * <p>Updates a <code>Link</code></p>
	 * @param link
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status updateLink(@WebParam(name="link")Link link);
	
	/**
	 * <p>Updates a related <code>Link</code></p>
	 * @param link
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status updateLinkRelated(@WebParam(name="link")Link link);

	/**
	 * <p>Returns all <code>Source</code> entities</p>
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	public List<Source> getSources();
	
	/**
	 * <p>Returns all <code>Source</code> entities with the <code>URL</code></p>
	 * @param rootUrl
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	public List<Source> getSourceByRootUrl(@WebParam(name="rootUrl")String rootUrl);
	
	/**
	 * <p>Adds a <code>Source</code> to the repository if it does not already exists</p>
	 * @param source
	 * @return Status
	 * 		   - Status.OK or Status.EXIST
	 */
	
	public Status addSource(@WebParam(name="source")Source source);
	
	/**
	 * <p>Deletes a <code>Source</code> entity with the specified <code>id</code></p>
	 * @param id
	 * @return Status
	 * 		   - Status.OK or Status.FAIL
	 */
	
	public Status deleteSource(@WebParam(name="id")Integer id);
	
	/**
	 * <p>Updates a <code>Source</code></p>
	 * @param source
	 * @return Status
	 * 		   - Status.OK or Status.FAIL
	 */
	
	public Status updateSource(@WebParam(name="source")Source source);
	
	/**
	 * <p>Searches all indexes</p>
	 * @param query
	 * @param languageCode
	 * @param source
	 * @param maxHits
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 */
	
	public List<LuceneHit> search(@WebParam(name="query")String query, @WebParam(name="languageCode")String languageCode, @WebParam(name="source")String source, @WebParam(name="maxHits")Integer maxHits);

	/**
	 * <p>Searches a specific URL</p>
	 * @param url
	 * @param languageCode
	 * @param source
	 * @param maxHits
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 */
	
	public List<LuceneHit> searchUrl(@WebParam(name="url")String url, @WebParam(name="languageCode")String languageCode, @WebParam(name="source")String source, @WebParam(name="maxHits")Integer maxHits);
	
	/**
	 * <p>Asserts the status of an url, updates the database and returns a <code>Status</code></p>
	 * @param url
	 * @return <code>Status.OK</code>
	 */
	
	public Status urlStatus(@WebParam(name="url")String url);
	
	
}

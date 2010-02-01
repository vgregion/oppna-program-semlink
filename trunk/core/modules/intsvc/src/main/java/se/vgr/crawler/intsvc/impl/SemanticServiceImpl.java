package se.vgr.crawler.intsvc.impl;

import java.util.List;

import se.vgr.crawler.dao.Status;
import se.vgr.crawler.domain.LuceneHit;
import se.vgr.crawler.entity.Link;
import se.vgr.crawler.entity.Source;
import se.vgr.crawler.intsvc.ISemanticService;
import se.vgr.crawler.service.HttpStatusService;
import se.vgr.crawler.service.LinkService;
import se.vgr.crawler.service.SearchService;
import se.vgr.crawler.service.SourceService;

public class SemanticServiceImpl implements ISemanticService {

	private SourceService sourceService;
	private LinkService linkService;
	private SearchService searchService;
	private HttpStatusService httpStatusService;
	
	/**
	 * <p>Returns all <code>Link</code> entities</p>
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinks(){
		return linkService.getAll();
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified URL</p>
	 * @param url
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinksByUrl(String url){
		return linkService.getByUrl(url);
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified related link</p>
	 * @param relatedLink
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinksByRelated(String relatedLink){
		return linkService.getByRelatedLink(relatedLink);
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities which is active</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	public List<Link> getLinksByActive(boolean active){
		return linkService.getByActive(active);
	}
	
	/**
	 * <p>Adds a <code>Link</code> to the repository if it does not already exists</p>
	 * @param link
	 * @return Status.OK, Status.EXIST
	 */
	
	public Status addLink(Link link){
		return linkService.add(link);
	}
	
	/**
	 * <p>Adds a <code>List</code> of <code>Link</code> to the repository if it does not already exists</p>
	 * @param links
	 * @return Status.OK
	 */	
	
	public Status addLinkList(List<Link> links){
		return linkService.add(links);
	}
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified URL</p>
	 * @param url
	 * @return Status
	 * 		   - Status.OK, Status.FAIL 
	 */
	
	public Status deleteLinkByUrl(String url){
		return linkService.deleteByUrl(url);
	}
	
	/**
	 * <p>Deletes <code>Link</code> entities by its active status</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return Status
	 * 		   - Status.OK, Status.FAIL 
	 */
	
	public Status deleteLinkByActive(boolean active){
		return linkService.deleteByActive(active);
	}
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified id</p>
	 * @param id
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status deleteLinkById(Integer id){
		return linkService.deleteById(id);
	}
	
	/**
	 * <p>Updates a <code>Link</code></p>
	 * @param link
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status updateLink(Link link){
		return linkService.update(link);
	}
	
	/**
	 * <p>Updates a related <code>Link</code></p>
	 * @param link
	 * @return Status
	 * 		   - Status.OK, Status.FAIL
	 */
	
	public Status updateLinkRelated(Link link){
		return linkService.updateRelatedLink(link);
	}
	
	/**
	 * <p>Returns all <code>Source</code> entities</p>
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	public List<Source> getSources(){
		return sourceService.getAll();
	}
	
	/**
	 * <p>Returns all <code>Source</code> entities with the <code>URL</code></p>
	 * @param rootUrl
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	public List<Source> getSourceByRootUrl(String rootUrl){
		return sourceService.getByRootUrl(rootUrl);
	}
	
	/**
	 * <p>Adds a <code>Source</code> to the repository if it does not already exists</p>
	 * @param source
	 * @return Status
	 * 		   - Status.OK or Status.EXIST
	 */
	
	public Status addSource(Source source){
		return sourceService.add(source);
	}
	
	/**
	 * <p>Deletes a <code>Source</code> entity with the specified <code>id</code></p>
	 * @param id
	 * @return Status
	 * 		   - Status.OK or Status.FAIL
	 */
	
	public Status deleteSource(Integer id){
		return sourceService.delete(id);
	}
	
	/**
	 * <p>Updates a <code>Source</code></p>
	 * @param source
	 * @return Status
	 * 		   - Status.OK or Status.FAIL
	 */
	
	public Status updateSource(Source source){
		return sourceService.update(source);
	}

	/**
	 * <p>Searches all indexes</p>
	 * @param query
	 * @param languageCode
	 * @param source
	 * @param maxHits
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 */
	
	public List<LuceneHit> search(String query, String languageCode, String source, Integer maxHits) {
		return searchService.search(query, languageCode, source, maxHits );
	}
	
	/**
	 * <p>Searches a specific URL</p>
	 * @param url
	 * @param languageCode
	 * @param source
	 * @param maxHits
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 */
	
	public List<LuceneHit> searchUrl(String url, String languageCode, String source, Integer maxHits){
		return searchService.searchUrl(url, languageCode, source, maxHits);
	}

	/**
	 * <p>Asserts the status of an url, updates the database and returns a <code>Status</code></p>
	 * @param url
	 * @return <code>Status.OK</code>
	 */
	
	public Status urlStatus(String url) {
		return httpStatusService.urlStatus(url);
	}

	
	/* Setters for Spring */
	
	public void setSourceService(SourceService sourceService) {
		this.sourceService = sourceService;
	}

	public void setLinkService(LinkService linkService) {
		this.linkService = linkService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public void setHttpStatusService(HttpStatusService httpStatusService) {
		this.httpStatusService = httpStatusService;
	}

}

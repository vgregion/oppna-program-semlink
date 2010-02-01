package se.vgr.crawler.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import se.vgr.crawler.dao.LinkDao;
import se.vgr.crawler.entity.Link;

/**
 * <p>Persistent Data Access Object for <code>Links</code></p>
 * @author Johan Säll Larsson
 */

@Transactional
public class LinkDaoImpl implements LinkDao {

	private EntityManager em;
	protected Logger log = Logger.getLogger(LinkDaoImpl.class);

	public LinkDaoImpl() {}
	
	/**
	 * <p>Returns all <code>Link</code> entities</p>
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	@SuppressWarnings("unchecked")
	public List<Link> getAll(){
		final String SQL_GET_ALL = "SELECT l FROM Link l";
		Query query = em.createQuery(SQL_GET_ALL);
		return query.getResultList();
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified URL</p>
	 * @param url
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	@SuppressWarnings("unchecked")
	public List<Link> getByUrl(String url){
		String SQL_GET_ALL = "SELECT l FROM Link l WHERE l.url=:url";
		Query query = em.createQuery(SQL_GET_ALL);
		query.setParameter("url",url);
		return query.getResultList();
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities with the specified related link</p>
	 * @param relatedLink
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	@SuppressWarnings("unchecked")
	public List<Link> getByRelatedLink(String relatedLink){
		final String SQL_GET_ALL = "SELECT l FROM Link l WHERE l.relatedLink=:relatedLink";
		Query query = em.createQuery(SQL_GET_ALL);
		query.setParameter("relatedLink",relatedLink);
		return query.getResultList();
	}
	
	/**
	 * <p>Returns all <code>Link</code> entities which is active</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return A <code>List</code> of <code>Link</code> objects
	 */
	
	@SuppressWarnings("unchecked")
	public List<Link> getByActive(boolean active){
		final String SQL_GET_ALL = "SELECT l FROM Link l WHERE l.active=:active";
		Query query = em.createQuery(SQL_GET_ALL);
		query.setParameter("active",active);
		return query.getResultList();
	}
	
	/**
	 * <p>Adds a <code>Link</code> to the repository if it does not already exists</p>
	 * @param link
	 * @return int
	 */
	
	public int add(Link link){
		
		if ( !exist(link) ) {
			em.persist(link);
			return 1;
		} else {
			log.debug(MessageFormat.format("Url {0} already exist in the database",link.getUrl()));
			return -1;
		}
		
	}
	
	/**
	 * <p>Adds a <code>List</code> of <code>Link</code> to the repository if it does not already exists</p>
	 * @param links
	 * @return int
	 */	
	
	public void add(List<Link> links){
		
		for ( Link link : links ) {
			add(link);
		}
		
	}
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified URL</p>
	 * @param url
	 * @return the number of <code>Links</code> deleted 
	 */
	
	public int deleteByUrl(String url){
		
		final String DELETE_SQL = "DELETE FROM Link l WHERE l.url=:url";
		Query query = em.createQuery(DELETE_SQL);
        query.setParameter("url", url);
        return query.executeUpdate();
        
	}

	/**
	 * <p>Deletes <code>Link</code> entities by its active status</p>
	 * @param active
	 * 		  Either <code>true</code> - the link is active or
	 * 		  <code>false</code> - the link is inactive, i.e. a 'dead link'
	 * @return the number of <code>Links</code> deleted 
	 */
	
	public int deleteByActive(boolean active){
		
		final String DELETE_SQL = "DELETE FROM Link l WHERE l.active=:active";
		Query query = em.createQuery(DELETE_SQL);
        query.setParameter("active", active);
        return query.executeUpdate();
        
	}
	
	/**
	 * <p>Deletes a <code>Link</code> entity with the specified id</p>
	 * @param id
	 * @return the number of <code>Links</code> deleted 
	 */
	
	public int deleteById(Integer id){
		
		final String DELETE_SQL = "DELETE FROM Link l WHERE l.id=:id";
		Query query = em.createQuery(DELETE_SQL);
        query.setParameter("id", id);
        return query.executeUpdate();
        
	}
	
	/**
	 * <p>Updates a <code>Link</code></p>
	 * @param link
	 * @return the number of <code>Links</code> updated 
	 */
	
	public int update(Link link){

		final String UPDATE_SQL = "UPDATE Link l SET l.url=:url, " +
				"l.title=:title, l.description=:description, " +
				"l.keyword=:keyword, l.relatedLink=:relatedLink, " +
				"l.semanticType=:semanticType, l.source=:source, " +
				"l.active=:active, l.responseCode=:responseCode, " +
				"l.responseMessage=:responseMessage, l.language=:language " +
				"WHERE l.id=:id";

		Query query = em.createQuery(UPDATE_SQL);
		query.setParameter("id", link.getId());
		query.setParameter("url", link.getUrl());
		query.setParameter("title", link.getTitle());
		query.setParameter("description", link.getDescription());
		query.setParameter("keyword", link.getKeyword());
		query.setParameter("relatedLink", link.getRelatedLink());
		query.setParameter("semanticType", link.getSemanticType());
		query.setParameter("source", link.getSource());
		query.setParameter("active", link.getActive());
		query.setParameter("responseCode", link.getResponseCode());
		query.setParameter("responseMessage", link.getResponseMessage());
		query.setParameter("language", link.getLanguage());
		
		return query.executeUpdate();
		
	}
	
	/**
	 * <p>Updates a related <code>Link</code></p>
	 * @param link
	 * @return the number of <code>Links</code> updated 
	 */
	
	public int updateRelatedLink(Link link){

		final String UPDATE_SQL = "UPDATE Link l SET l.url=:url, " +
				"l.title=:title, l.description=:description, " +
				"l.keyword=:keyword, l.relatedLink=:relatedLink, " +
				"l.semanticType=:semanticType, l.source=:source, " +
				"l.active=:active, l.responseCode=:responseCode, " +
				"l.responseMessage=:responseMessage, l.language=:language " +
				"WHERE l.relatedLink=:relatedLink";

		Query query = em.createQuery(UPDATE_SQL);
		query.setParameter("url", link.getUrl());
		query.setParameter("title", link.getTitle());
		query.setParameter("description", link.getDescription());
		query.setParameter("keyword", link.getKeyword());
		query.setParameter("relatedLink", link.getRelatedLink());
		query.setParameter("semanticType", link.getSemanticType());
		query.setParameter("source", link.getSource());
		query.setParameter("active", link.getActive());
		query.setParameter("responseCode", link.getResponseCode());
		query.setParameter("responseMessage", link.getResponseMessage());
		query.setParameter("language", link.getLanguage());
		
		return query.executeUpdate();
		
	}
	
	/**
	 * <p>Returns if <code>Link</code> exists</p>
	 * @param link
	 * @return <code>true</code> if exists
	 */
	
	@SuppressWarnings("unchecked")
	public boolean exist(Link link){
		
		String EXIST_SQL = "SELECT l FROM Link l WHERE l.url=:url AND l.relatedLink=:relatedLink";
		Query query = em.createQuery(EXIST_SQL);
		query.setParameter("url", link.getUrl());
		query.setParameter("relatedLink", link.getRelatedLink());
		
		List<Link> retval = query.getResultList();
		
		if ( retval != null && retval.size() > 0 ) {
			return true;
		} else {
			return false;
		}
		
	}
	
	/* Setter for entityManager */
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.em = entityManager;
	}
	
}

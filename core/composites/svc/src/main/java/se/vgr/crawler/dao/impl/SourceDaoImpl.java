package se.vgr.crawler.dao.impl;

import java.text.MessageFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import se.vgr.crawler.dao.SourceDao;
import se.vgr.crawler.entity.Link;
import se.vgr.crawler.entity.Source;

/**
 * <p>Persistent Data Access Object for <code>Source</code></p>
 * @author Johan Säll Larsson
 */

@Transactional
public class SourceDaoImpl implements SourceDao {
	
	private EntityManager em;
	protected Logger log = Logger.getLogger(SourceDaoImpl.class);
	
	public SourceDaoImpl() {}
	
	/**
	 * <p>Returns all <code>Source</code> entities</p>
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	@SuppressWarnings("unchecked")
	public List<Source> getAll(){
		
		final String SQL_GET_ALL = "SELECT s FROM Source s";
		Query query = em.createQuery(SQL_GET_ALL);
		return query.getResultList();
		
	}
	
	/**
	 * <p>Returns all <code>Source</code> entities by id</p>
	 * @param id
	 * @return A <code>Source</code> object
	 */
	
	public Source getById(Integer id) {
		
		final String SQL_GET_BY_ID = "SELECT s FROM Source s WHERE s.id=:id";
		Query query = em.createQuery(SQL_GET_BY_ID);
		query.setParameter("id", id);
		return (Source) query.getSingleResult();
		
	}
	
	/**
	 * <p>Returns all <code>Source</code> entities with the <code>URL</code></p>
	 * @param rootUrl
	 * @return A <code>List</code> of <code>Source</code> objects
	 */
	
	@SuppressWarnings("unchecked")
	public List<Source> getByRootUrl(String rootUrl) {
		
		final String SQL_BY_ROOT_URL = "SELECT s FROM Source s WHERE s.rootUrl=:rootUrl";
		Query query = em.createQuery(SQL_BY_ROOT_URL);
		query.setParameter("rootUrl", rootUrl);
		return query.getResultList();
		
	}
	
	/**
	 * <p>Adds a <code>Source</code> to the repository if it does not already exists</p>
	 * @param source
	 * @return int
	 */
	
	public int add(Source source){
		
		if ( !exist(source) ) {
			em.persist(source);
			return 1;
		} else {
			log.debug(MessageFormat.format("Source {0} already exist in the database", source.getRootUrl()));
			return 0;
		}
		
	}
	
	/**
	 * <p>Deletes a <code>Source</code> entity with the specified <code>id</code></p>
	 * @param id
	 * @return the number of <code>Sources</code> deleted 
	 */
	
	public int delete(Integer id){
		
		final String DELETE_SQL = "DELETE FROM Source s WHERE s.id=:id";
		Query query = em.createQuery(DELETE_SQL);
        query.setParameter("id", id);
        return query.executeUpdate();
        
	}
	
	/**
	 * <p>Updates a <code>Source</code></p>
	 * @param source
	 * @return the number of <code>Sources</code> updated 
	 */
	
	public int update(Source source){

		final String UPDATE_SQL = "UPDATE Source s SET s.rootUrl=:rootUrl, " +
				"s.source=:source, s.depth=:depth, " +
				"s.whiteListPattern=:whiteListPattern, s.blackListPattern=:blackListPattern, " +
				"s.uniqueRepositoryName=:uniqueRepositoryName, s.sourceType=:sourceType, " +
				"s.fullReCrawl=:fullReCrawl WHERE s.id=:id";
		
		Query query = em.createQuery(UPDATE_SQL);
		query.setParameter("id", source.getId());
		query.setParameter("rootUrl", source.getRootUrl());
		query.setParameter("source", source.getSource());
		query.setParameter("depth", source.getDepth());
		query.setParameter("whiteListPattern", source.getWhiteListPattern());
		query.setParameter("blackListPattern", source.getBlackListPattern());
		query.setParameter("uniqueRepositoryName", source.getUniqueRepositoryName());
		query.setParameter("sourceType", source.getSourceType());
		query.setParameter("fullReCrawl", source.getFullReCrawl());
		
		return query.executeUpdate();
		
	}
	
	/**
	 * <p>Returns if <code>Source</code> exists</p>
	 * @param source
	 * @return <code>true</code> if exists
	 */
	
	@SuppressWarnings("unchecked")
	public boolean exist(Source source){
		
		String EXIST_SQL = "SELECT s FROM Source s WHERE s.rootUrl=:rootUrl";
		Query query = em.createQuery(EXIST_SQL);
		query.setParameter("rootUrl", source.getRootUrl());
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

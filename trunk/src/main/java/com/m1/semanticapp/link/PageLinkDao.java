package com.m1.semanticapp.link;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PageLinkDao extends SimpleJdbcDaoSupport {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public PageLinkDao(){}
	
	/**
	 * <p>Gets the related links of the referenced URL.</p>
	 * @param String url
	 * @return List<PageLink>
	 */
	
	public List<PageLink> getPageLink(String url) {
		String GET_PAGELINK_SQL = "SELECT * FROM related_links WHERE ref='"+url+"'";
		List<PageLink> ListOfRef = getSimpleJdbcTemplate().query(
				GET_PAGELINK_SQL, new ReferenceMapper());
		return ListOfRef;
	}

	/**
	 * <p>Deletes the related links of the referenced URL.</p>
	 * @param List<PageLink>
	 * @return void
	 */
	
	private void deletePageLink(List<PageLink> ListOfRef) {
		String refString = null;
		for(PageLink ref : ListOfRef){
			refString = ref.getRef();
		}
		String SQL_DELETE_PAGELINK = "DELETE FROM related_links WHERE ref = ?";	
		int deleteJob = getSimpleJdbcTemplate().update(SQL_DELETE_PAGELINK, refString);
		logger.info("DELETED pageLink, rows affected: " + deleteJob);
	}

	/**
	 * <p>Public function which deletes all occurrences of a URL</p>
	 * @param url
	 * @return boolean
	 */
	public boolean deleteAllPageLink(String url){
		String SQL_DELETE_PAGELINK = "DELETE FROM related_links WHERE ref = ?";	
		int deleteJob = getSimpleJdbcTemplate().update(SQL_DELETE_PAGELINK, url);
		logger.info("DELETED pageLink, rows affected: " + deleteJob);
		return true;
	}

	/**
	 * Saves the related links of the referenced URL.
	 * 
	 * @param List<PageLink>
	 * @return boolean
	 */
	
	public boolean addPageLink(List<PageLink> referrer) {

		int count = referrer.size();
		if(count == 0){
			logger.warn("References was 0");
			return false;
		}
		
		deletePageLink(referrer);
		
		String SQL_INSERT_PAGELINK = "INSERT INTO related_links (id, uri, title, ref, type, source, active, code, msg) values (0,:uri,:title,:ref, :type, :source, :active, :code, :msg)";	
		
		for(PageLink ref : referrer){
			int insertJob = getSimpleJdbcTemplate().update(SQL_INSERT_PAGELINK, 
					new MapSqlParameterSource().addValue("uri", ref.getUri())
					.addValue("title", ref.getTitle())
					.addValue("ref", ref.getRef())
					.addValue("type", ref.getType())
					.addValue("source", ref.getSource())
					.addValue("active", ref.getActive())
					.addValue("code", ref.getResponseCode())
					.addValue("msg", ref.getResponseMessage())
			);
			logger.info("INSERTED pageLink, rows affected: " + insertJob);
		}

		return true;
	}
	
	private static class ReferenceMapper implements ParameterizedRowMapper<PageLink> {		
		public PageLink mapRow(ResultSet rs, int rowNum) throws SQLException {
			PageLink ref = new PageLink();
			ref.setI(rs.getInt("id"));
			ref.setRef(rs.getString("ref"));
			ref.setTitle(rs.getString("title"));
			ref.setUri(rs.getString("uri"));
			ref.setType(rs.getString("type"));
			ref.setSource(rs.getString("source"));
			ref.setActive(rs.getInt("active"));
			ref.setResponseCode(rs.getInt("code"));
			ref.setResponseMessage(rs.getString("msg"));
			return ref;	
		}
	}
}
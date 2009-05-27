package com.m1.semanticapp.service.webservice;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.m1.semanticapp.link.LinkManager;
import com.m1.semanticapp.link.PageLink;

public class LinksHandlerService extends SimpleJdbcDaoSupport {

	protected final Log logger = LogFactory.getLog(getClass());
	private LinkManager linkManager;
	
	/**
	 * <p>Gets the related links of the referenced URL.</p>
	 * @param String url
	 * @return PageLink[]
	 */
	
	public PageLink[] GetPageLink(String url) {
		String GET_PAGELINK_SQL = "SELECT * FROM related_links WHERE ref=:uri";
		List<PageLink> ListOfRef = getSimpleJdbcTemplate().query(
				GET_PAGELINK_SQL, new ReferenceMapper(), new MapSqlParameterSource().addValue("uri", url));
		return ListOfRef.toArray(new PageLink[ListOfRef.size()]);
	}
	
	/**
	 * <p>Deletes the related links of the referenced URL.</p>
	 * @param List<PageLink>
	 * @return none
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
	 * Public function which deletes all occurrences of a URL
	 * @param url
	 * @return
	 */
	public boolean deleteAllPageLink(String url){
		String SQL_DELETE_PAGELINK = "DELETE FROM related_links WHERE ref = ?";	
		int deleteJob = getSimpleJdbcTemplate().update(SQL_DELETE_PAGELINK, url);
		logger.info("DELETED pageLink, rows affected: " + deleteJob);
		return true;
	}
	
	/**
	 * <p>Saves the related links of the referenced URL.</p>
	 * 
	 * @param List<PageLink>
	 * @return boolean
	 */
	
	public boolean AddPageLink(String in) {
		List<PageLink> referrer = split(in);

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
			PageLink pageLink = new PageLink();
			pageLink.setI(rs.getInt("id"));
			pageLink.setRef(rs.getString("ref"));
			pageLink.setTitle(rs.getString("title"));
			pageLink.setUri(rs.getString("uri"));
			pageLink.setType(rs.getString("type"));
			pageLink.setSource(rs.getString("source"));
			pageLink.setActive(rs.getInt("active"));
			pageLink.setResponseCode(rs.getInt("code"));
			pageLink.setResponseMessage(rs.getString("msg"));
			System.out.println("Stuff out: active->"+rs.getInt("active")+" code->"+ rs.getInt("code")+" msg->"+rs.getString("msg"));
			return pageLink;	
		}
	}
	/* | object och ; attributes */
	// FIXME Unstable way to do this... Fix so that axis can understand a List of Objects, then delete this
	
	private List<PageLink> split(String in) {
		System.out.println("Detta ska sparas: " +in);
		List<PageLink> linkList = new ArrayList<PageLink>();
		String [] obj = null;
		String [] att = null;
	    
	    if (in.indexOf("|")==-1){
	    	obj = new String[1];
	    	obj[0] = in;
	    }else{
	    	obj = in.split("\\|");
	    }
	    for(int i=0;i<obj.length;i++){
	    	PageLink pageLink = new PageLink();
	    	att = obj[i].split("\\,");
	    	pageLink.setI(0);
	    	pageLink.setUri(att[1]);
	    	pageLink.setTitle(att[2]);
	    	pageLink.setRef(att[3]);
	    	pageLink.setSource(att[4]);
	    	pageLink.setType(att[5]);
	    	pageLink.setActive(Integer.parseInt(att[6]));
	    	pageLink.setResponseCode(Integer.parseInt(att[7]));
	    	pageLink.setResponseMessage(att[8]);
    		
    		linkList.add(pageLink);
	    }
		return linkList;
	 }

	public boolean UpdateUrlStatus(String url){
		List<PageLink> pageLink = linkManager.getPageLink(url);
		for(PageLink s: pageLink){
			linkManager.updatePageLinkStatus(s.getUri());
		}
		return true;
	}
	
	/* Spring bean setter */
	public void setLinkManager(LinkManager linkManager){
		this.linkManager = linkManager;
	}
	
	// TODO remove this
	// ONLY FOR TESTING
	public String getString(String a){
		return "Test success: "+a;
	}
}

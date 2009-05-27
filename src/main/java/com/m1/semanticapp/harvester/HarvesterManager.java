package com.m1.semanticapp.harvester;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class HarvesterManager extends SimpleJdbcDaoSupport {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public HarvesterManager(){}
	
	public List<HarvesterJob> getJob(){
		String GET_JOB_LIST_SQL = "SELECT * FROM harvester_job";
		List<HarvesterJob> job = getSimpleJdbcTemplate().query(
				GET_JOB_LIST_SQL, new HarvesterJobMapper());
		return job;
	}
	
	public void addJob(HarvesterJob job){
		String SQL_INSERT_JOB = "INSERT INTO harvester_job (id, starturl, sitename, depth) values (0,:starturl,:sitename,:depth)";
		int insertJob = getSimpleJdbcTemplate().update(SQL_INSERT_JOB, 
				new MapSqlParameterSource()
				.addValue("starturl", job.getStartUrl())
				.addValue("sitename", job.getSiteName())
				.addValue("depth", job.getDepth())
		);
		logger.info("INSERTED job, rows affected: " + insertJob);
	}
	
	public void deleteJob(HarvesterJob job){
		String SQL_DELETE_JOB = "DELETE FROM harvester_job WHERE id = ?";	
		int deleteJob = getSimpleJdbcTemplate().update(SQL_DELETE_JOB, job.getId());
		logger.info("DELETED job, rows affected: " + deleteJob);
	}
	
	public void clearJob(){
		String SQL_TRUNCATE_JOB = "TRUNCATE TABLE harvester_job";
		int deleteJob = getSimpleJdbcTemplate().update(SQL_TRUNCATE_JOB);
		logger.info("TRUNCATED TABLE job, rows affected: " + deleteJob);
	}
	
	/* This method returns all sites to be crawled
	 * @param none;
	 * @return List<HarvesterSite>
	 */
	public List<HarvesterSite> getAllSites(){
		String GET_JOB_LIST_SQL = "SELECT * FROM harvester_site WHERE 1=1";
		List<HarvesterSite> site = getSimpleJdbcTemplate().query(
				GET_JOB_LIST_SQL, new HarvesterSiteMapper());
		return site;
	}
	
	public HarvesterSite getSiteById(int id){		
		String GET_SITE_LIST_SQL = "SELECT * FROM harvester_site WHERE id="+id;
		List<HarvesterSite> siteList = getSimpleJdbcTemplate().query(
				GET_SITE_LIST_SQL, new HarvesterSiteMapper());
		
		//TODO fix this ugly hack
		HarvesterSite site = new HarvesterSite();
		for(HarvesterSite s: siteList){
			site.setId(s.getId());
			site.setStartUrl(s.getStartUrl());
			site.setSiteName(s.getSiteName());
			site.setIncludePattern(s.getIncludePattern());
			site.setExcludePattern(s.getExcludePattern());
			site.setDepth(s.getDepth());
		}
		return site;
	}
	
	public void addSite(HarvesterSite site){
		String SQL_INSERT_SITE = "INSERT INTO harvester_site (id, starturl, sitename, depth, includepattern, excludepattern) values (0,:starturl,:sitename,:depth, :includepattern, :excludepattern)";
		int insertJob = getSimpleJdbcTemplate().update(SQL_INSERT_SITE, 
				new MapSqlParameterSource()
				.addValue("starturl", site.getStartUrl())
				.addValue("sitename", site.getSiteName())
				.addValue("depth", site.getDepth())
				.addValue("includepattern", site.getIncludePattern())
				.addValue("excludepattern", site.getIncludePattern())
		);
		logger.info("INSERTED site, rows affected: " + insertJob);
	}
	
	public void deleteSite(int id){
		String SQL_DELETE_SITE = "DELETE FROM harvester_site WHERE id = ?";	
		int deleteJob = getSimpleJdbcTemplate().update(SQL_DELETE_SITE, id);
		logger.info("DELETED site, rows affected: " + deleteJob);
	}
	
	public void updateSite(HarvesterSite site){
		String SQL_DELETE_SITE = "UPDATE harvester_site SET starturl=?, sitename=?, depth=?, includepattern=?, excludepattern=?  WHERE id = ?";	
		int deleteJob = getSimpleJdbcTemplate().update(SQL_DELETE_SITE, site.getStartUrl(), site.getSiteName(), site.getDepth(), site.getIncludePattern(), site.getExcludePattern(), site.getId());
		logger.info("UPDATED site, rows affected: " + deleteJob);
	}
	
	private static class HarvesterSiteMapper implements ParameterizedRowMapper<HarvesterSite> {
		public HarvesterSite mapRow(ResultSet rs, int rowNum) throws SQLException {
			HarvesterSite site = new HarvesterSite();
			site.setId(rs.getInt("id"));
			site.setStartUrl(rs.getString("starturl"));
			site.setSiteName(rs.getString("sitename"));
			site.setDepth(rs.getInt("depth"));
			site.setIncludePattern(rs.getString("includepattern"));
			site.setExcludePattern(rs.getString("excludepattern"));
			return site;
		}
	}
	
	private static class HarvesterJobMapper implements ParameterizedRowMapper<HarvesterJob> {
		public HarvesterJob mapRow(ResultSet rs, int rowNum) throws SQLException {
			HarvesterJob job = new HarvesterJob();
			job.setId(rs.getInt("id"));
			job.setStartUrl(rs.getString("starturl"));
			job.setSiteName(rs.getString("sitename"));
			job.setDepth(rs.getInt("depth"));
			return job;
		}
	}
	
	// EARLY TESTS
	public static void main(String[] args) throws Exception {
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("./WebContent/WEB-INF/applicationContext.xml"));
		HarvesterManager harvesterManager = (HarvesterManager) factory.getBean("harvesterManager");
		List<HarvesterSite> site = harvesterManager.getAllSites();
		
		for(HarvesterSite s: site){
			System.out.println(""+
					"      Id: "+s.getId()+"\n"+
					"Starturl: "+s.getStartUrl()+"\n"+
					"Sitename: "+s.getSiteName()+"\n"+
					"   Depth: "+s.getDepth()+"\n"+
					"IncludeP: "+s.getIncludePattern()+"\n"+
					"ExcludeP: "+s.getExcludePattern()+"\n"+
					"");
		}
	}
	
}

package com.m1.semanticapp.link;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class PageLinkStatusDao extends SimpleJdbcDaoSupport {
	
	private HttpStatusResponse httpStatusResponse;
	private String responseMessage;
	private int responseCode;
	private int active;
	
	public PageLinkStatusDao(){}
	
	public void updatePageLinkStatus(String siteUrl){
		active = 1;
		httpStatusResponse = new HttpStatusResponse();
		responseCode = httpStatusResponse.getResponseCode(siteUrl);
		responseMessage = httpStatusResponse.getTranslatedResponseMessage(responseCode);
		
		/* If the response code is above 300 and below 500
		 * this should indicate that the URL cannot be found or that it is
		 * moved and should be flagged as such
		 */
		
		if(responseCode > 300 && responseCode < 500){
			int active = 1;
			System.out.println(responseMessage);
			System.out.println(responseCode);
			System.out.println(siteUrl);
			String UPDATE_RELATED_LINKS_SQL = "UPDATE `related_links` SET active=:active, code=:responseCode, msg=:responseMessage WHERE uri=:uri";
			int insertJob = getSimpleJdbcTemplate().update(UPDATE_RELATED_LINKS_SQL, 
					new MapSqlParameterSource().addValue("active", active)
					.addValue("responseCode", responseCode)
					.addValue("responseMessage", responseMessage)
					.addValue("uri", siteUrl)
			);
		}
	}
}

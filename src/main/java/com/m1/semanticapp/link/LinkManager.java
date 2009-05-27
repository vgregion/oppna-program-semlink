package com.m1.semanticapp.link;

import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

public class LinkManager {
	
	private PageLinkDao pageLink;
	private PageLinkStatusDao pageLinkStatus;
	private List<PageLink> link;
	
	/** Constructor */
	public LinkManager(){}
	
	/**
	 * Flags the dead links in the database
	 * @param siteUrl - String
	 */
	public void updatePageLinkStatus(String siteUrl){
		pageLinkStatus.updatePageLinkStatus(siteUrl);
	}
	
	public List<PageLink> getPageLink(String url) {
		return pageLink.getPageLink(url);
	}
	
	public void addPageLink(List<PageLink> referrer) {
		pageLink.addPageLink(referrer);
	}
	
	public void deleteAllPageLink(String url){
		pageLink.deleteAllPageLink(url);
	}
	
	// setters and getters
	public void setPageLinkDao(PageLinkDao pageLink){
		this.pageLink = pageLink;
	}
	
	public void setPageLinkStatusDao(PageLinkStatusDao pageLinkStatus){
		this.pageLinkStatus = pageLinkStatus;
	}
	
}

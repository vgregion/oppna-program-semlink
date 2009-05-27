package com.m1.semanticapp.harvester;

import java.io.Serializable;

public class HarvesterSite implements Serializable {

	/**
	 * Individual in a CrawlerSiteJob list
	 */
	
	private static final long serialVersionUID = 8241345418494928628L;
	private int id;
	private String startUrl;
	private String siteName;
	private int depth;
	private String includePattern;
	private String excludePattern;

	public HarvesterSite(){
		
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getDepth() {
		return depth;
	}

	public void setIncludePattern(String includePattern) {
		this.includePattern = includePattern;
	}

	public String getIncludePattern() {
		return includePattern;
	}

	public void setExcludePattern(String excludePattern) {
		this.excludePattern = excludePattern;
	}

	public String getExcludePattern() {
		return excludePattern;
	}

}

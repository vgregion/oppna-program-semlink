package com.m1.semanticapp.harvester;

public class HarvesterJob {
	/**
	 * Normal POJO for harvester jobs
	 */
	private int id;
	private String startUrl;
	private String siteName;
	private int depth;
		
	public HarvesterJob(){}

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
	
}

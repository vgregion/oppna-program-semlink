package com.m1.semanticapp.repository;

public class RdfRepository {

	private String repositoryDir; // path to respository directory
	private String repositoryName; // name of the respository
	
	private String accessDataDir; // path to data respository directory
	private String accessDataName; // name of the data respository
	
	
	public RdfRepository(){}


	public void setRepositoryDir(String repositoryDir) {
		this.repositoryDir = repositoryDir;
	}


	public String getRepositoryDir() {
		return repositoryDir;
	}


	public void setRepositoryName(String repositoryName) {
		this.repositoryName = repositoryName;
	}


	public String getRepositoryName() {
		return repositoryName;
	}


	public void setAccessDataDir(String accessDataDir) {
		this.accessDataDir = accessDataDir;
	}


	public String getAccessDataDir() {
		return accessDataDir;
	}


	public void setAccessDataName(String accessDataName) {
		this.accessDataName = accessDataName;
	}


	public String getAccessDataName() {
		return accessDataName;
	}

}

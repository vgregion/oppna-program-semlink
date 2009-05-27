package com.m1.semanticapp.service.webservice;

import java.util.List;

import com.m1.semanticapp.harvester.HarvesterJob;
import com.m1.semanticapp.harvester.HarvesterManager;

public class HarvesterJobService {

	private HarvesterManager harvesterManager;
	
	public List<HarvesterJob> getJob(){
		return this.harvesterManager.getJob();
	}
	
	public void addJob(HarvesterJob job){
		this.harvesterManager.addJob(job);
	}
	
	public void setHarvesterManager(HarvesterManager harvesterManager){
		this.harvesterManager = harvesterManager;
	}
}

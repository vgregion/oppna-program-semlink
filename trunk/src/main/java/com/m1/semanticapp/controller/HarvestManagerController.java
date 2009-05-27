package com.m1.semanticapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.m1.semanticapp.harvester.HarvesterManager;

public class HarvestManagerController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private HarvesterManager harvesterManager;

	//@Override
	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("harvesterManager", this.harvesterManager.getAllSites());
		
		return new ModelAndView("harvestmanager", "model", model);
	}
	
	public void setHarvesterManager(HarvesterManager harvesterManager) {
		this.harvesterManager = harvesterManager;
	}
}


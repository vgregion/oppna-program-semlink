package se.vgr.crawler.presentation.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import se.vgr.crawler.service.SourceService;


public class ListSourceController implements Controller{
	
	private SourceService sourceService;

	public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("harvesterManager", this.sourceService.getAll());
		
		return new ModelAndView("harvestmanager", "model", model);
		
	}
	
	public void setSourceService(SourceService sourceService) {
		this.sourceService = sourceService;
	}
	
}
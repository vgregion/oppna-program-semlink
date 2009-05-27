package com.m1.semanticapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.m1.semanticapp.harvester.HarvesterManager;
import com.m1.semanticapp.harvester.HarvesterSite;

@Controller
@RequestMapping("/app/deleteSite.htm")
@SessionAttributes("harvester")
public class HarvesterSiteDeleteController {
	
	private final HarvesterManager harvesterManager;

	@Autowired
	public HarvesterSiteDeleteController(HarvesterManager harvesterManager) {
		this.harvesterManager = harvesterManager;
	}

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(new String[] {"id"});
    }
    
    @RequestMapping(method = RequestMethod.GET)
	public String setupForm(@RequestParam(required=false) Integer id, Model model) {
    	HarvesterSite harvesterSite = new HarvesterSite();
		model.addAttribute("harvester", harvesterSite);
		model.addAttribute("new", true);
		return "harvesterForm";
	}
    
    @RequestMapping(method=RequestMethod.POST)
    public String delete(@RequestParam(required=true) Integer id) {
    	System.out.println("DELETING IN ACTION, id: "+id);
    	this.harvesterManager.deleteSite(id);
    	return "redirect:harvestmanager.htm";
    }
    
}

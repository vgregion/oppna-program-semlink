package com.m1.semanticapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.m1.semanticapp.harvester.HarvesterManager;
import com.m1.semanticapp.harvester.HarvesterSite;
import com.m1.semanticapp.controller.validator.HarvesterSiteValidator;

@Controller
@RequestMapping("/app/editSite.htm")
@SessionAttributes("harvester")
public class HarvesterSiteEditController {

	private final HarvesterManager harvesterManager;

	@Autowired
	public HarvesterSiteEditController(HarvesterManager harvesterManager) {
		this.harvesterManager = harvesterManager;
	}

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(new String[] {"id"});
    }

    @RequestMapping(method = RequestMethod.GET)
	public String setupForm(@RequestParam(required=false) Integer id, Model model) {
    	
    	HarvesterSite harvesterSite = new HarvesterSite();
    	
    	if(id == null){
    		
    	} else{
    		harvesterSite = new HarvesterSite();
    		harvesterSite = this.harvesterManager.getSiteById(id);
    	}
    	
		model.addAttribute("harvester", harvesterSite);
		model.addAttribute("new", false);
		return "harvesterForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("harvester") HarvesterSite harvesterSite, BindingResult result, Model model, SessionStatus status) {
		new HarvesterSiteValidator().validate(harvesterSite, result);
		if (result.hasErrors()) {
			return "harvesterForm";
		}
		else {
			this.harvesterManager.updateSite(harvesterSite);
			status.setComplete();
			model.addAttribute("success", "success.edit");
			return "harvesterForm";
		}
	}

}
package se.vgr.crawler.presentation.controller;

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

import se.vgr.crawler.entity.Source;
import se.vgr.crawler.presentation.validator.Validation;
import se.vgr.crawler.service.SourceService;

@Controller
@RequestMapping("/secure/addSite.htm")
@SessionAttributes("harvester")
public class AddSourceController {

	private final SourceService sourceService;
	
	@Autowired
	public AddSourceController(SourceService sourceService) {
		this.sourceService = sourceService;
	}

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields(new String[] {"id"});
    }

    @RequestMapping(method = RequestMethod.GET)
	public String setupForm(@RequestParam(required=false) Integer id, Model model) {
    	
    	Source source = new Source();
		model.addAttribute("harvester", source);
		model.addAttribute("new", true);
		return "harvesterForm";
		
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("harvester") Source source, BindingResult result, Model model, SessionStatus status) {
		
		new Validation().validate(source, result);
		
		if (result.hasErrors()) {
			
			return "harvesterForm";
			
		} else {
			
			this.sourceService.add(source);
			status.setComplete();
			model.addAttribute("success", "success.edit");
			return "redirect:harvestmanager.htm";
			
		}
		
	}
	
}

package se.vgr.crawler.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import se.vgr.crawler.entity.Source;
import se.vgr.crawler.service.SourceService;

@Controller
@RequestMapping("/secure/deleteSite.htm")
@SessionAttributes("harvester")
public class DeleteSourceController {

	private final SourceService sourceService;

	@Autowired
	public DeleteSourceController(SourceService sourceService) {
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
    
    @RequestMapping(method=RequestMethod.POST)
    public String delete(@RequestParam(required=true) Integer id) {
    	System.out.println("DELETING IN ACTION, id: "+id);
    	this.sourceService.delete(id);
    	return "redirect:harvestmanager.htm";
    }
    
}

package com.m1.semanticapp.controller.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.PatternSyntaxException;

import org.semanticdesktop.aperture.datasource.config.RegExpPattern;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.m1.semanticapp.harvester.HarvesterSite;

public class HarvesterSiteValidator implements Validator {

	//@Override
	public boolean supports(Class c) {
		return HarvesterSite.class.equals(c);
	}

	//@Override
	public void validate(Object obj, Errors errors) {
		HarvesterSite harvesterSite = (HarvesterSite) obj;
		
		// cant have empty input... db doesnt like that... neither does the crawler 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "startUrl", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "siteName", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depth", "error.required");
		
		// needs to start with http or https
		if(harvesterSite.getStartUrl().startsWith("http") || harvesterSite.getStartUrl().startsWith("https")){		
			//passed that test so lets try to convert it
			try {
                new URL(harvesterSite.getStartUrl());
			}catch (MalformedURLException e) {
				errors.rejectValue("startUrl", "error.noturl");
            }
			
		}else{
			errors.rejectValue("startUrl", "error.noturl");
		}
		// needs to start with http or https
		if(harvesterSite.getSiteName().startsWith("http") || harvesterSite.getSiteName().startsWith("https")){
			//passed that test so lets try to convert it
			try {
                new URL(harvesterSite.getSiteName());
			}catch (MalformedURLException e) {
				errors.rejectValue("siteName", "error.noturl");
            }
		}else{
			errors.rejectValue("siteName", "error.noturl");
		}
		
		// check depth
		if(harvesterSite.getDepth() < -1 || harvesterSite.getDepth()>10){
			errors.rejectValue("depth", "error.depth");
		}
		
		
		// just check that both are assigned
		if (!StringUtils.hasLength(harvesterSite.getIncludePattern()) && StringUtils.hasLength(harvesterSite.getExcludePattern())) {
			errors.rejectValue("includePattern", "error.notassigned");
		}
		
		if (StringUtils.hasLength(harvesterSite.getIncludePattern()) && !StringUtils.hasLength(harvesterSite.getExcludePattern())) {
			errors.rejectValue("excludePattern", "error.notassigned");
		}
		
		// if both assigned, check if its regEx syntax
		if (StringUtils.hasLength(harvesterSite.getIncludePattern()) && StringUtils.hasLength(harvesterSite.getExcludePattern())) {	
			try {
				new RegExpPattern(harvesterSite.getIncludePattern());
			} catch(PatternSyntaxException e){
				errors.rejectValue("includePattern", "error.notregex");
			}
		
			try {
				new RegExpPattern(harvesterSite.getExcludePattern());
			} catch(PatternSyntaxException e){
				errors.rejectValue("excludePattern", "error.notregex");
			}		
		}
	}
}

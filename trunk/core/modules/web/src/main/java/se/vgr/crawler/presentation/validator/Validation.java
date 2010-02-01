package se.vgr.crawler.presentation.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.PatternSyntaxException;

import org.semanticdesktop.aperture.datasource.config.SubstringCondition;
import org.semanticdesktop.aperture.datasource.config.SubstringPattern;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import se.vgr.crawler.entity.Source;

public class Validation implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class c) {
		return Source.class.equals(c);
	}

	public void validate(Object obj, Errors errors) {
		
		Source source = (Source) obj;
		
		// cant have empty input... db doesnt like that... neither does the crawler 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rootUrl", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "source", "error.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depth", "error.required");
		
		// needs to start with http or https
		
		if ( source.getRootUrl().startsWith("http") || source.getRootUrl().startsWith("https") ) {		
			
			try {
            
				new URL( source.getRootUrl() );
			
			} catch ( MalformedURLException e ) {
				
				errors.rejectValue("rootUrl", "error.noturl");
            
			}
			
		} else {
			
			errors.rejectValue("rootUrl", "error.noturl");
		
		}
		
		
		if ( source.getDepth() != null && source.getDepth() < -1 || source.getDepth() > 10 ) {
			errors.rejectValue("depth", "error.depth");
		}
		
		
		if ( StringUtils.hasLength( source.getWhiteListPattern() ) ) {
			
			try {
				
				new SubstringPattern(source.getWhiteListPattern(), SubstringCondition.STARTS_WITH);
			
			} catch( PatternSyntaxException e ){
				
				errors.rejectValue("whiteListPattern", "error.notregex");
			
			}
		
		} else {
			
			errors.rejectValue("whiteListPattern", "error.notassigned");
			
		}
		
		if ( StringUtils.hasLength( source.getBlackListPattern()) ) {
			
			try {
				
				new SubstringPattern(source.getBlackListPattern(), SubstringCondition.STARTS_WITH);
			
			} catch( PatternSyntaxException e ) {
				
				errors.rejectValue("blackListPattern", "error.notregex");
			
			}
			
		}
		
		
	}
	
}

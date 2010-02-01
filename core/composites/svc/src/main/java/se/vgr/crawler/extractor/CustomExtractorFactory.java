package se.vgr.crawler.extractor;

import java.util.List;

import org.semanticdesktop.aperture.extractor.ExtractorFactory;
import org.semanticdesktop.aperture.extractor.FileExtractorFactory;
import org.semanticdesktop.aperture.extractor.impl.ExtractorRegistryImpl;

/**
 * Custom extractor factory
 * @author Johan Säll Larsson
 */

public class CustomExtractorFactory extends ExtractorRegistryImpl {

	private static List<?> extractors;

	public CustomExtractorFactory(){}
	
	public void init(){
		
		for ( Object extractor : extractors ){
			
			if (extractor instanceof ExtractorFactory) {
				
                ExtractorFactory factory = (ExtractorFactory) extractor;
                add(factory);
                
            } else if (extractor instanceof FileExtractorFactory) {
            	
                FileExtractorFactory factory = (FileExtractorFactory) extractor;
                add(factory);
                
            }
			
		}
		
	}
	
	public void setExtractors(List<?> extractors) {
		CustomExtractorFactory.extractors = extractors;
	}
	
	
}

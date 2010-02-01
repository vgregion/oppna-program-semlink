package se.vgr.crawler.extractor.processor;
import java.util.Iterator;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.vocabulary.RDF;

import org.semanticdesktop.aperture.rdf.RDFContainer;
import org.semanticdesktop.aperture.util.UriUtil;
import org.semanticdesktop.aperture.vocabulary.NCO;
import org.semanticdesktop.aperture.vocabulary.NFO;

import se.vgr.crawler.util.HtmlParserUtil;
import se.vgr.crawler.util.StringUtils;
import se.vgr.crawler.vocabulary.DC;
import se.vgr.crawler.vocabulary.DCTERMS;
import se.vgr.crawler.vocabulary.SKOS;

public class TextProcessor extends HtmlParserUtil.ContentExtractor {

    private RDFContainer container;

    public TextProcessor(RDFContainer container) {
        this.container = container;
    }

    public void finishedParsing() {

        container.add(RDF.type, NFO.HtmlDocument);
        container.add(RDF.type, SKOS.concept);
        
        // uncomment to add text
        //container.add(NIE.plainTextContent, StringUtils.removeHtmlTags(getText()));

        Iterator<String> keywords = getKeywords();
        while (keywords.hasNext()) {
        	addProperty(DC.subject, keywords.next());
        }
        
        addProperty(DC.title, getTitle());
        addProperty(DC.description, StringUtils.encodeHtml(getDescription()));
        addProperty(DC.publisher, getAuthor());
        addContactProperty(NCO.creator, getAuthor());
        addProperty(DCTERMS.audience, getAudience());
        addProperty(DC.language, getLanguage());
        addProperty(DCTERMS.mesh, getMesh());
        
    }
    
    private void addContactProperty(URI property, String fullname) {
        
    	if ( fullname != null ) {
            
    		fullname = fullname.trim();
            Model model = container.getModel();
            Resource contactResource = UriUtil.generateRandomResource(model);
            model.addStatement(contactResource,RDF.type,NCO.Contact);
            model.addStatement(contactResource,NCO.fullname,fullname);
            container.add(property, contactResource);
            
        }
    	
    }

    private void addProperty(URI property, String value) {
    	
    	if (value != null) {
    		
    		value = value.trim();
    		
    		if (value.length() > 0) {
    			container.add(property, value);
    		}
    		
    	}
    	
    }
    
}


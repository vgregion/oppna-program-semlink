package com.m1.semanticapp.harvester.extractor.impl;


import java.util.Iterator;

import java.util.List;

import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.node.Resource;
import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.vocabulary.RDF;

import org.semanticdesktop.aperture.extractor.util.HtmlParserUtil;
import org.semanticdesktop.aperture.rdf.RDFContainer;
import org.semanticdesktop.aperture.util.UriUtil;
import org.semanticdesktop.aperture.vocabulary.NCO;
import org.semanticdesktop.aperture.vocabulary.NFO;
import com.m1.semanticapp.vocabularies.NIE;

import com.apelon.dts.client.DTSException;
import com.apelon.dts.client.concept.OntylogConcept;
import com.m1.semanticapp.taxonomy.TaxonomyClient;
import com.m1.semanticapp.stopword.KeywordStop;

public class TextAndMetadataExtractor extends HtmlParserUtil.ContentExtractor {

    private RDFContainer container;
    private TaxonomyClient taxonomyClient;

    public TextAndMetadataExtractor(RDFContainer container, TaxonomyClient taxonomyClient) {
        this.container = container;
        this.taxonomyClient = taxonomyClient;
    }

    public void finishedParsing() {
        // add an appropriate InformationElement type
        container.add(RDF.type,NFO.HtmlDocument);
        KeywordStop ks = new KeywordStop();
        
        // store extracted text
        //container.add(NIE.plainTextContent, getText());
        //FIXME gör en stemmer + stopplista påen ny pproperty här
        
        //TODO
        //System.out.println("2 Detta är taxonomins namespace: "+taxonomyClient.getNamespace());
        //taxonomyClient.initialize();

		
       
        // store keywords
        //FIXME change here iterator <?>
        Iterator<?> keywords = getKeywords();
        String keyword;
        while (keywords.hasNext()) {
        	keyword = keywords.next().toString();
        	if(ks.isStopword(keyword)){
        		System.out.println("Stoppord: "+keyword);
        	}else{
        		System.out.println("Not stopword: "+keyword);
        		/*OntylogConcept[] cons;
				try {
					cons = taxonomyClient.taxonomyLookup(keyword);
            		if(cons.length==1){
            			
            			String concept = taxonomyClient.getUniqueID(cons[0]);
            			addProperty(NIE.Concept, concept);
            			List<String> synonyms = taxonomyClient.getSynonyms(cons[0]);
            			for(String s: synonyms){
            				System.out.println("Synonyms: "+s);
            				addProperty(NIE.altLabel, s);
            			}
            			String pref = taxonomyClient.getPreferredTerm(cons[0]);
            			addProperty(NIE.prefLabel, pref);
            			
            		} else{
            			//TODO stoppa in i blacklist
            			addProperty(NIE.keyword, keyword);
            		}
				} catch (DTSException e) {
					e.printStackTrace();
				}*/
        		addProperty(NIE.keyword, keyword); // this is test so remove me
        	}
        }
        
        //taxonomyClient.close();
        
        // store other metadata
        addProperty(NIE.title, getTitle());
        addContactProperty(NCO.creator, getAuthor());
        addProperty(NIE.description, getDescription());
    }
    
    private void addContactProperty(URI property, String fullname) {
        if (fullname != null) {
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

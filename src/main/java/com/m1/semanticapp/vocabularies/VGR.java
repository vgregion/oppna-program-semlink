package com.m1.semanticapp.vocabularies;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class VGR {

	/** 
	 * <p>The RDF model that holds the vocabulary terms</p> 
	 * */
	public static Model m_model = ModelFactory.createDefaultModel();
	   
	/** 
	 * <p>The namespace of the vocabulary as a string ({@value})</p> 
	 * */
	public static final String NS = "http://www.semanticdesktop.org/ontologies/2007/01/19/nie#";
	   
	public static final Resource NAMESPACE = m_model.createResource( NS );
	   
	public static final Property keyword = m_model.createProperty( "http://www.semanticdesktop.org/ontologies/2007/01/19/nie#keyword" );
	   
	public static final Property title = m_model.createProperty( "http://www.semanticdesktop.org/ontologies/2007/01/19/nie#title" );
	   
	public static final Property description = m_model.createProperty( "http://www.semanticdesktop.org/ontologies/2007/01/19/nie#description" );
	   
	public static final Property htmlDocument = m_model.createProperty( "http://www.semanticdesktop.org/ontologies/2007/01/19/nie#HtmlDocument" );
	
	public static final Property rootElementOf = m_model.createProperty( "http://www.semanticdesktop.org/ontologies/2007/01/19/nie#rootElementOf" );
}

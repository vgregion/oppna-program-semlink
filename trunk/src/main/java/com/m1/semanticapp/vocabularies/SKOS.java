package com.m1.semanticapp.vocabularies;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;

public class SKOS {
	/** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model m_model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string ({@value})</p> */
    public static final String NS = "http://www.w3.org/2004/02/skos/core#";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = m_model.createResource( NS );
    
    /** <p>This property carries very weak semantics. It may be used to state that the 
     *  object is in some way more general in meaning than the subject. Essentially 
     *  it provides a means of organising concepts into hierarchical structures, without 
     *  being restrictive about the exact semantic implications of the hierarchical 
     *  structure itself. Extend this property to create properties that carry stronger 
     *  semantics, but may be reduced to a hierarchical structure for simple visual 
     *  displays. This property may be considered to be transitive.</p>
     */
    public static final Property broader = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#broader" );
    
    /** <p>An extension of the 'broader' property to specify a partitive relationship 
     *  between two concepts.</p>
     */
    public static final Property broaderPartitive = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#broaderPartitive" );
    
    /** <p>Use this property to indicate a literal which is the preferred label for a 
     *  resource. If a resource has this property, all other rdfs:label properties 
     *  are considered to be the 'alternative' (i.e. non-preferred) labels.</p>
     */
    public static final Property prefLabel = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#prefLabel" );
    
    /** <p>The "preferred" and "alternate" labels are useful when generating or 
     *  creating human-readable representations of a knowledge organization system. 
     *  These labels provide the strongest clues as to the meaning of a SKOS concept.
     * </p>
     */
    public static final Property altLabel = m_model.createProperty("http://www.w3.org/2004/02/skos/core#altLabel");
    
    /**
     * <p>The "hidden" labels are useful when a user is interacting with a knowledge 
     * organization system via a text-based search function. The user may, for example, 
     * enter mis-spelled words when trying to find a relevant concept. If the mis-spelled 
     * query can be matched against a "hidden" label, the user will be able to find 
     * the relevant concept, but the "hidden" label won't otherwise be visible to the 
     * user (so further mistakes aren't encouraged).
     */
    
    public static final Property hiddenLabel = m_model.createProperty("http://www.w3.org/2008/05/skos#hiddenLabel");
    
    /** <p>An 'externalID' is any non-lexical identifying code that is used to uniquely 
     *  identify a concept within a conceptual scheme.</p>
     */
    public static final Property externalID = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#externalID" );
    
    /** <p>An extension of the 'narrower' property to specify a partitive relationship 
     *  between two concepts. This property should be considered to be the inverse 
     *  of 'broaderPartitive'.</p>
     */
    public static final Property narrowerPartitive = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#narrowerPartitive" );
    
    /** <p>A scope note is a piece of text that in some way helps to further elucidate 
     *  the intended meaning of a concept.</p>
     */
    public static final Property scopeNote = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#scopeNote" );
    
    /** <p>A 'descriptor' is a label that uniquely identifies a concept within a conceptual 
     *  scheme. A descriptor must be unambiguous. Examples of good descriptors are 
     *  'Orange (fruit)' and 'Java programming language'. Examples of poor descriptors 
     *  are 'Orange' and 'Java'.</p>
     */
    public static final Property descriptor = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#descriptor" );
    
    /** <p>This property carries very weak semantics. It may be used to state that that 
     *  the object is in some way related to the subject, and the nature of that relationship 
     *  is NOT to be treated as hierarchical. Extend this property to create properties 
     *  with stronger semantics, but may still be reduced to an associative structure 
     *  for simple visual display. This property should be considered to be symmetric.</p>
     */
    public static final Property related = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#related" );
    
    /** <p>An extension of the 'narrower' property to specify the instantiation relationship 
     *  between two concepts. This property should be considered the inverse of 'broaderInstantive'.</p>
     */
    public static final Property narrowerInstantive = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#narrowerInstantive" );
    
    public static final Property generalNote = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#generalNote" );
    
    /** <p>A note providing information about the history of change of a concept.</p> */
    public static final Property historyNote = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#historyNote" );
    
    /** <p>An extension of the 'broader' property to specify the instantiation relationship 
     *  between two concepts. This property is semantically equivalent to the 'rdf:type' 
     *  property.</p>
     */
    public static final Property broaderInstantive = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#broaderInstantive" );
    
    /** <p>This property indicates that a concept is a member of a facet. A concept may 
     *  have only one inFacet property. This property is a sub-property of the 'broader' 
     *  property. Thus faceted conceptual structures may be reduced to simple hierarchical 
     *  displays by applications that do not comprehend facets.</p>
     */
    public static final Property inFacet = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#inFacet" );
    
    /** <p>A note on the hierarchical location of a concept.</p> */
    public static final Property hierarchyNote = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#hierarchyNote" );
    
    public static final Property editorNote = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#editorNote" );
    
    /** <p>This property should be considered to be the inverse of the 'broader' property.</p> */
    public static final Property narrower = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#narrower" );
    
    /** <p>An extension of the 'broader' property to specify the class subsumption relationship 
     *  between two concepts. This property is semantically equivalent to the 'rdfs:subClassOf' 
     *  property.</p>
     */
    public static final Property broaderGeneric = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#broaderGeneric" );
    
    /** <p>This property is the super-property of all properties used to make statements 
     *  about how concepts relate to each other.</p>
     */
    public static final Property semanticRelation = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#semanticRelation" );
    
    /** <p>An extension of the 'narrower' property to specify the class subsumption relationship 
     *  between two concepts. This property should be considered the inverse of 'broaderGeneric'.</p>
     */
    public static final Property narrowerGeneric = m_model.createProperty( "http://www.w3.org/2004/02/skos/core#narrowerGeneric" );
    
    /** <p>Facets provide a means of organising concepts along orthogonal dimensions. 
     *  A facet is treated as a concept. A facet may have member concepts. A concept 
     *  may be a member of only one facet.</p>
     */
    public static final Resource Facet = m_model.createResource( "http://www.w3.org/2004/02/skos/core#Facet" );
    
    /** <p>Use this class to indicate that a node may be considered to be an abstract 
     *  concept that is part of some conceptual scheme such as a thesaurus.</p>
     */
    public static final Resource Concept = m_model.createResource( "http://www.w3.org/2004/02/skos/core#Concept" );

	public static final URI CONCEPT = new URIImpl("http://www.w3.org/2004/02/skos/core#Concept");
	
}

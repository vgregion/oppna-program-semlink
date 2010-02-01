package se.vgr.crawler.vocabulary;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * SKOS vocabulary
 * @see http://www.w3.org/TR/2009/REC-skos-reference-20090818/
 * @author Johan Säll Larsson
 */

public class SKOS {
	
    
    public static final String NS = "http://www.w3.org/2004/02/skos/core#";
    
    public static final URI prefLabel = new URIImpl(  NS + "prefLabel" );
    
    public static final URI altLabel = new URIImpl( NS + "altLabel" );
    
    public static final URI hiddenLabel = new URIImpl( NS + "hiddenLabel" );
    
    public static final URI related = new URIImpl( NS + "related" );

	public static final URI concept = new URIImpl( NS + "Concept");
	
}

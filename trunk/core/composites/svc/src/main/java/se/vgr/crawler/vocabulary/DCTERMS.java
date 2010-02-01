package se.vgr.crawler.vocabulary;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Dublin core DC.Terms vocabulary
 * @see http://dublincore.org
 * @author Johan Säll Larsson
 */

public class DCTERMS {

	public static final String NS = "http://purl.org/dc/terms/";
	
	public static final URI audience = new URIImpl(NS+"audience");
	
	public static final URI mesh = new URIImpl(NS+"mesh");
	
}
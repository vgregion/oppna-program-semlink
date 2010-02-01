package se.vgr.crawler.vocabulary;

import org.ontoware.rdf2go.model.node.URI;
import org.ontoware.rdf2go.model.node.impl.URIImpl;

/**
 * Dublin core DC vocabulary
 * @see http://dublincore.org
 * @author Johan Säll Larsson
 */

public class DC {
	
	public static final String NS = "http://purl.org/dc/elements/1.1/";
	
	public static final URI title = new URIImpl(NS+"title");
	
	public static final URI language = new URIImpl(NS+"language");
	
	public static final URI publisher = new URIImpl(NS+"publisher");
	
	public static final URI description = new URIImpl(NS+"description");
	
	public static final URI subject = new URIImpl(NS+"subject");
	
}
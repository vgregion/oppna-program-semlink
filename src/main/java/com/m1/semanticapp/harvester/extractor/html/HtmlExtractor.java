package com.m1.semanticapp.harvester.extractor.html;

import java.io.InputStream;
import java.nio.charset.Charset;
import org.ontoware.rdf2go.model.node.URI;

import org.semanticdesktop.aperture.extractor.Extractor;
import org.semanticdesktop.aperture.extractor.ExtractorException;
import org.semanticdesktop.aperture.extractor.util.HtmlParserUtil;
import org.semanticdesktop.aperture.rdf.RDFContainer;
import com.m1.semanticapp.taxonomy.TaxonomyClient;
import com.m1.semanticapp.harvester.extractor.impl.TextAndMetadataExtractor;

public class HtmlExtractor implements Extractor {
	
    public void extract(URI id, InputStream stream, Charset charset, String mimeType, RDFContainer result) throws ExtractorException {
    	// We do not use this
    }
    
    public void extraction(URI id, InputStream stream, Charset charset, String mimeType, RDFContainer result, TaxonomyClient taxonomyClient) throws ExtractorException {
    	HtmlParserUtil.parse(stream, charset, new TextAndMetadataExtractor(result, taxonomyClient));
    }
}
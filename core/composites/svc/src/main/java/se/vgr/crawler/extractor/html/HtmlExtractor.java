package se.vgr.crawler.extractor.html;

import java.io.InputStream;
import java.nio.charset.Charset;

import org.ontoware.rdf2go.model.node.URI;
import org.semanticdesktop.aperture.extractor.Extractor;
import org.semanticdesktop.aperture.extractor.ExtractorException;

import org.semanticdesktop.aperture.rdf.RDFContainer;

import se.vgr.crawler.extractor.processor.TextProcessor;
import se.vgr.crawler.util.HtmlParserUtil;

public class HtmlExtractor implements Extractor {
	
    public void extract(URI id, InputStream stream, Charset charset, String mimeType, RDFContainer result) throws ExtractorException {
    	HtmlParserUtil.parse(stream, charset, new TextProcessor(result));
    }

}

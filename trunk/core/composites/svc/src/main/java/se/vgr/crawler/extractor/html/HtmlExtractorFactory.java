package se.vgr.crawler.extractor.html;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticdesktop.aperture.extractor.Extractor;
import org.semanticdesktop.aperture.extractor.ExtractorFactory;

public class HtmlExtractorFactory implements ExtractorFactory {

	private static final Set<String> MIME_TYPES;

	static {
		HashSet<String> set = new HashSet<String>();
		set.add("text/html");
		set.add("application/xhtml+xml");

		MIME_TYPES = Collections.unmodifiableSet(set);
	}

	public Extractor get() {
		return new HtmlExtractor();
	}

	public Set<String> getSupportedMimeTypes() {
		return MIME_TYPES;
	}
}
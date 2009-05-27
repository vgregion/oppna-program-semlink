package com.m1.semanticapp.harvester.extractor.html;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.semanticdesktop.aperture.extractor.Extractor;
import org.semanticdesktop.aperture.extractor.ExtractorFactory;


public class HtmlExtractorFactory implements ExtractorFactory {

	private static final Set MIME_TYPES;

	static {
		HashSet set = new HashSet();
		set.add("text/html");
		set.add("application/xhtml+xml");

		MIME_TYPES = Collections.unmodifiableSet(set);
	}

	public Extractor get() {
		return new HtmlExtractor();
	}

	public Set getSupportedMimeTypes() {
		return MIME_TYPES;
	}
}

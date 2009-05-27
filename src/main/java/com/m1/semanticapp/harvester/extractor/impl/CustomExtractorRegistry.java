package com.m1.semanticapp.harvester.extractor.impl;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.semanticdesktop.aperture.extractor.ExtractorFactory;
import org.semanticdesktop.aperture.extractor.FileExtractorFactory;
import org.semanticdesktop.aperture.extractor.impl.ExtractorRegistryImpl;
import org.semanticdesktop.aperture.util.ResourceUtil;
import org.semanticdesktop.aperture.util.SimpleSAXAdapter;
import org.semanticdesktop.aperture.util.SimpleSAXParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

public class CustomExtractorRegistry extends ExtractorRegistryImpl {

    private static final String DEFAULT_FILE = "com/m1/semanticapp/harvester/extractor/impl/defaults.xml";

    private static final String EXTRACTOR_FACTORY_TAG = "extractorFactory";

    private static final String NAME_TAG = "name";

    public CustomExtractorRegistry() {
        try {
            InputStream stream = ResourceUtil.getInputStream(DEFAULT_FILE, CustomExtractorRegistry.class);
            BufferedInputStream buffer = new BufferedInputStream(stream);
            parse(buffer);
            buffer.close();
        }
        catch (IOException e) {
            throw new RuntimeException("unable to parse " + DEFAULT_FILE, e);
        }
    }

    public CustomExtractorRegistry(InputStream stream) throws IOException {
        parse(stream);
    }

    private void parse(InputStream stream) throws IOException {
        try {
            // Parse the document
            SimpleSAXParser parser = new SimpleSAXParser();
            parser.setListener(new ExtractorParser());
            parser.parse(stream);
        }
        catch (ParserConfigurationException e) {
            IOException ie = new IOException(e.getMessage());
            ie.initCause(e);
            throw ie;
        }
        catch (SAXException e) {
            IOException ie = new IOException(e.getMessage());
            ie.initCause(e);
            throw ie;
        }
    }

    private class ExtractorParser extends SimpleSAXAdapter {

        private Logger logger = LoggerFactory.getLogger(getClass());

        private boolean insideFactoryElement = false;

        public void startTag(String tagName, Map atts, String text) throws SAXException {
            if (EXTRACTOR_FACTORY_TAG.equals(tagName)) {
                insideFactoryElement = true;
            }
            else if (NAME_TAG.equals(tagName) && insideFactoryElement && text != null) {
                processClassName(text);
            }
        }

        public void endTag(String tagName) {
            if (EXTRACTOR_FACTORY_TAG.equals(tagName)) {
                insideFactoryElement = false;
            }
        }

        private void processClassName(String className) {
            className = className.trim();
            if (!className.equals("")) {
                try {
                    Class clazz = Class.forName(className);
                    Object instance = clazz.newInstance();
                    if (instance instanceof ExtractorFactory) {
                        ExtractorFactory factory = (ExtractorFactory) instance;
                        add(factory);
                    } else if (instance instanceof FileExtractorFactory) {
                        FileExtractorFactory factory = (FileExtractorFactory) instance;
                        add(factory);
                    }
                }
                catch (ClassNotFoundException e) {
                    logger.warn("unable to find class " + className + ", ignoring", e);
                }
                catch (InstantiationException e) {
                    logger.warn("unable to instantiate class " + className + ", ignoring", e);
                }
                catch (IllegalAccessException e) {
                    logger.warn("unable to access class " + className + ", ignoring", e);
                }
                catch (ClassCastException e) {
                    logger.warn("unable to cast instance to " + ExtractorFactory.class.getName()
                            + ", ignoring", e);
                }
            }
        }
    }
}

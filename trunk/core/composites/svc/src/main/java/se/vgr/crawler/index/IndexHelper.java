package se.vgr.crawler.index;

import info.aduna.text.StringUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import java.text.MessageFormat;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.ontoware.rdf2go.model.Model;
import org.ontoware.rdf2go.model.Statement;
import org.ontoware.rdf2go.model.node.Literal;
import org.ontoware.rdf2go.model.node.Node;
import org.ontoware.rdf2go.model.node.URI;

/**
 * <p>Index helper</p>
 * @author Johan Säll Larsson
 * @see http://wiki.apache.org/jakarta-lucene/LuceneFAQ
 */

public class IndexHelper {

	protected static Logger log = Logger.getLogger(IndexHelper.class);
	private static float boost = 1.0f;
	
	public static void index(LuceneIndex luceneIndex, URI uri, Model model, boolean fullText) {
		
		Map<String, Float> fieldBoosts = luceneIndex.getFieldBoosts();
		Document document = createDocument(uri);

		for ( Statement statement : model ) {
			
			if (statement.getSubject().equals(uri)) {
				
				Node node = statement.getObject();

				if ( node instanceof Literal ) {
					
					String predicate = statement.getPredicate().toString();
					String field = IndexConstants.INDEXED_RESOURCE_PREDICATES.get(predicate);
					
					if ( field != null ) {
						
						if ( fieldBoosts != null ) {
							
							Float customBoost = fieldBoosts.get(field);
							
							if ( customBoost != null ) {
								boost = customBoost.floatValue();
							}
						
						}
						
						if ( fullText ) {
							
							index(field, node.toString(), document, boost);
						
						} else {
							
							if ( !field.equals(IndexConstants.TEXT_FIELD) ) {
								index(field, node.toString(), document, boost);
							} 
							
						}
						
					}
					
				}
				
			}

		}

		try {
			
			luceneIndex.addDocument(document);
			
		} catch (IOException e) {
			log.error("Unable to add document", e);
		}

	}

	private static Document createDocument(URI uri) {
		
		Document document = new Document();

		String originalUri = uri.toString();
		String url = uri.toString();
		
		/* Since URI's are id's we need to store them */
		document.add(new Field(IndexConstants.UID_FIELD, url, Field.Store.YES, Field.Index.ANALYZED_NO_NORMS));
		
		try {
			url = URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {}

		/* Path indexing */
		
		String path = StringUtil.getAllAfter(url, ':');
		
		if ( !originalUri.startsWith("file:") ) {
			
			path = StringUtil.getAllBefore(path, '#');
			path = StringUtil.getAllBefore(path, '?');
			
		}

		document.add(new Field(IndexConstants.PATH_FIELD, path, Field.Store.NO, Field.Index.ANALYZED));

		StringTokenizer tokenizer = new StringTokenizer(path, "/\\: ", false);
		
		Integer count = 0;
		
		while (tokenizer.hasMoreTokens()) {
			
			String term = tokenizer.nextToken();
			term = term.trim();
			term = term.toLowerCase();
			log.debug(MessageFormat.format("Indexing path: {0}", term ));
			
			if ( count == 0 ) {
				document.add(new Field(IndexConstants.SOURCE, term, Field.Store.YES, Field.Index.NOT_ANALYZED_NO_NORMS));
			} else {
				document.add(new Field(IndexConstants.PATH_FIELD, term, Field.Store.NO, Field.Index.NOT_ANALYZED));
			}
			
			count ++;
			
		}

		return document;
		
	}

	private static void index(String field, String value, Document document, float boost) {
		
		/*log.debug(MessageFormat.format("Indexing field: {0}, with literal: {1}", field, value ));*/
		Field luceneField = new Field(field, value, Field.Store.YES, Field.Index.ANALYZED);
		luceneField.setBoost(boost);
		document.add(luceneField);
		
	}

}

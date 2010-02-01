package se.vgr.crawler.service;

import java.text.MessageFormat;
import java.util.List;

import org.apache.log4j.Logger;

import se.vgr.crawler.domain.LuceneHit;
import se.vgr.crawler.extractor.processor.StopWord;
import se.vgr.crawler.index.LuceneIndex;
import se.vgr.crawler.index.analysis.LanguageAnalyzer;

/**
 * <p>Search service</p>
 * @author Johan Säll Larsson
 */

public class SearchService {

	private static Logger log = Logger.getLogger(SearchService.class);
	private LuceneIndex luceneIndex;
	private LanguageAnalyzer languageAnalyzer;
	private StopWord stopWord;
	
	public SearchService() {}
	
	/**
	 * <p>Searches all indexes</p>
	 * @param query
	 * @param languageCode
	 * @param maxHits
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 */
	
	public List<LuceneHit> search(String query, String languageCode, String source, Integer maxHits) {

		log.debug(MessageFormat.format("Searching for {0} with language {1} source {2} and max hits {3}", query, languageCode, source, maxHits));
		
		try {
			
			languageAnalyzer.setLanguageCode(languageCode);
			languageAnalyzer.setStopWords(stopWord.getStopWordByLanguage(languageCode));
			luceneIndex.setAnalyzer(languageAnalyzer);
			List<LuceneHit> retval = luceneIndex.search(query, languageCode, source, maxHits);
			
			log.debug(MessageFormat.format("Found {0} hits", retval.size()));
			
			return retval;

		} catch ( Exception e ) {
			e.printStackTrace();
			log.warn(MessageFormat.format("Error while searching: {0}", e.getMessage()));
		}
		
		return null;

	}
	
	/**
	 * <p>Searches a specific URL</p>
	 * @param url
	 * @param languageCode
	 * @param source
	 * @param maxHits
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 */
	
	public List<LuceneHit> searchUrl(String url, String languageCode, String source, Integer maxHits) {
		
		try {
			
			languageAnalyzer.setLanguageCode(languageCode);
			languageAnalyzer.setStopWords(stopWord.getStopWordByLanguage(languageCode));
			luceneIndex.setAnalyzer(languageAnalyzer);
			return luceneIndex.searchUrl(url, languageCode, source, maxHits);

		} catch ( Exception e ) {
			e.printStackTrace();
			log.warn(MessageFormat.format("Error while searching: {0}", e.getMessage()));
		}
		
		return null;

	}
	

	/* Setter for Spring */
	
	public void setStopWord(StopWord stopWord) {
		this.stopWord = stopWord;
	}

	public void setLuceneIndex(LuceneIndex luceneIndex) {
		this.luceneIndex = luceneIndex;
	}

	public void setLanguageAnalyzer(LanguageAnalyzer languageAnalyzer) {
		this.languageAnalyzer = languageAnalyzer;
	}
	
}

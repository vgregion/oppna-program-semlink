package se.vgr.crawler.index.analysis;

import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.standard.*;
import org.apache.lucene.util.Version;

import se.vgr.crawler.index.analysis.filter.LanguageFilter;

import java.io.IOException;
import java.io.Reader;
import java.util.Set;

/**
 * <p>Modified Snowball analyzer</p>
 * @see http://www.apache.org/licenses/LICENSE-2.0
 * @author Johan Säll Larsson
 */

public class LanguageAnalyzer extends Analyzer {
	  
	private String languageCode;
	private String[] stopWords;
	
	private Set<?> stopSet;


	public LanguageAnalyzer() {}
	
	/**
	 * <p>Builds the named analyzer with the given stop words.</p>
	 * @param luceneVersion
	 * @param languageCode
	 * 		  - Two-letter ISO 639-1 language code.
	 * @param stopWords
	 */
	
	public LanguageAnalyzer(String languageCode, String[] stopWords) {
		
		this.languageCode = languageCode;
		this.stopWords = stopWords;
		this.initialize();
	}
	
	/**
	 * <p>initialize the analyzer </p>
	 */
	
	@SuppressWarnings("deprecation")
	public void initialize() {
		
		if ( languageCode == null ) {
			this.languageCode = "default";
		}
		
		setOverridesTokenStreamMethod(LanguageAnalyzer.class);
		
		if ( stopWords != null ) {
			stopSet = StopFilter.makeStopSet(stopWords);
		}
		
	}

	/**
	 * <p>Constructs a <code>StandardTokenizer</code> filtered by a <code>StandardFilter</code>, 
	 * a <code>LowerCaseFilter</code>, a <code>StopFilter</code>, and a <code>SnowballFilter</code></p>
	 * @param fieldName
	 * @param reader
	 */
	
	public TokenStream tokenStream(String fieldName, Reader reader) {
		
		TokenStream result = new StandardTokenizer(Version.LUCENE_CURRENT, reader);
		result = new StandardFilter(result);
		result = new LowerCaseFilter(result);
		
		if ( stopSet != null ) {
			result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(Version.LUCENE_CURRENT), result, stopSet);
		}
		
		result = new LanguageFilter(result, languageCode);
		
		return result;
		
	}
	  
	private class SavedStreams {
		Tokenizer source;
		TokenStream result;
	}
  
	/** 
	 * <p>Returns a (possibly reused) <code>StandardTokenizer</code> filtered by a <code>StandardFilter</code>, 
	 * a <code>LowerCaseFilter</code>, a <code>StopFilter</code>, and a <code>SnowballFilter</code></p>
	 * @param fieldName
	 * @param reader
	 */
	
	public TokenStream reusableTokenStream(String fieldName, Reader reader) throws IOException {
	
		if ( overridesTokenStreamMethod ) {
			return tokenStream(fieldName, reader);
	    }
	    
	    SavedStreams streams = (SavedStreams) getPreviousTokenStream();
	    
	    if ( streams == null ) {
	    	
	    	streams = new SavedStreams();
	    	streams.source = new StandardTokenizer(Version.LUCENE_CURRENT, reader);
	    	streams.result = new StandardFilter(streams.source);
	    	streams.result = new LowerCaseFilter(streams.result);
	    	
	    	if ( stopSet != null ) {
	    		streams.result = new StopFilter(StopFilter.getEnablePositionIncrementsVersionDefault(Version.LUCENE_CURRENT), streams.result, stopSet);
	    	}
	    	
	    	streams.result = new LanguageFilter(streams.result, languageCode);
	    	setPreviousTokenStream(streams);
	    	
	    } else {
	    	streams.source.reset(reader);
	    }
	    
	    return streams.result;
	    
	}
	
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	public void setStopWords(String[] stopWords) {
		this.stopWords = stopWords;
	}

	
}


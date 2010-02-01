package se.vgr.crawler.index.analysis.filter;
import java.io.IOException;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.tartarus.snowball.SnowballProgram;

import se.vgr.crawler.index.analysis.stemmer.Stemmer;

/**
 * <p>A filter that stems words using a Snowball-generated stemmer</p>
 * @author Johan Säll Larsson
 */

public class LanguageFilter extends TokenFilter {

	private SnowballProgram stemmer;
	
	private TermAttribute termAtt;
	  
	public LanguageFilter(TokenStream input, SnowballProgram stemmer) {
		super(input);
		this.stemmer = stemmer;
		termAtt = (TermAttribute) addAttribute(TermAttribute.class);
	}

	/**
	 * Construct the named stemming filter.
	 *
	 * @param in the input tokens to stem
	 * @param languageCode
	 */
	
	public LanguageFilter(TokenStream in, String languageCode) {
	   
		super(in);
		
		String className = Stemmer.stemmers.get(languageCode);
		if ( className == null ) {
			className = Stemmer.stemmers.get("default");
		}
		
		try {
			
			Class<?> stemClass = Class.forName(className);
			stemmer = (SnowballProgram) stemClass.newInstance();
			
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	
		termAtt = (TermAttribute) addAttribute(TermAttribute.class);
	
	}

	/** Returns the next input Token, after being stemmed */
	public final boolean incrementToken() throws IOException {
		
		if (input.incrementToken()) {
			
			String originalTerm = termAtt.term();
			stemmer.setCurrent(originalTerm);
			stemmer.stem();
			String finalTerm = stemmer.getCurrent();
			
			if ( !originalTerm.equals(finalTerm) ){
				termAtt.setTermBuffer(finalTerm);
			}
			
			return true;
			
		} else {
			return false;
		}
		
	}
	  
	/** 
	 * @deprecated Will be removed in Lucene 3.0. This method is final, as it should 
	 * not be overridden. Delegates to the backwards compatibility layer. 
	 */
	
	public final Token next(final Token reusableToken) throws java.io.IOException {
		return super.next(reusableToken);
	}

	/** 
	 * @deprecated Will be removed in Lucene 3.0. This method is final, as it should 
	 * not be overridden. Delegates to the backwards compatibility layer. 
	 */
	
	public final Token next() throws java.io.IOException {
		return super.next();
	}
	
}

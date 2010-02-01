package se.vgr.crawler.index.analysis.stemmer;

import java.util.HashMap;

/**
 * <p>List of stemmers by two-letter ISO 639-1 language code. </p>
 * @see http://www.infoterm.info/standardization/iso_639_1_2002.php
 * @author Johan Säll Larsson
 */

public class Stemmer {

	public static HashMap<String, String> stemmers = new HashMap<String,String>();
	
	static {
		
		stemmers.put("en", "org.tartarus.snowball.ext.EnglishStemmer");
		stemmers.put("sv", "org.tartarus.snowball.ext.SwedishStemmer");
		stemmers.put("no", "org.tartarus.snowball.ext.NorwegianStemmer");
		stemmers.put("da", "org.tartarus.snowball.ext.DanishStemmer");
		stemmers.put("fi", "org.tartarus.snowball.ext.FinnishStemmer");
		stemmers.put("pt", "org.tartarus.snowball.ext.PortugueseStemmer");
		stemmers.put("nl", "org.tartarus.snowball.ext.DutchStemmer");
		stemmers.put("fr", "org.tartarus.snowball.ext.FrenchStemmer");
		stemmers.put("de", "org.tartarus.snowball.ext.GermanStemmer");
		stemmers.put("hu", "org.tartarus.snowball.ext.HungarianStemmer");
		stemmers.put("it", "org.tartarus.snowball.ext.ItalianStemmer");
		stemmers.put("ro", "org.tartarus.snowball.ext.RomanianStemmer");
		stemmers.put("ru", "org.tartarus.snowball.ext.RussianStemmer");
		stemmers.put("es", "org.tartarus.snowball.ext.SpanishStemmer");
		stemmers.put("tr", "org.tartarus.snowball.ext.TurkishStemmer");
		
		stemmers.put("default", "org.tartarus.snowball.ext.PorterStemmer");

	}
}

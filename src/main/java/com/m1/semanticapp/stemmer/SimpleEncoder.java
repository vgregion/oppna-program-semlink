package com.m1.semanticapp.stemmer;

public class SimpleEncoder extends Stemmer {

	private static final long serialVersionUID = -38767906945466234L;

	/**
	 * SimpleEncoder simply replaces the occasional HTML encodings
	 * @param String
	 * @return String
	 * @author Johan 
	 */
	@Override
	public String stem(String word) {
		if(word.contains("&amp;#")){
			word = word.replace("&amp;#229;", "�");
			word = word.replace("&amp;#228;","�");
			word = word.replace("&amp;#246;","�");
			word = word.replace("&amp;#233;","�");
			word = word.replace("&amp;#197;","�");
			word = word.replace("&amp;#196;","�");
			word = word.replace("&amp;#214;","�");
    	}
    	return word;
	}
}
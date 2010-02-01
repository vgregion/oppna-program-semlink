package se.vgr.crawler.util;

public class StringUtils {

	/**
	 * Simply replaces occasional HTML encodings
	 * @param word
	 * @return <code>String</code>
	 */
	
	public static String encodeHtml(String word) {
		
		if ( word != null && word.contains("&amp;#") ) {
			word = word.replace("&amp;#229;","�");
			word = word.replace("&amp;#228;","�");
			word = word.replace("&amp;#246;","�");
			word = word.replace("&amp;#233;","�");
			word = word.replace("&amp;#197;","�");
			word = word.replace("&amp;#196;","�");
			word = word.replace("&amp;#214;","�");
    	}
    	
		return word;
    	
	}
	
	/**
	 * <p>Removes all html tags</p>
	 * @param text
	 * @return <code>String</code>
	 */
	
	public static String removeHtmlTags(String text){
		text = text.replaceAll("\\<.*?\\>", "");
		return text;
	}
	
}

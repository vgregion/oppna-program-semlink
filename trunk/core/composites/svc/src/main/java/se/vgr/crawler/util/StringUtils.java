package se.vgr.crawler.util;

public class StringUtils {

	/**
	 * Simply replaces occasional HTML encodings
	 * @param word
	 * @return <code>String</code>
	 */
	
	public static String encodeHtml(String word) {
		
		if ( word != null && word.contains("&amp;#") ) {
			word = word.replace("&amp;#229;","å");
			word = word.replace("&amp;#228;","ä");
			word = word.replace("&amp;#246;","ö");
			word = word.replace("&amp;#233;","é");
			word = word.replace("&amp;#197;","Å");
			word = word.replace("&amp;#196;","Ä");
			word = word.replace("&amp;#214;","Ö");
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

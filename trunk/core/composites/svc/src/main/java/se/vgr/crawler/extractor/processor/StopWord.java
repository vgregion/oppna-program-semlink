package se.vgr.crawler.extractor.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


public class StopWord {

	private HashMap<String, String[]> stopWords = new HashMap<String, String[]>();
	private Map<String,String> map;
	
	protected static Logger log = Logger.getLogger(StopWord.class);
	
	public StopWord() {}
	
	/**
	 * <p>Spring initializer for the stop words</p>
	 */
	
	public void init() {
		
		Set< Entry<String, String> > set = map.entrySet();
		Iterator< Entry<String, String> > iterator = set.iterator();
		
		while ( iterator.hasNext() ) {
			Map.Entry<String,String> entry = (Map.Entry<String,String>) iterator.next();
			loadStopWordList(entry.getKey(), entry.getValue());
		}
		
	}
	
	/**
	 * <p>Loads the stop words in a HashMap</p>
	 * @param language
	 * @param filename
	 */
	
	private void loadStopWordList(String language, String filename) {
		
		if( filename != null || language != null) {
			
			try {
				
				InputStream stopWordStream = this.getClass().getResourceAsStream(filename);
				BufferedReader reader = new BufferedReader( new InputStreamReader(stopWordStream, "UTF-8") );
				String line = reader.readLine();
				
				List<String> stopList = new ArrayList<String>();
				
				while ( line != null ) {
					
					stopList.add(line);
					line = reader.readLine();
				
				}
				
				String[] stopword = stopList.toArray(new String[stopList.size()]);
				stopWords.put(language, stopword);
				
			} catch (NullPointerException e) {
				log.error(MessageFormat.format("Stopword file {0} could not be found. No filtering will be performed",filename));
				
			} catch (IOException e) {
				stopWords.clear();
				log.error(MessageFormat.format("Error reading stopword file {0}. List will be cleared. No filtering will be performed", filename));
			}
			
		} else {
			
			log.warn("No stopword file given. No filtering will be performed");
			
		}
		
	}
	
	/**
	 * <p>Returns the stop words for the specific language</p>
	 * @param language
	 * @return <code>Array</code> of <code>String</code>
	 */
	
	public String[] getStopWordByLanguage(String language){
		if ( stopWords != null && language != null ) {
			return stopWords.get(language);
		}
		return null;
	}

	/**
	 * <p>Returns the stop words</p>
	 * @return <code>HashMap</code>
	 */
	
	public HashMap<String, String[]> getStopWords() {
		return stopWords;
	}
	
	/**
	 * <p>Sets stop words</p>
	 * @param stopWords
	 */
	
	public void setStopWord(HashMap<String, String[]> stopWords){
		this.stopWords = stopWords;
	}

	/* Spring setter */
	
	public void setMap(Map<String,String> map) {
		this.map = map;
	}

}

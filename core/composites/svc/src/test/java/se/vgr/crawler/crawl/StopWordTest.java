package se.vgr.crawler.crawl;

import java.net.MalformedURLException;
import java.text.MessageFormat;

import org.apache.log4j.Logger;
import se.vgr.crawler.AbstractDataAccess;
import se.vgr.crawler.extractor.processor.StopWord;

public class StopWordTest extends AbstractDataAccess {

	private StopWord stopWord;
	protected Logger log = Logger.getLogger(StopWordTest.class);

	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		stopWord = (StopWord) applicationContext.getBean("stopWord");
	}
	
	public void testStopWord() throws MalformedURLException {
		String[] stopwords = stopWord.getStopWordByLanguage("sv");
		
		if ( stopwords != null ) {
			for ( int i=0; i < stopwords.length; i++ ) {
				log.debug(MessageFormat.format("Stopword: {0}", stopwords[i]));
			}	
		}
		
	}
	
}

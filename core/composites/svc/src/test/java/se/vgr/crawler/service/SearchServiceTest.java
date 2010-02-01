package se.vgr.crawler.service;

import java.text.MessageFormat;
import java.util.List;
import org.apache.log4j.Logger;

import se.vgr.crawler.AbstractDataAccess;

import se.vgr.crawler.domain.LuceneHit;

import se.vgr.crawler.service.SearchService;

/**
 * <p>Test case: Search service</p>
 * @author Johan Säll Larsson
 */

public class SearchServiceTest extends AbstractDataAccess {

	private static Logger log = Logger.getLogger(SearchServiceTest.class);
	private SearchService searchService;
	
	private static final String LANGUAGE = null;
	private static final String QUERY = " teckenspråk, sjukvård, västra götaland ";
	private static final Integer MAXHIT = 10;
	private static final String SOURCE = null;
	
	private static final String URL = "\"http://vard.vgregion.se/sv/Teman1/Tema-Gravid/Konsorgan-och-fortplantning/\"";
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		searchService = (SearchService) applicationContext.getBean("system.searchService");
	}
	
	public void testMultiSearch() {
		
		System.out.println("Query: "+QUERY);
		List<LuceneHit> list = searchService.search(QUERY, LANGUAGE, SOURCE,  MAXHIT);
		
		if ( list != null ) {
			
			for ( LuceneHit hit : list ) {
				
				log.debug(MessageFormat.format("Score: {0}", hit.getScore()));
				log.debug(MessageFormat.format("Title: {0}", hit.getTitle()));
				log.debug(MessageFormat.format("Description: {0}", hit.getDescription()));
				log.debug(MessageFormat.format("Language: {0}", hit.getLanguage()));
				log.debug(MessageFormat.format("Source: {0}", hit.getSource()));
				log.debug(MessageFormat.format("Url: {0}", hit.getUrl()));
				log.debug(MessageFormat.format("Audience: {0}", hit.getAudience()));
				
				/*
				List<String> synonyms = hit.getSynonyms();
				if ( synonyms != null ) {
					for ( String syn : synonyms ) {
						log.debug(MessageFormat.format("Synonyms: {0}", syn));
					}
				}
				
				List<String> keywords = hit.getKeyword();
				for ( String kw : keywords ) {
					log.debug(MessageFormat.format("Keyword: {0}", kw));
				}
				
				List<String> paths = hit.getPath();
				for ( String ps : paths ) {
					log.debug(MessageFormat.format("Path: {0}", ps));
				}
				
				System.out.println("\n");
				*/
				
			}
		}
		
		assertNull(null);
		
	}
	
	public void testSearchUrl() {
		
		log.debug("Running test: testSearchUrl");
		
		List<LuceneHit> list = searchService.searchUrl(URL, LANGUAGE, SOURCE,  MAXHIT);
		
		if ( list != null ) {
			
			System.out.println("list not null and length: "+list.size());
			
			for ( LuceneHit hit : list ) {
				
				log.debug(MessageFormat.format("Score: {0}", hit.getScore()));
				log.debug(MessageFormat.format("Title: {0}", hit.getTitle()));
				log.debug(MessageFormat.format("Description: {0}", hit.getDescription()));
				log.debug(MessageFormat.format("Language: {0}", hit.getLanguage()));
				log.debug(MessageFormat.format("Source: {0}", hit.getSource()));
				log.debug(MessageFormat.format("Url: {0}", hit.getUrl()));
				log.debug(MessageFormat.format("Audience: {0}", hit.getAudience()));
				
				/*
				List<String> synonyms = hit.getSynonyms();
				if ( synonyms != null ) {
					for ( String syn : synonyms ) {
						log.debug(MessageFormat.format("Synonyms: {0}", syn));
					}
				}
				
				List<String> keywords = hit.getKeyword();
				for ( String kw : keywords ) {
					log.debug(MessageFormat.format("Keyword: {0}", kw));
				}
				
				List<String> paths = hit.getPath();
				for ( String ps : paths ) {
					log.debug(MessageFormat.format("Path: {0}", ps));
				}
				*/
				
				List<String> meshes = hit.getMeshUID();
				for ( String m : meshes ) {
					log.debug(MessageFormat.format("Mesh: {0}", m));
				}
				
				System.out.println("\n");
				
			}
			
		} else {
			System.out.println("testURLSearch returned NULL");
		}
		
		assertNull(null);
		
	}
	
}

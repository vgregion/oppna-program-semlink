package se.vgr.crawler.dao;

import java.util.List;

import org.apache.log4j.Logger;

import se.vgr.crawler.AbstractDataAccess;
import se.vgr.crawler.entity.Source;
import se.vgr.crawler.entity.SourceType;

/**
 * <p>Test case : <code>SourceDao</code></p>
 * @author Johan Säll Larsson
 */

public class SourceDaoTest extends AbstractDataAccess {

	private SourceDao sourceDao;
	protected Logger log = Logger.getLogger(SourceDaoTest.class);
	
	private static String URL = "http://www.thesemanticweb.se";
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		sourceDao = (SourceDao) applicationContext.getBean("sourceDao");
	}
	
	public void testAddSource() {
		
		Source source = new Source();
		source.setRootUrl(URL);
		source.setSource(URL);
		source.setWhiteListPattern(URL);
		source.setBlackListPattern(null);
		source.setDepth(0);
		source.setFullReCrawl(true);
		source.setSourceType(SourceType.WEB);
		source.setUniqueRepositoryName(URL);
		
		sourceDao.add(source);
		List<Source> list = sourceDao.getByRootUrl(URL);
		Source s = list.get(0);
		assertEquals(source.getRootUrl(), s.getRootUrl());
		
	}
	
	public void testUpdate() {
		
		List<Source> list = sourceDao.getByRootUrl(URL);
		
		if ( list != null && list.size() != 0 ) {
			
			Source s = list.get(0);
			s.setDepth(1);
			int status = sourceDao.update(s);
			
			assertSame(1, status);
			
		}
		
	}
	
	public void testGetAll() {
		
		List<Source> list = sourceDao.getAll();
		assertNotNull(list);
		
		for ( Source s : list ) {
			log.debug(s.getRootUrl());
			log.debug(s.getFullReCrawl());
		}
		
	}

}
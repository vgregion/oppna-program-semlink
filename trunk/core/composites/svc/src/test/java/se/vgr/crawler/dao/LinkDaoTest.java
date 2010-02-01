package se.vgr.crawler.dao;

import java.net.MalformedURLException;
import java.util.List;

import org.apache.log4j.Logger;

import se.vgr.crawler.AbstractDataAccess;
import se.vgr.crawler.entity.Link;

/**
 * <p>Test case: Link DAO</p>
 * @author Johan Säll Larsson
 */

public class LinkDaoTest extends AbstractDataAccess {

	private LinkDao linkDao;
	protected Logger log = Logger.getLogger(LinkDaoTest.class);
	
	private static String TEST = "test";
	private static String URL = "http://www.example.com";
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		linkDao = (LinkDao) applicationContext.getBean("linkDao");
	}
	
	public void testAddLink() throws MalformedURLException {
		
		Link link = new Link();
		link.setUrl(URL);
		link.setTitle(TEST);
		link.setRelatedLink(URL);
		link.setSource(URL);
		link.setDescription(TEST);
	
		linkDao.add(link);
		List<Link> list = linkDao.getByUrl(URL);
		Link l = list.get(0);
		assertEquals(link.getUrl(), l.getUrl());
		
	}

	public void testGetByUrl() throws MalformedURLException {
		
		List<Link> list = linkDao.getByUrl(URL);
		if ( list != null ) {
			assertNotNull(list);
		}
		
	}
	
	public void testUpdate() throws MalformedURLException{
		
		List<Link> list = linkDao.getByUrl(URL);
		
		if ( list != null && list.size() != 0 ) {
			
			Link l = list.get(0);
			l.setRelatedLink(URL);
			int status = linkDao.update(l);
			
			assertEquals(l, linkDao.getByUrl(URL));
			assertSame(1, status);
			
		}
		
	}

}

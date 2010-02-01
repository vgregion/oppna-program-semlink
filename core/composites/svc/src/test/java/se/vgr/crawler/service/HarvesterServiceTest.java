package se.vgr.crawler.service;

import java.io.IOException;

import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.config.RepositoryConfigException;
import org.openrdf.rio.RDFParseException;

import se.vgr.crawler.AbstractDataAccess;
import se.vgr.crawler.entity.Source;
import se.vgr.crawler.entity.SourceType;

/**
 * <p>Test case: Harvester service</p>
 * @author Johan Säll Larsson
 */

public class HarvesterServiceTest extends AbstractDataAccess {

	private HarvesterService harvesterService;
	
	private static String rootUrl = "http://vard.vgregion.se/sv/Teckenspraksfilmer/";
	private static String source = "vard.vgregion.se";
	private static Integer depth = 1;
	private static String includePattern = "http://vard.vgregion.se/sv/Teckenspraksfilmer/";
	private static String excludePattern = null;
	private static String uniqueRepositoryName = "vard.vgregion.se";
	private static boolean fullRecrawl = false;
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		harvesterService = (HarvesterService) applicationContext.getBean("system.harvesterService");
	}
	
	public void testHarvest() throws RepositoryException, RepositoryConfigException, RDFParseException, IOException{
		
		Source config = new Source();
		config.setDepth(depth);
		config.setWhiteListPattern(includePattern);
		config.setFullReCrawl(fullRecrawl);
		config.setBlackListPattern(excludePattern);
		config.setRootUrl(rootUrl);
		config.setSource(source);
		config.setUniqueRepositoryName(uniqueRepositoryName);
		config.setSourceType(SourceType.WEB);
		
		harvesterService = (HarvesterService) applicationContext.getBean("system.harvesterService");
		harvesterService.startWebCrawl(config);
		
		assertNull(null);
		
	}
	
}

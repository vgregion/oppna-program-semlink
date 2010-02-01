package se.vgr.crawler;

import org.springframework.test.jpa.AbstractJpaTests;

public abstract class AbstractDataAccess extends AbstractJpaTests {
        
	/**
	 * Reference the Spring configuration file for the test case.
	 */
    
	@Override
	protected String[] getConfigLocations(){
	     return new String[]{ "services-config-test.xml" };
	}

}

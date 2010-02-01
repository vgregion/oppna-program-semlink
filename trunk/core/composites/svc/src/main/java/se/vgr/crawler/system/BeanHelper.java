package se.vgr.crawler.system;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanHelper {

	public static ApplicationContext context;
	
	static {
		
		context = new ClassPathXmlApplicationContext(new String[] {"services-config.xml"});
		
	}
	
}

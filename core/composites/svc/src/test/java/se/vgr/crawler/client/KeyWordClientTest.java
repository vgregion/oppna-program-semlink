package se.vgr.crawler.client;

import java.util.List;

import org.apache.log4j.Logger;

import se.vgr.crawler.AbstractDataAccess;
import se.vgr.metaservice.schema.node.v2.NodePropertyListType;
import se.vgr.metaservice.schema.node.v2.NodePropertyType;
import se.vgr.metaservice.schema.node.v2.NodeType;
import se.vgr.metaservice.schema.node.v2.SynonymsListType;

/**
 * <p>Test case for KeyWordClient</p>
 * @author Johan Säll Larsson
 */

public class KeyWordClientTest extends AbstractDataAccess {

	private KeyWordClient keyWordClient;
	protected Logger log = Logger.getLogger(KeyWordClientTest.class);
	
	private static String TEXT_CONTENT = "graviditet";
	private static String TEXT_TITLE = "Tema Gravid";
	
	@Override
	protected void onSetUp() throws Exception {
		super.onSetUp();
		keyWordClient = (KeyWordClient) applicationContext.getBean("keyWordClient");
	}
	
	public void testUrlStatus() {
		
		List<NodeType> nodeTypeList = keyWordClient.getKeyWords(TEXT_TITLE, TEXT_CONTENT);
		
		if ( nodeTypeList != null && nodeTypeList.size() > 0 ) {
			
			NodeType node = nodeTypeList.get(0);
			
			log.debug("name: "+node.getName());
			log.debug("sourceid: "+node.getSourceId());
			
			SynonymsListType synonymTypeList = node.getSynonyms();
			List<String> synonyms = synonymTypeList.getSynonym();
			
			NodePropertyListType propertiyList = node.getNodeProperties();
			List<NodePropertyType> nodePropertyType = propertiyList.getNodeProperty();

			//NodeListType parentList = node.getParents();
			//List<NodeType> parents = parentList.getNode();
			
			
			for ( String synonym : synonyms ) {
				log.debug("synonym: "+synonym);
			}
			
			for ( NodePropertyType property : nodePropertyType ) {
				if ( property.getName().equals("Code in Source") ) {
					log.debug("property name: "+property.getName());
					log.debug("property value: "+property.getValue());
				}
			}
			
		} else {
			log.error("Failed!");
		}
		
		assertNotNull(nodeTypeList);
		
	}

}

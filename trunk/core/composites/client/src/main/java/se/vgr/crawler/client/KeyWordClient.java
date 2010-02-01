package se.vgr.crawler.client;

import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.log4j.Logger;

import se.vgr.metaservice.schema.document.v1.TextDocumentType;
import se.vgr.metaservice.schema.node.v2.NodeListType;
import se.vgr.metaservice.schema.node.v2.NodeType;
import se.vgr.metaservice.schema.request.v1.IdentificationType;
import se.vgr.metaservice.schema.response.v1.NodeListResponseObjectType;
import se.vgregion.metaservice.keywordservice.intsvc.KeywordServiceIntServiceImplService;
import keywordservices.wsdl.metaservice_vgr_se.v2.GetKeywordsRequest;
import keywordservices.wsdl.metaservice_vgr_se.v2.KeywordService;

/**
 * <p>Client for metadata service</p>
 * @author Johan Säll Larsson
 */

public class KeyWordClient {

	protected Logger log = Logger.getLogger(KeyWordClient.class);
	protected KeywordService service;
	private KeywordServiceIntServiceImplService keyWordService;
	private String profileId;
	private String userId;
	private String requestId;
	
	public KeyWordClient() {}
	
	public void init() {
		try {
			service = keyWordService.getKeywordServiceIntServiceImplPort();
		} catch ( Exception e ) {
			log.error("Client exception: "+e.getMessage());
		}
	}
	
	/**
	 * <p>Returns <code>NodeType</code> for a text</p>
	 * 
	 * @param textTitle
	 * @param textContent
	 * 
	 * @return <code>List</code> of <code>NodeType</code> objects
	 */
	
	public List<NodeType> getKeyWords(String textTitle, String textContent){
		
		GetKeywordsRequest param = new GetKeywordsRequest();
		
		TextDocumentType textDocument = new TextDocumentType();
		textDocument.setTitle(textTitle);
		textDocument.setTextContent(textContent);
		param.setDocument(textDocument);
		
		IdentificationType id = new IdentificationType();
		id.setProfileId(profileId);
		id.setUserId(userId);
		param.setIdentification(id);
		
		param.setRequestId(requestId); 
		
		NodeListType list = null;
		
		try {
			
			NodeListResponseObjectType response = service.getKeywords(param);
			list = response.getNodeList();
			
		} catch ( SOAPFaultException e ) {
			log.error(e);
		}
		
		if ( list != null ) {
			return list.getNode();
		} else {
			return null;
		}
		
	}

	/* Setter for Spring */
	
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setKeyWordService(KeywordServiceIntServiceImplService keyWordService) {
		this.keyWordService = keyWordService;
	}
	
}

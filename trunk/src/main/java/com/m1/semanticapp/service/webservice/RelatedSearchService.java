package com.m1.semanticapp.service.webservice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import com.m1.semanticapp.link.Link;
import com.m1.semanticapp.service.search.result.SearchResult;
import com.m1.semanticapp.service.search.KeywordSearch;

public class RelatedSearchService extends ServletEndpointSupport {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public Link[] GetRelatedLinks(String keyword) throws IOException, ParseException{	
		if(keyword.isEmpty()){
			Link[] link = new Link[0];
			link[0] = new Link();
			return link;
		}
		List<Link> listLink = new ArrayList<Link>();
		KeywordSearch keywordSearch = new KeywordSearch();
		SearchResult searchResult = keywordSearch.doSearch(keyword);
		listLink = searchResult.getLinkList();	
		return listLink.toArray(new Link[listLink.size()]);
	}
}

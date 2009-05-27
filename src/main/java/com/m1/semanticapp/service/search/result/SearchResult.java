package com.m1.semanticapp.service.search.result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocCollector;
import com.m1.semanticapp.link.Link;
import com.m1.semanticapp.service.index.IndexService;
import com.m1.semanticapp.stemmer.SimpleEncoder;

public class SearchResult {
	
	private List<Link> linkList = new ArrayList<Link>();
    private Link link;
	private String title;
	private String desc;
	private String uri;
	private String keyword;
	private String rootElementOf;
	private int totalHits;
	private String type;
	private SimpleEncoder simpleEncoder;
    
	public SearchResult(){}
	
    public SearchResult(TopDocCollector collector, IndexSearcher indexSearcher) throws IOException {
    	simpleEncoder = new SimpleEncoder();
    	
    	ScoreDoc[] scoreDoc = collector.topDocs().scoreDocs;
    	setTotalHits(scoreDoc.length);
       
        HashSet<String> seen = new HashSet<String>();
        for (int i = 0; i < scoreDoc.length; i++) {
            int docId = scoreDoc[i].doc;
            Document d = indexSearcher.doc(docId);
           
            uri = d.getValues(IndexService.URI_FIELD)[0];
            if(d.getValues("title").length == 0){
        		title = "";
        	}else{
        		title = d.get("title");
        	}
            if(d.getValues("description").length == 0){
        		desc = "";
        	}else{
        		desc = d.get("description");
        	}
            if(d.getValues("keyword").length == 0){
            	keyword = "";
        	}else{
        		keyword = "";
        		for(int ki = 0; ki < d.getValues("keyword").length; ki++){
        			keyword += d.getValues("keyword")[ki]+ " ";
        		}
        	}
            if(d.getValues("rootElementOf").length == 0){
            	rootElementOf = "";
        	}else{
        		rootElementOf = d.get("rootElementOf");
        	}
            if(d.getValues("type").length == 0){
            	type = "";
        	}else{
        		type = "";
        		for(int ti = 0; ti < d.getValues("type").length; ti++){
        			type += d.getValues("type")[ti]+ " ";
        		}
        	}            
            
            
            if (seen.add(uri)) {
            	link = new Link();
            	link.setUri(cleanUri(uri));
            	link.setScore(scoreDoc[i].score);
            	link.setTitle(cleanTitle(title));
            	link.setDesc(simpleEncoder.stem(desc));
            	link.setType(type);
            	link.setSource(remove(rootElementOf));
            	link.setKeyword(keyword);
            	linkList.add(link);
            }
        }  
    }
    
    private String remove(String url){
    	if(url.indexOf("http://") != -1 || url.indexOf("www")  != -1){
    		url = url.replace("http://", "");
    		url = url.replace("www", "");
    	}
    	return url;
    }
    
	//TODO put this in the db
    private String cleanTitle(String title){
    	if(title.indexOf("- Vård i Västra Götaland") != -1){
    		title = title.replace("- Vård i Västra Götaland", "");
    	}
    	return title;
    }
    
    private String cleanUri(String uri){
    	if (uri.startsWith("R")) {
    		uri = uri.substring(1);
    	}
    	return uri;
    }
    
    /**
     * Return a list of Resources found by this search, ordered
     * by match score.*/
    public List<Link> getLinkList(){
    	return linkList;
    }

	public void setTotalHits(int totalHits) {
		this.totalHits = totalHits;
	}

	public int getTotalHits() {
		return totalHits;
	}
    
}

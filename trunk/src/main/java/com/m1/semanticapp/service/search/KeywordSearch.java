package com.m1.semanticapp.service.search;

import java.io.IOException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocCollector;
import com.m1.semanticapp.datasource.GlobalSource;
import com.m1.semanticapp.service.index.IndexService;
import com.m1.semanticapp.service.search.result.SearchResult;

public class KeywordSearch {
	
	private IndexService index;
	private IndexSearcher indexSearcher;
	
	public SearchResult doSearch(String q) throws IOException, ParseException {
		
		index = GlobalSource.getIndex();
		indexSearcher = GlobalSource.getIndexSearcher();
		
        synchronized (this) {
        	if (!IndexReader.indexExists(index.getPath())) {
            	index.index();
            }
        }
        
        String[] fields = new String[1];
        fields[0] = IndexService.ALL_FIELD;
        
        MultiFieldQueryParser multiQueryParser = new MultiFieldQueryParser(fields, index.getAnalyzer());
        Query query = multiQueryParser.parse(q);            
        TopDocCollector collector = new TopDocCollector(100);
        indexSearcher.search(query, collector);
                         
        return new SearchResult(collector, indexSearcher);     
	}
}

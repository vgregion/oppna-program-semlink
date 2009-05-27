package com.m1.semanticapp.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.search.IndexSearcher;
import org.springframework.web.context.ServletContextAware;

import com.m1.semanticapp.service.index.IndexService;

public class GlobalSource implements ServletContextAware {
    
	protected final Log logger = LogFactory.getLog(getClass());
    private static GlobalSource theGlobalSource; /** The single global instance */
    private static ServletContext servletContext;

    private String[] meshData;
    private String[] ontologyData;
    private String closureRulesData = null;
    private String ontClosureRulesData = null;
    private String indexDir;
    private static IndexService index;
    private static IndexSearcher indexSearcher;
    
    /** 
     * Constructor.
     */
    private GlobalSource() {}
    
    public void init() throws CorruptIndexException, IOException {
    	if(theGlobalSource == null){
    		theGlobalSource = new GlobalSource();
    	}
    	load();
    }
    
    public void load() throws CorruptIndexException, IOException{    	
    	List<String> meshDataPath = new ArrayList<String>();
    	List<String> ontologyDataPath = new ArrayList<String>();
    	
    	for(int i=0;i<ontologyData.length;i++){
    		ontologyDataPath.add("file:" + getServletContext().getRealPath(ontologyData[i]));
    	}

    	for(int i=0;i<meshData.length;i++){
    		meshDataPath.add("file:" + getServletContext().getRealPath(meshData[i]));
    	}
    	
		DataSource ds = new DataSource();
		ds.setMeshData(meshDataPath);
		ds.setOntologyData(ontologyDataPath);
		ds.setIndexDir(getServletContext().getRealPath(indexDir));  
		
		if(!closureRulesData.isEmpty()){
			ds.setClosureRulesData(getServletContext().getRealPath(closureRulesData));
		}
		
		if(!ontClosureRulesData.isEmpty()){
			ds.setOntClosureRulesData(getServletContext().getRealPath(ontClosureRulesData));
		}
    	
    	setIndex(ds.getIndex());
    	
    	if(indexSearcher == null){
    		setIndexSearcher(new IndexSearcher(getIndex().getPath()));
    	}
    	
    }
    
    /**
     * Return the single global instance of this class.
     */
    public static GlobalSource getGlobalSources() {
        return theGlobalSource;
    }
    
	public void setServletContext(ServletContext ctx) {
		servletContext = ctx;		
	}
	
	public static ServletContext getServletContext() {
		 return servletContext;
	 }

	public void setMeshData(String[] meshData) {
		this.meshData = meshData;
	}

	public String[] getMeshData() {
		return this.meshData;
	}

	public void setOntologyData(String[] ontologyData) {
		this.ontologyData = ontologyData;
	}

	public String[] getOntologyData() {
		return ontologyData;
	}

	public void setClosureRulesData(String closureRulesData) {
		this.closureRulesData = closureRulesData;
	}

	public String getClosureRulesData() {
		return closureRulesData;
	}

	public void setOntClosureRulesData(String ontClosureRulesData) {
		this.ontClosureRulesData = ontClosureRulesData;
	}

	public String getOntClosureRulesData() {
		return ontClosureRulesData;
	}
	
	public void setIndexDir(String indexDir){
		this.indexDir = indexDir;
	}
	
	public String getIndexDir(){
		return this.indexDir;
	}

	public static void setIndex(IndexService index) {
		GlobalSource.index = index;
	}

	public static IndexService getIndex() {
		return index;
	}

	public static void setIndexSearcher(IndexSearcher indexSearcher) {
		GlobalSource.indexSearcher = indexSearcher;
	}

	public static IndexSearcher getIndexSearcher() {
		return indexSearcher;
	}
	
}

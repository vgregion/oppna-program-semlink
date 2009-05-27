package com.m1.semanticapp.datasource;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.m1.semanticapp.service.index.IndexService;

public class DataSource {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private List<String> meshData;
	private List<String> ontologyData;
	private String closureRulesData;
	private String ontClosureRulesData;
	private IndexService index;
	private String indexDir;
	protected DataStore store;
	
	public DataSource(){}

	public void init(){
		index = getIndex();
        try {
			index.index();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public synchronized DataStore getDataStore() {
        if (store == null) { 
            store = new DataStore(this);
        }
        return store;
    }
    
	public IndexService getIndex() {

        if (index == null) {                         
            File dir = new File(indexDir);
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    logger.error("Failed to create Lucene index store directory: " + indexDir);
                    return null;
                }
            }
    		
            DataStore ds = getDataStore();
            index = new IndexService(indexDir, ds.getMultiModel(), ds.getOntologyModel());
            index.setIndexBnodeClosure(true);
            index.setIndexByLocalName(true);
            
            // Check how we want to index the ontology
            //RDFNode allowed = config.getPropertyValue(SemAppVocab.indexOntologyBy);
            //rdfs:label

            index.setOntologyIndexing(false, null);
        }
        
        return index;
    }

	public void setMeshData(List<String> meshData){
		this.meshData = meshData;
	}
	public List<String> getMeshData(){
		return this.meshData;
	}
	public void setIndexDir(String indexDir){
		this.indexDir = indexDir;
	}
	public String getIndexDir(){
		return this.indexDir;
	}
	public void setOntologyData(List<String> ontologyData) {
		this.ontologyData = ontologyData;
	}
	public List<String> getOntologyData() {
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

}

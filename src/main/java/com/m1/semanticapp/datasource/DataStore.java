package com.m1.semanticapp.datasource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jena.RuleMap;

import com.hp.hpl.jena.graph.compose.Union;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ModelMaker;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.util.MultiModel;
import com.hp.hpl.jena.util.MultiModelImpl;
import com.hp.hpl.jena.util.SmushUtil;
import com.hp.hpl.jena.vocabulary.OWL;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;
import com.m1.semanticapp.vocabularies.SemAppVocab;

public class DataStore {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private DataSource dataSource;
	protected MultiModel multiModel;
	protected OntModel ontology;
	protected GenericRuleReasoner ontReasoner;
	protected GenericRuleReasoner reasoner;
	protected Model data;
    protected List<Resource> inverseFunctionProperties;
	
    public DataStore(DataSource datasource) {       
    	
    	this.dataSource = datasource;     
        List<String> meshData = dataSource.getMeshData();
        List<String> ontologyData = dataSource.getOntologyData();
        
        if (!ontologyData.isEmpty()) { 
        	if(!dataSource.getClosureRulesData().isEmpty()){
        		initOntReasoner(dataSource.getClosureRulesData());
        	}
            ontology = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM, null);  
            for(String s: ontologyData){
                Model temp = FileManager.get().loadModel(s.toString());                   
                if (temp != null) {
                    ontNormalize(temp);
                    ontology.add(temp);
                    ontology.setNsPrefixes(temp);
                }             
            }

            // We use rdfs:Class internally for template definitions and need to ensure
            // that owl:Class es are also rdfs:Classes without the full cost of OWL reasoning
            List<Resource> classes = new ArrayList<Resource>();
            for (ResIterator i = ontology.listSubjectsWithProperty(RDF.type, OWL.Class); i.hasNext();) {
                classes.add(i.nextResource());
            }
            for (Iterator<Resource> i = classes.iterator(); i.hasNext(); ) {
                ontology.add(i.next(), RDF.type, RDFS.Class);
            }               
        } else {
            // No explicit ontologies so assume it is all in with the raw data
            ontology = ModelFactory.createOntologyModel(OntModelSpec.RDFS_MEM, data);
        }
        
        // Set up the closure rule processor
        if(!dataSource.getClosureRulesData().isEmpty()){
        	initReasoner(dataSource.getClosureRulesData());
        }
        
        // Load all the data models
        ModelMaker mm = ModelFactory.createMemModelMaker();
        multiModel = new MultiModelImpl("", mm);
        data = multiModel;

        for(String s: meshData){
        	Model temp = FileManager.get().loadModel(s.toString());
            if (temp != null) {
                normalize(temp);
                multiModel.addModel("", temp);
                multiModel.setNsPrefixes(temp);
            }
        }         
            
        /* Also load any current saved annotations
        RDFNode file = Util.getValue(config, SemAppVocab.annotationFile);
        if (file != null) {
            String fname = Util.toURL((Resource)file, pcontext);
            annotations = FileManager.get().loadModel(fname);
            data = ModelFactory.createModelForGraph(new Union(multiModel.getGraph(), annotations.getGraph()));
        } else {
            data = multiModel;
        }*/
        
        // Dynamic merge of data and ontology
        // Dynamic merge of data and ontology and config information
        if (!ontologyData.isEmpty()) {
            //ontology.add(cm);
            data = ModelFactory.createModelForGraph(new Union(data.getGraph(), ontology.getGraph()));
        }
        
        /* Optional final transformation rules for viewing the total data set
        RDFNode transformRulesN = config.getPropertyValue(SemAppVocab.transformRulesURL);
        if (transformRulesN != null && transformRulesN instanceof Resource) {
            String filename = Util.toURL((Resource)transformRulesN, pcontext);
            try {
                List rules = RuleMap.loadRules(filename, new HashMap());
                reasoner = new GenericRuleReasoner(rules);
                reasoner.setTransitiveClosureCaching(true);
            } catch (IOException e) {
                logger.error("Failed to load closure rules: " + e);
            }
            data = ModelFactory.createInfModel(reasoner, data);
        }
        */
        data.setNsPrefix("pcv", SemAppVocab.NS);

    }

    /**
     * Preprocess an ontology file using a defined rule set.
     */
    public void ontNormalize(Model newOnt) {
        if (ontReasoner != null) {
            InfModel temp = ModelFactory.createInfModel(ontReasoner, newOnt);
            newOnt.add(temp.getDeductionsModel());
        }
    }
    
    public void normalize(Model newData) {
        // Smush any identifiable bNodes
        SmushUtil.prepareMerge(inverseFunctionProperties.iterator(), newData, data);

        // Apply closure rules
        if (reasoner != null) {
            InfModel temp = ModelFactory.createInfModel(reasoner, newData);
            newData.add(temp.getDeductionsModel());
        }
    }

    /**
     * Initialize the reasoner configuration. Should be called once during
     * the initial construction when the ontology model is available.
     */
    protected void initReasoner(String closureRules) {
        if (closureRules != null) {
            try {
                List<?> rules = RuleMap.loadRules(closureRules, new HashMap<Object, Object>());
                reasoner = new GenericRuleReasoner(rules);
                reasoner.setTransitiveClosureCaching(true);
                reasoner = (GenericRuleReasoner) reasoner.bindSchema(ontology);
            } catch (IOException e) {
                logger.error("Failed to load closure rules: " + e);
            }
        }
        
        // Note any inverse functional properties needed during smushing
        ResIterator i = getOntologyModel().listSubjectsWithProperty(RDF.type, OWL.InverseFunctionalProperty);
        inverseFunctionProperties = new ArrayList<Resource>();
        while (i.hasNext()) {
            Resource ifpR = i.nextResource();
            inverseFunctionProperties.add(ontology.createProperty(ifpR.getURI()));
        }
    }
    
    // Optional ontology rule processor
    protected void initOntReasoner(String ontClosureRules) {
        if (ontClosureRules != null) {
            try {
                List<?> rules = RuleMap.loadRules(ontClosureRules, new HashMap<Object, Object>());
                ontReasoner = new GenericRuleReasoner(rules);
            } catch (IOException e) {
                logger.error("Failed to load closure rules: " + e);
            }
        }
    }

    public MultiModel getMultiModel() {
        return multiModel;
    }
    public Model getDataModel() {
        return data;
    }
    public synchronized OntModel getOntologyModel() {
        if (ontology == null) {
            getDataModel();
        }
        return ontology;
    }  
    
}

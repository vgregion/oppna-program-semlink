/******************************************************************
 * File:        MultiModelImp.java
 * Created by:  Dave Reynolds
 * Created on:  08-Jun-2004
 * 
 * (c) Copyright 2004, Hewlett-Packard Development Company, LP, all rights reserved.
 * [See end of file]
 * $Id: MultiModelImpl.java,v 1.4 2005/02/04 14:40:44 der Exp $
 *****************************************************************/
package com.hp.hpl.jena.util;

import java.util.*;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.datatypes.TypeMapper;
import com.hp.hpl.jena.datatypes.xsd.impl.XMLLiteralType;
import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.graph.impl.LiteralLabel;
import com.hp.hpl.jena.mem.GraphMem;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.rdf.model.impl.ModelCom;
import com.hp.hpl.jena.shared.DoesNotExistException;
import com.hp.hpl.jena.shared.ReificationStyle;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;
import com.hp.hpl.jena.util.iterator.Map1;
import com.hp.hpl.jena.vocabulary.RDF;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * An implementation of MultiModels which uses a ModelMaker to access
 * the underlying models, which may be persistent.
 * This implementation is <em>brute force</em> - models statements are effectively
 * stored three times, once in individual
 * models, once a within a giant (static) union model and once indirectly in a meta
 * model which links statements to their sources. The latter could be replaced by
 * use of reification in the union model but for the current RDB design a reification
 * will occupy at least as much space (though still less that naive reification).
 * </p><p>
 * If retrieving single graphs need not be efficient then a construction
 * time option is available which does away with storing single graphs. Not only does
 * this save one copy of each triple but it avoids needing a pair of database tables
 * for each component model. The cost is that getModel is slow (because it has to
 * recreate the model from metadata data) and can only return models which can be
 * completely manifested withing memory.
 * </p><p>
 * This is a static MultiModel. Adding or removing statements from individual models
 * does not change the overall MultiModel collection. You have to explicitly replace
 * the model.
 * </p><p>
 * Alternative implementations which know something about the underlying store should
 * be able to do a much better job, in particular a ModelRDB should only need to store
 * statements once.
 * </p>
 * 
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.4 $ on $Date: 2005/02/04 14:40:44 $
 */
public class MultiModelImpl extends ModelCom implements MultiModel {

    /** The prefix used to uniquely identify members of this group within the maker */
    protected String idPrefix;
    
    /** The graph maker which can open, create or remove models from the group */
    protected GraphMaker gmaker;
    
    /** The meta graph which maps statement signatures to source models */
    protected Graph metaGraph;
    
    /** Construction-time flag which enables explicit storage of component models */
    private boolean componentModels = false;
    
    /** The suffix used for the union model which collects all the model data together */
    public static final String UNION_ID = "union";
    
    /** The suffix used for the meta model which maps statement signatures to source models */
    public static final String META_ID = "meta";
    
    /** A class used to mark each model within the meta model */
    protected static final Node ModelClass = Node.createURI("http://jena.hpl.hp.com/multiModel#ModelClass");
    
    /** A property used to link each model to a statement digest */
    protected static final Node hasStatement = Node.createURI("http://jena.hpl.hp.com/multiModel#hasStatement");
    
    /**
     * Constructor.
     * 
     * @param idPrefix The prefix used to uniquely identify members of this group within the maker 
     * @param maker The Modelmaker which should be used to manipulate the models within this group
     * @param componentModels set to false to suppress explicit storage of component models, this
     * saves space and database tables and the cost of making getModel slow
     */
    public MultiModelImpl(String idPrefix, ModelMaker maker, boolean componentModels) {
        super(maker.getGraphMaker().openGraph(idPrefix + UNION_ID));
        this.idPrefix = idPrefix;
        this.gmaker = maker.getGraphMaker();
        this.componentModels = componentModels;
        metaGraph = gmaker.openGraph(idPrefix + META_ID);
    }
    
    /**
     * Constructor.
     * 
     * @param idPrefix The prefix used to uniquely identify members of this group within the maker 
     * @param maker The Graphmaker which should be used to manipulate the models within this group
     * @param componentModels set to false to suppress explicit storage of component models, this
     * saves space and database tables and the cost of making getModel slow
     */
    public MultiModelImpl(String idPrefix, GraphMaker maker, boolean componentModels) {
        super(maker.openGraph(idPrefix + UNION_ID));
        this.idPrefix = idPrefix;
        this.gmaker = maker;
        this.componentModels = componentModels;
        metaGraph = gmaker.openGraph(idPrefix + META_ID);
    }
    
    /**
     * Constructor.
     * 
     * @param idPrefix The prefix used to uniquely identify members of this group within the maker 
     * @param maker The Modelmaker which should be used to manipulate the models within this group
     */
    public MultiModelImpl(String idPrefix, ModelMaker maker) {
        this(idPrefix, maker, true);
    }
   
    /**
     * Add a new model to the group under the given label. This
     * can be an arbitrary string but is typically a URI.
     */
    public synchronized void addModel(String uriLabel, Model model) {
        String label = idPrefix + uriLabel;
        if (hasLabel(uriLabel)) {
        	//
        }
        // Note the presence of the model in the persistent meta model
        Node modelNode = Node.createURI(uriLabel);
        metaGraph.add(new Triple(modelNode, RDF.Nodes.type, ModelClass));

        doAddModel(label, model, modelNode, true);
    }
    
    /**
     * Check if the multimodel already has a model associated with the given
     * label.
     */
    protected synchronized boolean hasLabel(String uriLabel) {
        Node modelNode = Node.createURI(uriLabel);
        return metaGraph.contains(modelNode, RDF.Nodes.type, ModelClass);
    }
    
    /**
     * The internals of addModel ignoring label handling for reuse within replace
     */
    private void doAddModel(String label, Model model, Node modelNode, boolean addLabel) {
        if (componentModels) {
            // Add the data to the created model within the group        
            Graph copy = gmaker.openGraph(label);
            TransactionHandler th = copy.getTransactionHandler();
            if (th.transactionsSupported()) th.begin();
            copy.getBulkUpdateHandler().add(model.getGraph());
            if (th.transactionsSupported()) th.commit();
            // Closing an individual model seems to close the connection so
            // this is ommited for now
            // copy.close();
        }
        
        // Add the data to the union and the provenance to the meta model
        TransactionHandler th = graph.getTransactionHandler();
        TransactionHandler thm = metaGraph.getTransactionHandler();
        if (th.transactionsSupported()) th.begin();
        if (thm.transactionsSupported()) thm.begin();
        for (Iterator i = model.getGraph().find(null, null, null); i.hasNext(); ) {
            Triple t = (Triple)i.next();
            graph.add(t);
            metaGraph.add(new Triple(modelNode, hasStatement, digest(t)));
        }
        if (addLabel) {
            metaGraph.add(new Triple(modelNode, RDF.Nodes.type, ModelClass));
        }
        if (th.transactionsSupported()) th.commit();
        if (thm.transactionsSupported()) thm.commit();
    }
    
    /**
     * Remove a model from the group, as identified by the given
     * ID label.
     */
    public synchronized void removeModel(String uriLabel)  {
        String label = idPrefix + uriLabel;
        Node modelNode = Node.createURI(uriLabel);
        doRemoveModel(label, modelNode, true);
        // Note the removal of the model in the persistent meta model
        metaGraph.delete(new Triple(modelNode, RDF.Nodes.type, ModelClass));
    }
    
    /**
     * Internals of removeModel for case where we are storing component graphs
     * @param label the internal label
     * @param modelNode the meta node marker for the model being removed
     * @param removeGraph if true the whole graph is removed rather than just emptied
     */
    private void doRemoveModelComponents(String label, Node modelNode, boolean removeGraph) {
        try {
            Graph oGraph = gmaker.openGraph(label, true);
            
            // Remove the data from the union and meta model
            TransactionHandler th = graph.getTransactionHandler();
            TransactionHandler thm = metaGraph.getTransactionHandler();
            TransactionHandler tho = oGraph.getTransactionHandler();
            if (th.transactionsSupported()) th.begin();
            if (thm.transactionsSupported()) thm.begin();
            if (!removeGraph && tho.transactionsSupported()) tho.begin();
            for (Iterator i = oGraph.find(null, null, null); i.hasNext(); ) {
                Triple t = (Triple)i.next();
                Node digest = digest(t);
                if (tripleRefCountOne(digest)) {
                    graph.delete(t);
                }
                if (!removeGraph) {
                    i.remove();
                }
                metaGraph.delete(new Triple(modelNode, hasStatement, digest));
            }
            if (th.transactionsSupported()) th.commit();
            if (thm.transactionsSupported()) thm.commit();
            if (!removeGraph && tho.transactionsSupported()) tho.commit();

            // Closing an individual model seems to close the connection so
            // this is ommited for now
//            oGraph.close();
            
            // Remove the graph itself
            if (removeGraph) {
                gmaker.removeGraph(label);
            }
        } catch (DoesNotExistException e) {
            // No model needs to be removed
        }
    }
    
    /**
     * Internals of removeModel for case where we are not storing component graphs
     * @param label the internal label
     * @param modelNode the meta node marker for the model being removed
     * @param removeGraph if true the whole graph is removed rather than just emptied
     */
    private void doRemoveModelNoComponents(String label, Node modelNode, boolean removeGraph) {
        // Remove the data from the union and meta model
        TransactionHandler th = graph.getTransactionHandler();
        TransactionHandler thm = metaGraph.getTransactionHandler();
        if (th.transactionsSupported()) th.begin();
        if (thm.transactionsSupported()) thm.begin();
        for (Iterator i = metaGraph.find(modelNode, hasStatement, null); i.hasNext(); ) {
            Triple metaT = (Triple)i.next();
            Node digest = metaT.getObject();
            Triple t = undigestTriple(digest.getLiteral().getLexicalForm());
            if (tripleRefCountOne(digest)) {
                graph.delete(t);
            }
            i.remove();
        }
        if (th.transactionsSupported()) th.commit();
        if (thm.transactionsSupported()) thm.commit();
    }
    
    /**
     * Internals of removeModel
     * @param label the internal label
     * @param modelNode the meta node marker for the model being removed
     * @param removeGraph if true the whole graph is removed rather than just emptied
     */
    private void doRemoveModel(String label, Node modelNode, boolean removeGraph) {
        if (componentModels) {
            doRemoveModelComponents(label, modelNode, removeGraph);
        } else {
            doRemoveModelNoComponents(label, modelNode, removeGraph);
        }
    }
    
    /**
     * Return the model corresponding to the given label, returns
     * null if there is no such model.
     */
    public Model getModel(String uriLabel) {
        if (!componentModels) {
            // Create an in-memory copy of the graph from the metadata
            // NO caching.
            Node modelNode = Node.createURI(uriLabel);
            if (metaGraph.contains(modelNode, RDF.Nodes.type, ModelClass)) {
                Graph g = new GraphMem(ReificationStyle.Standard);
                for (Iterator i = metaGraph.find(modelNode, hasStatement, null); i.hasNext(); ) {
                    String digest = ((Triple)i.next()).getObject().getLiteral().getLexicalForm();
                    Triple t = undigestTriple(digest);
                    g.add(t);
                }
                return ModelFactory.createModelForGraph(g);
            } else {
                return null;
            }
        } else {
            String label = idPrefix + uriLabel;
            try {
                return new ModelCom(gmaker.openGraph(label, true));
            } catch (DoesNotExistException e) {
                return null;
            }
        }
    }
    
    /**
     * Remove any Model currently regestered against the uriLabel
     * and replaces it with the given new model.
     */ 
    public void replaceModel(String uriLabel, Model model) {
        String label = idPrefix + uriLabel;
        Node modelNode = Node.createURI(uriLabel);
        if (hasLabel(uriLabel)) {
            doInplaceReplace(label, model, modelNode);
            // Check for special case where the model being added is one of ours already
            // Only reliable way to do this seems to be to touch one graph and see if
            // the other sees the touch
            if (componentModels) {
                Graph ourGraph = gmaker.openGraph(label, true);
                Graph newGraph = model.getGraph();
                Node testN = Node.createAnon();
                Triple test = new Triple(testN, RDFS.Nodes.seeAlso, testN);
                ourGraph.add(test);
                boolean sameGraph = newGraph.contains(test);
                ourGraph.delete(test);
                if (!sameGraph) {
                    // Add the data to the created model within the group        
                    Graph copy = gmaker.openGraph(label);
                    TransactionHandler th = copy.getTransactionHandler();
                    if (th.transactionsSupported()) th.begin();
                    copy.getBulkUpdateHandler().add(model.getGraph());
                    if (th.transactionsSupported()) th.commit();
                }
            }
        } else {
            doAddModel( label, model, modelNode, true);
        }
    }
    
    /**
     * Inplace case. A model has been extracted from a withComponents multimodel,
     * modified in place, and now we want to update to see those changes.
     * Assumes the set of statements will fit in memory in digest form.
     * <p>The current implementation will not correctly reference count,
     * on the grounds that this mode of working is intended for high performance situations
     * where multiple references to the same statement are not expected.
     */
    protected void doInplaceReplace(String label, Model model, Node modelNode) {
        TransactionHandler th = graph.getTransactionHandler();
        TransactionHandler thm = metaGraph.getTransactionHandler();
        if (th.transactionsSupported()) th.begin();
        if (thm.transactionsSupported()) thm.begin();
        
        // Find all statements previously in this component model
        Set prior = new HashSet();
        for (Iterator i = metaGraph.find(modelNode, hasStatement, null); i.hasNext(); ) {
            Triple metaT = (Triple)i.next();
            Node digest = metaT.getObject();
            prior.add(digest);
        }
        
        // Find all statements now in this component model
        Set current = new HashSet();
        for (Iterator i = model.getGraph().find(null, null, null); i.hasNext(); ) {
            current.add(digest((Triple)i.next()));
        }
        
        // Process deletes
        for (Iterator i = prior.iterator(); i.hasNext(); ) {
            Node digest = (Node)i.next();
            if (!current.contains(digest)) {
                graph.delete(undigestTriple(digest.getLiteral().getLexicalForm()));
                metaGraph.delete(new Triple(modelNode, hasStatement, digest));
            }
        }
        
        // Process adds
        for (Iterator i = current.iterator(); i.hasNext(); ) {
            Node digest = (Node)i.next();
            if (!prior.contains(digest)) {
                graph.add(undigestTriple(digest.getLiteral().getLexicalForm()));
                metaGraph.add(new Triple(modelNode, hasStatement, digest));
            }
        }

        if (th.transactionsSupported()) th.commit();
        if (thm.transactionsSupported()) thm.commit();
        
    }
    
    /**
     * Return an iterator over the labels for all models in this
     * group.
     */
    public Iterator listModelLabels() {
        // Could do the rewrite in streaming mode but are assumign that
        // the number of models in the group is not that large
        ArrayList list = new ArrayList();
        for (Iterator i = metaGraph.find(null, RDF.Nodes.type, ModelClass); i.hasNext(); ) {
            Triple t = (Triple)i.next();
            list.add(t.getSubject().getURI());
        }
        return list.iterator();
    }
    
    /**
     * Return the labels of all models which contain the given 
     * RDF statement.
     */
    public ExtendedIterator listModelLabelsForStmt(Statement s) {
        Triple t = s.asTriple();
        return metaGraph.find(null, hasStatement, digest(t)).mapWith(
            new Map1() {
                public Object map1(Object value) {
                    return ((Triple)value).getSubject().getURI();
                }
            }
        );
    }
    
    /**
     * Return a random selection from the labels of all models
     * which contain the given RDF statement.
     */
    public String getModelLabelForStmt(Statement s) {
        ExtendedIterator i = listModelLabelsForStmt(s);
        if (i.hasNext()) {
            String result = (String)i.next();
            i.close();
            return result;
        } else {
            return null;
        }
    }
    
    /**
     * Compute a unique signature for a triple. In this case the triple is presumed
     * to be from a model in the group so bNodes have usable identity and we don't
     * need to worry about graph coloring.
     */
    public static Node digest(Triple t) {
        StringBuffer digest = new StringBuffer();
        digest(t.getSubject(), digest);
        digest.append("|");
        digest(t.getPredicate(), digest);
        digest.append("|");
        digest(t.getObject(), digest);
        return Node.createLiteral(digest.toString());
    }
    
    /**
     * Reverse a digest to recreate the Triple
     */
    private static Triple undigestTriple(String digest) {
        int index1 = digest.indexOf('|');
        Node subject = undigest(digest.substring(0, index1));
        int index2 = digest.indexOf('|', index1+1);
        Node predicate = undigest(digest.substring(index1+1, index2));
        Node object = undigest(digest.substring(index2+1));
        return new Triple(subject, predicate, object);
    }
    

    /**
     * Compute a unique signature for a node. In this case the node is presumed
     * to be from a triple in a model in the group so bNodes have usable identity and we don't
     * need to worry about graph coloring.
     */
    private static void digest(Node n, StringBuffer digest) {
        if (n instanceof Node_URI) {
            digest.append("U");
            digest.append(n.getURI());
        } else if (n instanceof Node_Blank) {
            digest.append("A");
            digest.append(n.getBlankNodeId());
        } else if (n instanceof Node_Literal) {
            LiteralLabel ll = n.getLiteral();
            digest.append("L");
            digest.append(ll.getLexicalForm());
            String dt = ll.getDatatypeURI();
            if (dt != null) {
                digest.append("^");
                digest.append(dt);
            }
            String lang = ll.language();
            if (lang != null && lang.length() > 0) {
                digest.append("@");
                digest.append(lang);
            }
        } else {
            digest.append("*");
        }
    }
    
    /**
     * Reverse a digest to recreate the node.
     */
    private static Node undigest(String digest) {
        switch (digest.charAt(0)) {
            case 'U':
                return Node.createURI(digest.substring(1));
         
            case 'A':
                return Node.createAnon(new AnonId(digest.substring(1)));
                
            case 'L':
                int index = digest.lastIndexOf('@');
                String lang = null;
                if (index != -1) {
                    lang = digest.substring(index+1);
                    // Only plain literals can have a lang
                    return Node.createLiteral(digest.substring(1, index), lang, false);
                }
                index = digest.lastIndexOf('^');
                if (index != -1) {
                    // A typed literal
                    String dtUri = digest.substring(index+1);
                    String lex = digest.substring(1, index);
                    if (dtUri.equals(XMLLiteralType.theXMLLiteralType.getURI())) {
                        // An XML literal
                        return Node.createLiteral(lex, null, true);
                    } else {
                        RDFDatatype dt = TypeMapper.getInstance().getTypeByName(dtUri);
                        return Node.createLiteral(lex, null, dt);
                    }
                } else {
                    // A plain literal
                    return Node.createLiteral(digest.substring(1));
                }
                
            default:
                return Node.ANY;
        }
    }
    
    /**
     * Check if the given triple digest is present exactly once in the union.
     */
    private boolean tripleRefCountOne(Node digest) {
        ExtendedIterator i = metaGraph.find(null, hasStatement, digest);
        if (! i.hasNext()) return false;
        i.next();
        if (! i.hasNext()) return true;
        i.close();
        return false;
    }
    
}


/*
    (c) Copyright Hewlett-Packard Development Company, LP 2004
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:

    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.

    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.

    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
/******************************************************************
 * File:        SmushUtil.java
 * Created by:  Dave Reynolds
 * Created on:  14-Jun-2004
 * 
 * (c) Copyright 2004, Hewlett-Packard Development Company, LP, all rights reserved.
 * [See end of file]
 * $Id: SmushUtil.java,v 1.1 2004/11/11 10:24:36 der Exp $
 *****************************************************************/
package com.hp.hpl.jena.util;

import com.hp.hpl.jena.rdf.model.*;
import java.util.*;

/**
 * Collection of simple functions to help with bNode merging. 
 * 
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.1 $ on $Date: 2004/11/11 10:24:36 $
 */
public class SmushUtil {
    
    /**
     * Prepares an incoming model for merger with an existing union by
     * locating all mergable bNodes using the given list of InverseFunctionalProperties.
     * Each set of equivalent nodes is replaced by at most one bNode. If there are
     * multiple non-blank resources in the set they are all retained. If the
     * incoming data supplies a URI for an entity currently only known by
     * bNode then the union data will be rewritten to use the URI.
     * @param ifps a iterator over resources representing the InverseFunctionalProperties
     * to be used for merging
     * @param incoming the new data to be rewritten
     * @param data the existing data prior to merge, it is assumed that each 
     * identifiable entity in this data has already been reduce to minamal form 
     * (at most one bNode per entity) though it is possible for an entity to be
     * currently a bNode but be identified in the incoming data
     */
    public static void prepareMerge(Iterator ifps, Model incoming, Model data) {
        Map incomingRewrites = new HashMap();
        Map dataRewrites = new HashMap();
        
        while (ifps.hasNext()) {
            // Step 1. Find all the equivalence chains within the new data
            Map idChains = new HashMap();
            Resource ifpR = (Resource)ifps.next();
            Property ifp = (ifpR instanceof Property) 
                             ? (Property) ifpR : data.createProperty(ifpR.getURI());
            StmtIterator si = incoming.listStatements(null, ifp, (RDFNode)null);
            while (si.hasNext()) {
                Statement s = si.nextStatement();
                RDFNode marker = s.getObject();
                Set idChain = (Set)idChains.get(marker);
                if (idChain == null) {
                    idChain = new HashSet();
                    idChains.put(marker, idChain);
                }
                idChain.add(s.getSubject());
            }
            
            // Step 2. For each chain check what is known about the marker in the existing data
            for (Iterator i = idChains.entrySet().iterator(); i.hasNext(); ) {
                Map.Entry entry = (Map.Entry)i.next();
                RDFNode marker = (RDFNode) entry.getKey();
                Set idChain = (Set)entry.getValue();
                
                ResIterator ri = data.listSubjectsWithProperty(ifp, marker);
                Resource id = ri.hasNext() ? ri.nextResource() : null;
                ri.close();
                
                int chainSize = idChain.size();
                if (id != null && ! idChain.contains(id)) chainSize++;
                
                if (chainSize > 1) {
                    // Find node to rewrite to, preferably a non-blank if any
                    Resource target = id;
                    if (target == null || target.isAnon()) {
                        for (Iterator j = idChain.iterator(); j.hasNext(); ) {
                            Resource candidate = (Resource)j.next();
                            if ( ! candidate.isAnon()) {
                                target = candidate;
                                break;
                            }
                            if (target == null) {
                                target = candidate;
                            }
                        }
                    }
                    
                    if (target != id && id != null ) {
                        addRewrite(dataRewrites, id, target);
                    }
                    for (Iterator j = idChain.iterator(); j.hasNext(); ) {
                        Resource r = (Resource)j.next();
                        if (r.isAnon() && r != target) {
                            addRewrite(incomingRewrites, r, target);
                        }
                    }
                }
            }
        }

        // Make sure any data rewrite that affect incoming rewrites are copied across
        for (Iterator j = dataRewrites.entrySet().iterator(); j.hasNext(); ) {
            Map.Entry entry = (Map.Entry)j.next();
            Resource id = (Resource)entry.getKey();
            if (incomingRewrites.containsValue(id)) {
                Resource target = (Resource)entry.getValue();
                for (Iterator k = incomingRewrites.entrySet().iterator(); k.hasNext(); ) {
                    Map.Entry kEntry = (Map.Entry)k.next();
                    if (kEntry.getValue().equals(id)) {
                        kEntry.setValue(target);
                    }
                }
            }
        }
                
        // Perform the rewrites
        if ( ! dataRewrites.isEmpty()) {
            rewrite(dataRewrites, data);
        }
        if ( ! incomingRewrites.isEmpty()) {
            rewrite(incomingRewrites, incoming);
        }
    }
    
    /**
     * Add a new rewrite to the current set. If a rewrite already exists
     * for the current node then we pick the most ground one.
     */
    private static void addRewrite(Map rewrites, Resource r, Resource target) {
        Resource priorTarget = (Resource)rewrites.get(r);
        if (priorTarget == null || priorTarget.isAnon()) {
            rewrites.put(r, target);
        }
    }
    
    /**
     * Performs a set of node rewrites specified in the mapping table.
     * @param rewrites a map from old Resource to new Resource, all statements 
     * with old as subject or object will be replaced by equivalent
     * statements with new as the subject/object
     * @param model the model to be processed, N.B a transaction will be
     * used during rewrites this may not be a good idea if there is an outer
     * transaction in progress
     */
    public static void rewrite(Map rewrites, Model model) {
        // Rewrite the smushable nodes
        List additions = new ArrayList();
        if (model.supportsTransactions()) model.begin();
        for (Iterator i = rewrites.entrySet().iterator(); i.hasNext(); ) {
            Map.Entry entry = (Map.Entry)i.next();
            Resource oldR = (Resource)entry.getKey();
            Resource newR = (Resource)entry.getValue();
            // pass 1 = a statements with target node as object
            StmtIterator si = model.listStatements(null, null, oldR);
            while (si.hasNext()) {
                Statement s = si.nextStatement();
                si.remove();
                additions.add(model.createStatement(s.getSubject(), s.getPredicate(), newR));
            }
            // pass 2 = a statements with target node as subject
            si = model.listStatements(oldR, null, (RDFNode)null);
            while (si.hasNext()) {
                Statement s = si.nextStatement();
                si.remove();
                additions.add(model.createStatement(newR, s.getPredicate(), s.getObject()));
            }
            model.add(additions);
        }
        if (model.supportsTransactions()) model.commit();
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
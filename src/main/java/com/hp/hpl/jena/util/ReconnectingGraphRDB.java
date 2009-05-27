/******************************************************************
 * File:        ReconnectingGraphRDB.java
 * Created by:  Dave Reynolds
 * Created on:  24-Jul-2004
 * 
 * (c) Copyright 2004, Hewlett-Packard Development Company, LP, all rights reserved.
 * [See end of file]
 * $Id: ReconnectingGraphRDB.java,v 1.2 2005/06/24 11:06:52 der Exp $
 *****************************************************************/
package com.hp.hpl.jena.util;

import com.hp.hpl.jena.db.DBConnection;
import com.hp.hpl.jena.db.GraphRDB;
import com.hp.hpl.jena.db.IDBConnection;
import com.hp.hpl.jena.graph.*;
import com.hp.hpl.jena.graph.impl.GraphBase;
import com.hp.hpl.jena.graph.impl.TransactionHandlerBase;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.shared.ReificationStyle;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Used to wrap an existing GraphRDB to enable to reconnect after
 * the database drops the connection.
 * <p>
 * The current implementation is fairly brutal, an entire new GraphRDB
 * will be created each time. It only supports reconnectable find/add/delete
 * and getTransactionHandler.begin(). The bulk update handler is not
 * reconnecting.
 *
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.2 $ on $Date: 2005/06/24 11:06:52 $
 */
public class ReconnectingGraphRDB extends GraphBase {
    
    static Log logger = LogFactory.getLog(ReconnectingGraphRDB.class) ;
    
    /** The database URL */
    protected String dbURL;
    
    /** The database login name */
    protected String dbUser;
    
    /** The database login password */
    protected String dbPassword;
    
    /** The database type */
    protected String dbType;
    
    /** The name of the database graph being access */
    protected String dbName;
    
    /** The reification style to use */
    protected int reificationStyle;
    
    /** The current underlying GraphRDB */
    protected GraphRDB baseGraph;
    
    /** The associated transaction handler */
    protected TransactionHandler th;
    
    /**
     * Create a graph that will auto reconnect after
     * dropped connections.
     *  
     * @param dbURL The database URL
     * @param dbUser The database login name
     * @param dbPassword The database login password
     * @param dbType The database type
     * @param dbName The name of the database graph to be opened
     * @param propGraph Graph defining the configuration properties for this GraphRDB, may be null
     * @param reificationStyle the reificationStyle to use
     * @param isNew set to true to create a non-existent graph
     */
    public ReconnectingGraphRDB(String dbURL, String dbUser, String dbPassword, 
                                String dbType, String dbName, Graph propGraph,
                                ReificationStyle reificationStyle, boolean isNew) {
        this.dbURL = dbURL;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.dbType = dbType;
        this.dbName = dbName;
        this.reificationStyle = GraphRDB.styleRDB( reificationStyle );
        IDBConnection conn = new DBConnection(dbURL, dbUser, dbPassword, dbType);
        Graph p = isNew ? (propGraph == null ? conn.getDefaultModelProperties().getGraph() : propGraph) : null;
        baseGraph = new GraphRDB(conn, dbName, p, this.reificationStyle, isNew);
    }
    
    /**
     * Create a graph that will auto reconnect after
     * dropped connections. Assumes this the graph exists.
     *  
     * @param dbURL The database URL
     * @param dbUser The database login name
     * @param dbPassword The database login password
     * @param dbType The database type
     * @param dbName The name of the database graph to be opened
     */
    public ReconnectingGraphRDB(String dbURL, String dbUser, String dbPassword, 
                                String dbType, String dbName) {
        this(dbURL, dbUser, dbPassword, dbType, dbName, null, ModelFactory.Standard, false);
    }
    
    /**
     * Attempt to reconnect the underlying Graph RDB
     */
    public void reconnect() {
        if (baseGraph != null) {
            baseGraph.close();
        } 
        logger.warn("Attempt to reconnect GraphRDB to database");
        IDBConnection conn = new DBConnection(dbURL, dbUser, dbPassword, dbType);
        baseGraph = new GraphRDB(conn, dbName, null, reificationStyle, false);
    }
    

    /**
     * Find which retries if there is an exception. Unfortunately the
     * exceptions are manifested in many mysterious ways so we have
     * retry on any error if it might not be an SQL connection problem.
     */
    public ExtendedIterator graphBaseFind( TripleMatch m ) {
        try {
            return baseGraph.find(m);
        } catch (Exception e) {
            reconnect();
            return baseGraph.find(m); 
        }
    }
    
    /**
     * Carry out an add operation, one retry if fails.
     */
    public void performAdd( Triple t ) {
        try {
            baseGraph.performAdd(t);
        } catch (Exception e) {
            reconnect();
            baseGraph.performAdd(t);
        }        
    }
        
    /**
     * Carry out an delete operation, one retry if fails.
     */
    public void performDelete( Triple t ) {
        try {
            baseGraph.performDelete(t);
        } catch (Exception e) {
            reconnect();
            baseGraph.performDelete(t);
        }
    }
    
    
    /**
     * Return the underlying GraphRDB.
     * This is exposed mostly for testing purposes do not use
     * unless you know what you are doing.
     */
    public GraphRDB getBaseGraph() {
        return baseGraph;
    }
    
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.graph.Graph#getBulkUpdateHandler()
     * <p>
     * Warning: The returned handler will not be reconnectable because
     * we can't update the graph that the handler refers.
     */
     public BulkUpdateHandler getBulkUpdateHandler() {
         return baseGraph.getBulkUpdateHandler();
     }

    /* 
     * (non-Javadoc)
     * @see com.hp.hpl.jena.graph.Graph#getReifier()
     */
    public Reifier getReifier() {
        return baseGraph.getReifier();
    }
     
    /* (non-Javadoc)
     * @see com.hp.hpl.jena.graph.Graph#getPrefixMapping()
     */
    public PrefixMapping getPrefixMapping() {
        return baseGraph.getPrefixMapping(); 
    }


    /* (non-Javadoc)
     * @see com.hp.hpl.jena.graph.Graph#getTransactionHandler()
     * Warning: The returned handler will not be reconnectable because
     * we can't update the graph that the handler refers.
     */
    public TransactionHandler getTransactionHandler() {
        if (th == null) {
            th = new ReconnectingDBTransactionHandler(this);
        }
        return th;
    }

    /* (non-Javadoc)
     * @see com.hp.hpl.jena.graph.Graph#close()
     */
    public synchronized void close() {
        baseGraph.close();
    }
    
    /**
     * Remove this Graph entirely from the database.
     * 
     * This operation is unique to GraphRDB - it removes all
     * mention of this graph from the database - after removing
     * a graph it is recommended to immediately call close()
     * (there is no other useful operation that may be 
     * performed, and so no reason to keep the Graph around).
     */
    public synchronized void remove() {
        baseGraph.remove();
    }
    
    /**
     * Transaction handler implementation that will attempt
     * to reconnect during a begin if required.
     */
    public class ReconnectingDBTransactionHandler extends TransactionHandlerBase {
        
        /** The reconnectable graph this is an handler for */ 
        protected ReconnectingGraphRDB parent;
    
        /** The last known good handler for the base graph */
        protected TransactionHandler lastTH;
        
        /**
         * Construct a transaction handler for the database.
         */
        public ReconnectingDBTransactionHandler(ReconnectingGraphRDB parent ) {
            super();
            this.parent = parent;
            this.lastTH = parent.getBaseGraph().getTransactionHandler();
        }

        public boolean transactionsSupported() {
            try {
                return lastTH.transactionsSupported();
            } catch (Exception e) {
                parent.reconnect();
                lastTH = parent.getBaseGraph().getTransactionHandler();
                return lastTH.transactionsSupported();
            }
        }

        public void begin() {
            try {
                lastTH.begin();
            } catch (Exception e) {
                parent.reconnect();
                lastTH = parent.getBaseGraph().getTransactionHandler();
                lastTH.begin();
            }
        }

        public void abort() {
            lastTH.abort();
        }

        public void commit() {
            lastTH.commit();
        }

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
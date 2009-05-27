/******************************************************************
 * File:        MultiModel.java
 * Created by:  Dave Reynolds
 * Created on:  08-Jun-2004
 * 
 * (c) Copyright 2004, Hewlett-Packard Development Company, LP, all rights reserved.
 * [See end of file]
 * $Id: MultiModel.java,v 1.1 2004/11/11 10:24:36 der Exp $
 *****************************************************************/
package com.hp.hpl.jena.util;

import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

/**
 * Interface for a group of models that supports access to the union
 * of the whole group as it if were a single model.
 * 
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.1 $ on $Date: 2004/11/11 10:24:36 $
 */
public interface MultiModel extends Model, ModelCon, ModelGroup {
    
    /**
     * Return the labels of all models which contain the given 
     * RDF statement.
     */
    ExtendedIterator listModelLabelsForStmt(Statement s) ;
    
    /**
     * Return a random selection from the labels of all models
     * which contain the given RDF statement.
     */
    String getModelLabelForStmt(Statement s) ;

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
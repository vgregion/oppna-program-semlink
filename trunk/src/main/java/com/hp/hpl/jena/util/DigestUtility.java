/******************************************************************
 * File:        DigestUtility.java
 * Created by:  Dave Reynolds
 * Created on:  11-Jun-2004
 * 
 * (c) Copyright 2004, Hewlett-Packard Development Company, LP, all rights reserved.
 * [See end of file]
 * $Id: DigestUtility.java,v 1.1 2004/11/11 10:24:36 der Exp $
 *****************************************************************/
package com.hp.hpl.jena.util;

import java.security.MessageDigest;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.hpl.jena.datatypes.xsd.XSDDatatype;
import com.hp.hpl.jena.rdf.model.*;

/**
 * Some static utilities to simplify the generation, recording and
 * retrieval of digests of a data string as RDF literal values.
 * 
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.1 $ on $Date: 2004/11/11 10:24:36 $
 */
public class DigestUtility {

    /** MD5 hash generator */
    protected static MessageDigest digester;

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        } catch (java.security.NoSuchAlgorithmException e) {
            Log logger = LogFactory.getLog(DigestUtility.class) ;
            logger.warn("Failed to find an MD5 provider", e);
        }
    }
    
    /**
     * Digest a byte[] (e.g. the internals of a String) and create
     * an RDF literal to represent it (currently it will be the
     * base64 encoding of an MD5 digest as a typed literal).
     * @param data the data to digest
     * @param model a model which can be used to manufacture the literal
     * @return rdf literal of type xsd:base64binary encoding the MD5 digest
     */
    public synchronized static Literal digest(byte[] data, Model model) {
        digester.update(data);
        byte[] res = digester.digest();
        digester.reset();
        return model.createTypedLiteral(res, XSDDatatype.XSDbase64Binary);
    }
    
    /**
     * Check a digest against a new data value
     * @param data the data to digest
     * @param digest literal of type xsd:base64binary encoding the MD5 digest
     * @return true if the digest of the new data matches the given prior digest
     */
    public synchronized static boolean digestMatches(byte[] data, Literal digest) {
        digester.update(data);
        byte[] res = digester.digest();
        digester.reset();
        Object digestO = digest.getValue();
        if (digestO instanceof byte[]) {
            return Arrays.equals(res, (byte[])digestO);
        } else {
            return false;
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
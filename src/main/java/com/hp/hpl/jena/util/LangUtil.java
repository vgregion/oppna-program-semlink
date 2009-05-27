/******************************************************************
 * File:        LangUtil.java
 * Created by:  Dave Reynolds
 * Created on:  05-Nov-2004
 * 
 * (c) Copyright 2004, Hewlett-Packard Development Company, LP
 * [See end of file]
 * $Id: LangUtil.java,v 1.1 2004/11/11 10:24:36 der Exp $
 *****************************************************************/

package com.hp.hpl.jena.util;

import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDFS;

/**
 * Utilities for retrieving appropriately lang-tagged literal values from
 * multi-lingual data.
 * 
 * @author <a href="mailto:der@hplb.hpl.hp.com">Dave Reynolds</a>
 * @version $Revision: 1.1 $
 */

public class LangUtil {
	// Constants used to order the language match cases
	private static final int FULL_MATCH = 4;
	private static final int PRIMARY_TAG_ONLY = 3;
	private static final int PRIMARY_TAG = 2;
	private static final int NO_TAG = 1;

    /**
     * Returns the literal value of the given resource/property pair which
     * best matches the given language tag. The language tag matches are ordered
     * crudely according to the following odering:
     * <ol>
     *   <li> full match (en-US ~ en-US) </li>
     *   <li> primary tags match and one tag is only a primary tag (en-US ~ en) </li>
     *   <li> primary tags match, secondary tags differ (en-US ~ en-scouse) </li>
     *   <li> lang free tag (en-US ~ <blank>) </li>
     * </ol>
     * Selections in the event of a tie are arbitrary.
     * 
     * @param resource the subject resource 
     * @param prop the property to be retrieved
     * @param langTarget the preferred language code
     * @return the best matching literal value or null if there is no value at all
     */
    public static String getBestLiteralValue(Resource resource, Property prop, String langTarget) {
    	int bestScore = -1;
    	String bestMatch = null;
    	String langTargetPT = getPrimaryTag(langTarget);
    	StmtIterator i = resource.listProperties(prop);
    	while (i.hasNext()) {
    		RDFNode valN = i.nextStatement().getObject();
    		if (valN instanceof Literal) {
    			Literal val = (Literal)valN;
    			if (langTarget == null || langTarget.equals("")) {
    				return val.getLexicalForm();
    			}
    			String lang =  val.getLanguage();
    			int score = 0;
    			if (lang == null || lang.equals("")) {
    				score = NO_TAG;
    			} else if (lang.equalsIgnoreCase(langTarget)) {
    				return val.getLexicalForm();
    			} else if (getPrimaryTag(lang).equalsIgnoreCase(langTargetPT)) {
    				score = hasSecondaryTag(lang) ? PRIMARY_TAG : PRIMARY_TAG_ONLY;
    			}
    			if (score > bestScore) {
    				bestScore = score;
    				bestMatch = val.getLexicalForm();
    			}
    		}
    	}
    	return bestMatch;
    }

    /**
     * Determine a displayable name for a resource. This will pick the RDFS label
     * from the closest matching language or use the shortform of the URI.
     * @param node the node to be displayed 
     * @param langTarget the preferred language code
     */
    public static String getDisplayName(RDFNode node, String langTarget) {
    	if (node instanceof Resource) {
    		Resource r = (Resource)node;
    		String name = getBestLiteralValue(r, RDFS.label, langTarget);
    		if (name == null) {
    			if (r.isAnon()) {
    				return "[]";
    			} else {
    				return r.getModel().shortForm(r.getURI());
    			}
    		}
    		return name;
    	} else if (node instanceof Literal) {
    		return ((Literal)node).getLexicalForm();
    	} else {
    		return node.toString();
    	}
    }
    
    /**
     * Return the primary tag from a language tag.
     */
    public static String getPrimaryTag(String lang) {
    	if (lang == null) return null;
    	int index = lang.indexOf('-');
    	if (index == -1) {
    		return lang;
    	} else {
    		return lang.substring(0, index);
    	}
    }
    
    /**
     * Return true if the language tag has a secondary tag.
     */
    public static boolean hasSecondaryTag(String lang) {
    	return (lang != null) && (lang.indexOf('-') != -1);
    }
}


/*
    (c) Copyright 2002 Hewlett-Packard Development Company, LP
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

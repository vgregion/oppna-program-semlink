package com.m1.semanticapp.stopword;

import java.io.Serializable;

public abstract class Stopword implements Serializable {
	  
	private static final long serialVersionUID = 4750748087528638730L;

	/** 
	   * Returns true if the given string is a stop word.
	   */
	  public abstract boolean isStopword(String str);
	  
}
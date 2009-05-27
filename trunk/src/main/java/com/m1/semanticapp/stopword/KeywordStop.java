package com.m1.semanticapp.stopword;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;

public class KeywordStop extends Stopword {

	private static final long serialVersionUID = -5785927808334358553L;
	
	/** The hashtable containing the list of stopwords */
	private static Hashtable<String, Double> m_Stopwords = null;

	static {
		
		if (m_Stopwords == null) {
			m_Stopwords = new Hashtable<String, Double>();
			Double dummy = new Double(0);
			File txt = new File("src/main/webapp/data/stopwords/stopwords_keyword.txt");	
			InputStreamReader is;
			String sw = null;
			try {
				is = new InputStreamReader(new FileInputStream(txt), "UTF-8");
				BufferedReader br = new BufferedReader(is);				
				while ((sw=br.readLine()) != null)  {
					m_Stopwords.put(sw, dummy);   
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/** 
	 * Returns true if the given string is a stop word.
	 */
	@Override
	public boolean isStopword(String keyword) {
		keyword = keyword.trim();
		boolean stopword;
		System.out.println("vad kommer in: "+keyword);
		if(keyword.length()>2){
			stopword = m_Stopwords.containsKey(keyword.toLowerCase());
		}else{
			stopword = true;
		}
		return stopword;
	}

}
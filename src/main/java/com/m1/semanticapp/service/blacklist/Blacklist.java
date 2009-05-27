package com.m1.semanticapp.service.blacklist;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;

import com.m1.semanticapp.service.blacklist.Blacklist;

public class Blacklist extends SimpleJdbcDaoSupport {

	protected final Log logger = LogFactory.getLog(getClass());
	
	public boolean isBlacklisted(String word){
		String GET_BLACKLISTED_TERM_SQL = "SELECT COUNT(word) FROM blacklisted WHERE word= ? ";
		int count = getSimpleJdbcTemplate().queryForInt(
				GET_BLACKLISTED_TERM_SQL, new MapSqlParameterSource().addValue("word", word));
		if(count > 0){
			return true;
		} else{
			return false;
		}
	}
	
	public void addBlacklistedWord(String word){
		String ADD_BLACKLISTED_TERM_SQL = "INSERT INTO blacklisted (id, word) values (0,:word)";
		int insertJob = getSimpleJdbcTemplate().update(ADD_BLACKLISTED_TERM_SQL, 
				new MapSqlParameterSource().addValue("uri", word)
		);
	}
	
	public static void main(String args[]){
		Blacklist bl = new Blacklist();
	}
}
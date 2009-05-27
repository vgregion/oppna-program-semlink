package com.m1.semanticapp.link;

import java.io.Serializable;

public class Link implements Serializable {

	/**
	 * A Links class for search
	 */
	
	private static final long serialVersionUID = 1L;
	private Float score;
	private String uri;
	private String title;
	private String desc;
	private String source;
	private String type;
	private String keyword;
	private int active;
	private int responseCode;
	private String responseMessage;
	
	public Float getScore(){
		return this.score;
	}

	public void setScore(Float score){
		this.score = score;
	}
	
	public String getUri(){
		return this.uri;
	}
	
	public void setUri(String uri){
		this.uri = uri;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getActive() {
		return active;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

}

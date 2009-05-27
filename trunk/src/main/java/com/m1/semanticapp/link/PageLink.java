package com.m1.semanticapp.link;

import java.io.Serializable;

public class PageLink implements Serializable {

	private static final long serialVersionUID = 91454661198951967L;
	private int i;
	private String uri;
	private String title;
	private String ref;
	private String source;
	private String type;
	private int active;
	private int responseCode;
	private String responseMessage;
	
	public void setI(int i) {
		this.i = i;
	}
	
	public int getI() {
		return i;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getRef() {
		return ref;
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

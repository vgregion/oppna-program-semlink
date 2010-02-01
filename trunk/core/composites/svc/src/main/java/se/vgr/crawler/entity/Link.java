package se.vgr.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity representing a HTTP link
 * @author Johan Säll Larsson
 */

@Entity
public class Link {

	private Integer id;
	private String url;
	private String title;
	private String description;
	private String keyword;
	private String relatedLink;
	private String semanticType;
	private Float score;
	private String source;
	private Boolean active;
	private Integer responseCode;
	private String responseMessage;
	private String language;
	
	public Link() {}

	/**
	 * <p>Sets the id</p>
	 * @param id
	 */
	
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * <p>Returns the id</p>
	 * @return id
	 */
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	/**
	 * <p>Sets the unique <code>String</code></p>
	 * @param url
	 * 		  <code>String</code> which contains related conterparts
	 */
	
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * <p>Returns the unique <code>String</code></p>
	 * @return <code>String</code>
	 */
	
	public String getUrl() {
		return url;
	}

	/**
	 * <p>Sets the page title</p>
	 * @param title
	 */
	
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * <p>Returns the page title</p>
	 * @return page title
	 */
	
	public String getTitle() {
		return title;
	}
	
	/**
	 * <p>Sets the page description</p>
	 * @param description
	 */
	
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * <p>Returns the page description</p>
	 * @return description
	 */
	
	public String getDescription() {
		return description;
	}

	/**
	 * <p>Sets the page keyword/subject</p>
	 * @param keyword
	 */
	
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * <p>Returns the page keyword/subject</p>
	 * @return keyword
	 */
	
	public String getKeyword() {
		return keyword;
	}

	/**
	 * <p>Sets the related link</p>
	 * @param relatedLink
	 * 		  The <code>String</code> for the related link
	 */
	
	public void setRelatedLink(String relatedLink) {
		this.relatedLink = relatedLink;
	}

	/**
	 * <p>Returns the related link <code>String</code></p>
	 * @return <code>String</code>
	 */
	public String getRelatedLink() {
		return relatedLink;
	}

	/**
	 * <p>Sets the semantic type for this specific <code>Link</code></p>
	 * @param semanticType
	 * 		  Eg. A concept, a Http document etc.
	 */
	
	public void setSemanticType(String semanticType) {
		this.semanticType = semanticType;
	}

	/**
	 * <p>Returns the semantic type</p>
	 * @return semantic type
	 */
	
	public String getSemanticType() {
		return semanticType;
	}

	/**
	 * <p>Sets the Lucene score for the link.</p>
	 * @param score
	 */
	
	public void setScore(Float score) {
		this.score = score;
	}

	/**
	 * <p>Returns the Lucene score</p>
	 * @return score
	 */
	
	public Float getScore() {
		return score;
	}

	/**
	 * <p>Sets the <code>String</code> of the web source</p>
	 * @param source
	 * 		  A source <code>String</code>, Eg. <code>http://www.example.com</code>
	 */
	
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * <p>Returns the <code>String</code> of the web source</p>
	 * @return <code>String</code>
	 */
	
	public String getSource() {
		return source;
	}

	/**
	 * <p>Sets whether the specified <code>Link</code> is active</p>
	 * @param active
	 * 		  <code>true</code> if active
	 */
	
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * <p>Returns is active</p>
	 * @return <code>true</code> if active
	 */
	
	public Boolean getActive() {
		return active;
	}

	/**
	 * <p>Sets the response code for the <code>Link</code></p>
	 * @param responseCode
	 * 		  Eg. 200, 300, 404.
	 */
	
	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * <p>Returns the response code</p>
	 * @return response code
	 */
	
	public Integer getResponseCode() {
		return responseCode;
	}

	/**
	 * <p>Sets the response message for the <code>Link</code></p>
	 * @param responseMessage
	 * 		  Eg. "Not Found", "Permanently Moved", etc.
	 */
	
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	/**
	 * <p>Returns the response message for the for the <code>Link</code></p>
	 * @return response message
	 */
	
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * <p>Sets the language</p>
	 * @param language
	 * 		  - Two-letter ISO 639-1 language code. @see http://www.infoterm.info/standardization/iso_639_1_2002.php
	 */
	
	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * <p>Returns the language</p>
	 * @return language
	 */
	
	public String getLanguage() {
		return language;
	}
	
}

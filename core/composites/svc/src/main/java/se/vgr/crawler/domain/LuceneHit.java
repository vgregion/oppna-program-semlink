package se.vgr.crawler.domain;

import java.util.List;

/**
 * <p>A Lucene hit from an executed search</p>
 * @author Johan Säll Larsson
 */

public class LuceneHit {

	private String url;
	private String title;
	private String description;
	private List<String> keyword;
	private List<String> semanticType;
	private Float score;
	private String source;
	private String audience;
	private List<String> path;
	private List<String> meshUID;
	private List<String> synonyms;
	private String language;
	
	public LuceneHit() {}
	
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
	
	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}

	/**
	 * <p>Returns the page keyword/subject</p>
	 * @return keyword
	 */
	
	public List<String> getKeyword() {
		return keyword;
	}

	/**
	 * <p>Sets the semantic type for this specific <code>Link</code></p>
	 * @param semanticType
	 * 		  Eg. A concept, a Http document etc.
	 */
	
	public void setSemanticType(List<String> semanticType) {
		this.semanticType = semanticType;
	}

	/**
	 * <p>Returns the semantic type</p>
	 * @return semantic type
	 */
	
	public List<String> getSemanticType() {
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
	
	/**
	 * <p>Sets the audience</p>
	 * @param audience
	 * 		  - Eg. medical, public, school
	 */
	
	public void setAudience(String audience) {
		this.audience = audience;
	}
	
	/**
	 * <p>Returns the audience</p>
	 * @return
	 */
	
	public String getAudience() {
		return audience;
	}
	
	/**
	 * <p>Sets the path</p>
	 * @param path
	 */
	
	public void setPath(List<String> path) {
		this.path = path;
	}
	
	/**
	 * <p>Returns the path</p>
	 * @return
	 */
	
	public List<String> getPath() {
		return path;
	}
	
	/**
	 * <p>Sets the MESH UID</p>
	 * @param meshUID
	 */
	
	public void setMeshUID(List<String> meshUID) {
		this.meshUID = meshUID;
	}
	
	/**
	 * <p>Returns the MESH UID</p>
	 * @return
	 */
	
	public List<String> getMeshUID() {
		return meshUID;
	}
	
	/**
	 * <p>Sets the synonyms</p>
	 * @param synonyms
	 */
	
	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}
	
	/**
	 * <p>Returns the synonyms</p>
	 * @return
	 */
	
	public List<String> getSynonyms() {
		return synonyms;
	}
	
}

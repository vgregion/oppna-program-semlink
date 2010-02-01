package se.vgr.crawler.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * <p>Entity representing a source which is actually a configuration for a crawl job.</p>
 * @author Johan Säll Larsson
 */

@Entity
public class Source {
	
	private Integer id;
	private String rootUrl;
	private String source;
	private Integer depth;
	private String whiteListPattern;
	private String blackListPattern;
	private String uniqueRepositoryName;
	private SourceType sourceType;
	private Boolean fullReCrawl;

	public Source(){}
	
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
     * <p>Sets the URL of the webpage were the crawling should begin</p>
     * 
     * @param rootUrl 
     * 		  - URL of the webpage were the crawling should begin
     */
	
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	/**
	 * <p>Returns the URL of the webpage were the crawling should begin</p>
	 * 
	 * @return startURL 
	 */
	
	public String getRootUrl() {
		return rootUrl;
	}
	
	/**
	 * <p>Sets the source for the crawl. Eg. www.example.com or example.com</p>
	 * 
	 * @param source
	 */
	
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * <p>Returns the source</p>
	 * 
	 * @return siteName
	 */
	
	public String getSource() {
		return source;
	}


    /**
     * <p>Sets how many levels of link references the crawler should cover.</p>
     * 
     * @param depth
     * 		  - <code>0</code> will not follow any links
     */
	
	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	/**
	 * <p>Returns how many levels of link references the crawler should cover.</p>
	 * 
	 * @return depth
	 */
	
	public Integer getDepth() {
		return depth;
	}
	
	
	/** 
	 * <p>Sets a unigue name for the RDF repository.</p>
	 * 
	 * <p>The full directory path will be: 
	 * <pre>systemPath/repositories/<strong>uniqueName</strong></pre></p>
	 * 
	 * @param uniqueRepositoryName
	 */
	
	public void setUniqueRepositoryName(String uniqueRepositoryName) {
		this.uniqueRepositoryName = uniqueRepositoryName;
	}

	/**
	 * <p>Returns the unique name</p>
	 * 
	 * @return uniqueRepositoryName
	 */
	
	public String getUniqueRepositoryName() {
		return uniqueRepositoryName;
	}

	/**
	 * <p>Sets the whitelist pattern</p>
	 * <p>Each <code>CrawlConfig</code> maintains lists of include and exclude patterns. 
	 * A URL is matched against these two pattern lists to determine whether 
	 * it is inside or outside the domain. A URL is inside the domain when 
	 * it matches at least one of the include patterns but none of the exclude 
	 * patterns.</p> 
	 * <p>In case no include patterns are specified, all URLs that 
	 * don't match any of the exclude patterns are included</p>
	 * 
	 * @param whiteListPattern
	 */
	
	public void setWhiteListPattern(String whiteListPattern) {
		this.whiteListPattern = whiteListPattern;
	}

	/**
	 * <p>Returns the whitelist pattern</p>
	 * 
	 * @return whiteListPattern
	 */
	
	public String getWhiteListPattern() {
		return whiteListPattern;
	}

	/**
	 * <p>Sets the blacklist pattern</p>
	 * <p>Each <code>CrawlConfig</code> maintains lists of include and exclude patterns. 
	 * A URL is matched against these two pattern lists to determine whether 
	 * it is inside or outside the domain. A URL is inside the domain when 
	 * it matches at least one of the include patterns but none of the exclude 
	 * patterns.</p> 
	 * <p>In case no include patterns are specified, all URLs that 
	 * don't match any of the exclude patterns are included</p>
	 * 
	 * @param blackListPattern
	 */
	
	public void setBlackListPattern(String blackListPattern) {
		this.blackListPattern = blackListPattern;
	}

	/**
	 * <p>Returns the blacklist pattern</p>
	 * 
	 * @return blackListPattern
	 */
	
	public String getBlackListPattern() {
		return blackListPattern;
	}

	/**
	 * <p>Sets the source type</p>
	 * 
	 * @param sourceType
	 * 		  - <code>SourceType.WEB</code>, <code>SourceType.FILE</code>
	 */
	
	public void setSourceType(SourceType sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * <p>Returns the source type</p>
	 * @return sourceType
	 */
	
	public SourceType getSourceType() {
		return sourceType;
	}

	/**
	 * <p>Sets if the crawl should delete it´s index and repository
	 * and re-crawl the source</p>
	 * 
	 * @param fullReCrawl
	 */
	
	public void setFullReCrawl(Boolean fullReCrawl) {
		this.fullReCrawl = fullReCrawl;
	}

	/**
	 * <p>Return whether the crawl should delete it´s index and repository
	 * and re-crawl the source</p> 
	 * 
	 * @return fullReCrawl
	 */
	
	public Boolean getFullReCrawl() {
		return fullReCrawl;
	}
	
}

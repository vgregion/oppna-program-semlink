package se.vgr.crawler.index;

import static se.vgr.crawler.index.IndexConstants.PATH_FIELD;
import static se.vgr.crawler.index.IndexConstants.SUMMARY_FIELD;
import static se.vgr.crawler.index.IndexConstants.TEXT_FIELD;
import static se.vgr.crawler.index.IndexConstants.TITLE_FIELD;
import static se.vgr.crawler.index.IndexConstants.MESH;
import static se.vgr.crawler.index.IndexConstants.SYNONYMS;
import static se.vgr.crawler.index.IndexConstants.LANGUAGE;
import static se.vgr.crawler.index.IndexConstants.KEYWORD;
import static se.vgr.crawler.index.IndexConstants.SOURCE;
import static se.vgr.crawler.index.IndexConstants.AUDIENCE;
import static se.vgr.crawler.index.IndexConstants.UID_FIELD;

import info.aduna.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.misc.ChainedFilter;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.BooleanQuery;

import org.apache.lucene.search.FieldCacheTermsFilter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiSearcher;

import org.apache.lucene.search.Filter;
import org.apache.lucene.search.ParallelMultiSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searchable;
import org.apache.lucene.search.TopDocs;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import se.vgr.crawler.domain.LuceneHit;

/**
 * Class representing a Lucene index which handles read/write synchronization.
 */

public class LuceneIndex {

	/** 
	 * @see http://wiki.apache.org/jakarta-lucene/LuceneFAQ#Why_am_I_getting_a_TooManyClauses_exception.3F 
	 */
	
	static {	
		BooleanQuery.setMaxClauseCount(1048576);
	}

	private static Logger log = Logger.getLogger(LuceneIndex.class);

	private static final String INDEX_DIR = "index";
	
	public static final String[] SEARCH_FIELDS = new String[] { 
		TEXT_FIELD, TITLE_FIELD, PATH_FIELD, SUMMARY_FIELD, KEYWORD, MESH, SYNONYMS, LANGUAGE, SOURCE 
	};

	private File defaultDir;
	
	private File indexDirectory;

	private Directory directory;

	private Analyzer analyzer;

	private MultiSearcher searcher;

	private IndexWriter indexWriter;
	
	private Searchable[] searchable;

	private MultiFieldQueryParser queryParser;
	
	private Map<String, Float> fieldBoosts;


	/**
	 * <p>Creates and initializes a Lucene index</p>
	 * <p>Index directory and analyzer needs to be set</p>
	 * 
	 * @param defaultDir
	 */
	
	public LuceneIndex(File defaultDir) {
		this.defaultDir = new File(defaultDir, INDEX_DIR);
	}
	
	/**
	 * <p>Searches through all index</p>
	 * 
	 * @param searchString
	 * @param totalHits
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 * 
	 * @throws IOException
	 * @throws ParseException
	 */
	
	public synchronized List<LuceneHit> search(String queryString, String languageCode, String source, Integer totalHits) throws IOException, ParseException {
		
		Searchable[] searchable = getAllIndexes();
		
		if ( searchable != null ) {

			queryParser = new MultiFieldQueryParser(Version.LUCENE_CURRENT, SEARCH_FIELDS, analyzer);
			searcher = new ParallelMultiSearcher(searchable);
			
			Query query = queryParser.parse( queryString );
			
			TopDocs hits = null;
			
			// Add filters
			
			List<Filter> filters = new ArrayList<Filter>();
			
			if ( languageCode != null ) {
				FieldCacheTermsFilter filter = new FieldCacheTermsFilter(LANGUAGE, new String[]{ languageCode } );
				filters.add(filter);
			} 
			
			if ( source != null ) {
				FieldCacheTermsFilter filter = new FieldCacheTermsFilter(SOURCE, new String[]{ source } );
				filters.add(filter);
			}
			
			// search
			
			if ( filters.size() > 0 ) {
				
				Filter[] chain = filters.toArray(new Filter[filters.size()]);
				ChainedFilter chainedFilter = new ChainedFilter(chain, ChainedFilter.AND);
				hits = searcher.search(query, chainedFilter, totalHits);
				
			} else {
				
				hits = searcher.search(query, totalHits);
			
			}
			
			return extractResult(hits, searcher);
			
		} 
		
		return null;
		
	}
	
	public synchronized List<LuceneHit> searchUrl(String url, String languageCode, String source, Integer maxHits) throws IOException, ParseException {
		
		Searchable[] searchable = getAllIndexes();
		LuceneHit found = null;
		
		log.debug("query:"+url);
		
		if ( searchable != null ) {

			log.debug("searchable not null");
			log.debug("searchable length: "+searchable.length);
			queryParser = new MultiFieldQueryParser(Version.LUCENE_CURRENT, new String[] { UID_FIELD }, new StandardAnalyzer(Version.LUCENE_CURRENT));
			searcher = new ParallelMultiSearcher(searchable);
			Query query = queryParser.parse( url );		
			
			//FieldCacheTermsFilter filter = new FieldCacheTermsFilter(IndexConstants.UID_FIELD, new String[]{ url } );
			TopDocs hits = searcher.search(query, 10);			
			List<LuceneHit> extracted = extractResult(hits, searcher);
			
			log.debug("First search made a total hit count of "+extracted.size()+" hits");
			if ( extracted != null && extracted.size() > 0 ) {
				found = extracted.get(0);
			}
			
		} 
		
		List<LuceneHit> retval = new ArrayList<LuceneHit>();
		
		String rewrittenUrl = url.replace("\"", "");
		
		if ( found != null ) {
			
			log.debug("Rewritten URL: "+rewrittenUrl);
			log.debug("Found URL: "+found.getUrl());
			
			HashMap<String, Object> exist = new HashMap<String, Object>();
			List<String> nodes = found.getMeshUID();
			
			if ( nodes != null ) {
				
				for ( String node : nodes ) {
					
					int last = node.lastIndexOf(".");
					if ( last != -1 ) {
						node = node.substring(0, last);
						node = node+"*";
						System.out.println("my rewritten node: "+node);
					}
					
					List<LuceneHit> hits = search(node, languageCode, source, maxHits);
					
					if ( hits != null ) {
						
						for ( LuceneHit hit : hits ) {
							
							if ( !hit.getUrl().equals(rewrittenUrl) ) {
								
								if ( !exist.containsKey(hit.getUrl()) ) {
									log.debug("Adding result, found: "+hits.size()+" hits");
									retval.add(hit);
									exist.put(hit.getUrl(), hit);
								}
								
							}
							
						}
						
					}
	
				}
				
			}
			
		}
		
		return retval;
		
	}
	
	/**
	 * <p>Extracts the search results</p>
	 * 
	 * @param topDocs
	 * @param searcher
	 * 
	 * @return <code>List</code> of <code>LuceneHit</code> objects
	 * 
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	
	public synchronized List<LuceneHit> extractResult(TopDocs topDocs, Searchable searcher ) throws CorruptIndexException, IOException {
		
		List<LuceneHit> retval = new ArrayList<LuceneHit>();
		
		if ( topDocs != null ) {
			
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			
			for ( int i = 0; i < scoreDocs.length; i++ ) {
				
				Document document = searcher.doc(scoreDocs[i].doc);
				LuceneHit hit = new LuceneHit();
				
				hit.setUrl(document.get(IndexConstants.UID_FIELD));
				hit.setScore(scoreDocs[i].score);
				hit.setTitle(document.get(TITLE_FIELD));
				hit.setLanguage(document.get(LANGUAGE));
				hit.setDescription(document.get(SUMMARY_FIELD));
				hit.setSource(document.get(SOURCE));
				hit.setAudience(document.get(AUDIENCE));
				
				List<Field> keywords = Arrays.asList(document.getFields(KEYWORD));
				List<Field> paths = Arrays.asList(document.getFields(PATH_FIELD));
				List<Field> synonyms = Arrays.asList(document.getFields(SYNONYMS));
				List<Field> nodes = Arrays.asList(document.getFields(MESH));
				
				List<String> kw = new ArrayList<String>();
				List<String> ps = new ArrayList<String>();
				List<String> syn = new ArrayList<String>();
				List<String> n = new ArrayList<String>();
				
				for ( Field keyword : keywords ) {
					kw.add(keyword.stringValue());
				}
				
				for ( Field path : paths ) {
					ps.add(path.stringValue());
				}
				
				for ( Field synonym : synonyms ) {
					syn.add(synonym.stringValue());
				}
				
				for ( Field node : nodes ) {
					n.add(node.stringValue());
				}
				
				hit.setKeyword(kw);
				hit.setPath(ps);
				hit.setSynonyms(syn);
				hit.setMeshUID(n);
				
				retval.add(hit);
				
			}
			
		}

		return retval;
		
	}

	/**
	 * <p>Returns all indexes from a directory</p>
	 * @return <code>Array</code> of <code>Searchable</code>
	 */
	
	public Searchable[] getAllIndexes() {
		
		if ( searchable == null ) {
			
			List<Searchable> searchers = new ArrayList<Searchable>();
			List<String> directories = Arrays.asList(defaultDir.list());
			List<File> indexes = new ArrayList<File>();
			
			if ( directories != null ) {
				
				for ( String directory : directories ) {
					File index = new File(defaultDir, directory);
					indexes.add(index);
				}
				
			}
			
			for ( File path : indexes ) {
				
				try {
					Directory directory = FSDirectory.open(path);
					Searchable searcher = new IndexSearcher(directory, true);
					searchers.add(searcher);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			if ( searchers != null && searchers.size() > 0 ) {
				searchable = searchers.toArray(new Searchable[searchers.size()]);
			}
		
		}
		
		return searchable;
		
	}
	
	/**
	 * <p>Adds a <code>Document</code> to the index</p>
	 * @param document
	 * @throws IOException
	 */
	
	public synchronized void addDocument(Document document) throws IOException {
		closeIndexSearcher();
		openIndexWriter();
		indexWriter.addDocument(document);
	}

	/**
	 * <p>Deletes a <code>Term</code> from the index<p>
	 * @param term
	 * @throws IOException
	 */
	
	public synchronized void delete(Term term) throws IOException {
		closeIndexSearcher();
		openIndexWriter();
		indexWriter.deleteDocuments(term);
	}

	/**
	 * <p>Optimizes the Lucene index.</p>
	 * @throws IOException
	 */
	
	public synchronized void optimize() throws IOException {
		closeIndexSearcher();
		openIndexWriter();
		indexWriter.optimize();
		closeIndexWriter();
	}

	/**
	 * <p>Close and remove existing index</p>
	 * @throws IOException
	 */
	
	public synchronized void clear() throws IOException {
		closeAll();
		createIndex();
	}

	public synchronized void close() throws IOException {
		
		closeAll();

		if ( directory != null ) {
			directory.close();
			directory = null;
		}
		
	}

	/**
	 * <p>Creates an index in the index directory, removing any existing data
	 * due to stale/corrupt files</p>
	 */
	
	private void createIndex() throws IOException {
		
		if ( !FileUtil.deltree(indexDirectory) ) {
			log.error("Index directory cannot be cleared, even though there is no index left there!");
		}

		indexDirectory.mkdirs();
		directory = FSDirectory.open(indexDirectory);
		openIndexWriter(true);
		closeIndexWriter();
		
	}

	private void closeIndexSearcher() throws IOException {
		
		if ( searcher != null ) {
			searcher.close();
			searcher = null;
		}
		
	}

	private void openIndexWriter() throws IOException {
		openIndexWriter(false);
	}

	private void openIndexWriter(boolean create) throws IOException {
		
		if ( indexWriter == null ) {
			indexWriter = new IndexWriter(directory, new StandardAnalyzer(Version.LUCENE_CURRENT), create, IndexWriter.MaxFieldLength.UNLIMITED);
			indexWriter.setUseCompoundFile(true);
			indexWriter.setMaxFieldLength(Integer.MAX_VALUE);
		}
		
	}

	private void closeIndexWriter() throws IOException {
		if (indexWriter != null) {
			indexWriter.close();
			indexWriter = null;
		}
	}

	private void closeAll() throws IOException {
		closeIndexSearcher();
		closeIndexWriter();
	}

	public void setIndexDirectory(String directory) throws IOException {
		
		log.debug(MessageFormat.format("Index directory {0}", directory));
		
		this.indexDirectory = new File(defaultDir, directory);
		
		if ( this.indexDirectory.exists() ) {
			
			try {
				
				this.directory = FSDirectory.open(this.indexDirectory);	
				
			} catch ( IOException e ) {
				createIndex();
			}
			
		} else {
			createIndex();
		}
		
		/* Unlock any locks that may have been left by previous crashed processes */
		
		if ( IndexWriter.isLocked(this.directory) ) {
			log.error(MessageFormat.format("Directory ({0}) had to be unlocked", this.indexDirectory));
			IndexWriter.unlock(this.directory);
		}
		
	}
	

	public void setFieldBoosts(Map<String, Float> fieldBoosts) {
		this.fieldBoosts = fieldBoosts;
	}
	
	public Map<String, Float> getFieldBoosts() {
		return this.fieldBoosts;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}
	
}
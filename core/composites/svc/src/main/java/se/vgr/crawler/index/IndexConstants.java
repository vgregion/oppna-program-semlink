package se.vgr.crawler.index;

import org.semanticdesktop.aperture.vocabulary.NCAL;
import org.semanticdesktop.aperture.vocabulary.NCO;
import org.semanticdesktop.aperture.vocabulary.NEXIF;
import org.semanticdesktop.aperture.vocabulary.NID3;
import org.semanticdesktop.aperture.vocabulary.NIE;
import org.semanticdesktop.aperture.vocabulary.NMO;

import java.util.HashMap;
import java.util.Map;

import se.vgr.crawler.vocabulary.DC;
import se.vgr.crawler.vocabulary.SKOS;
import se.vgr.crawler.vocabulary.VGR;

/**
 * <p>Maps RDF predicates of properties connected to the crawled Resource to Lucene field names</p>
 * @author Johan Säll Larsson
 */

public class IndexConstants {
	
	public static final Map<String, String> INDEXED_RESOURCE_PREDICATES;

	public static final String UID_FIELD = "id";

	public static final String TEXT_FIELD = "text";

	public static final String TITLE_FIELD = "title";

	public static final String PATH_FIELD = "path";

	public static final String SUMMARY_FIELD = "summary";

	public static final String KEYWORD = "keyword";
	
	public static final String MESH = "mesh";
	
	public static final String SYNONYMS = "synonyms";
	
	public static final String LANGUAGE = "language";
	
	public static final String SOURCE = "source";
	
	public static final String AUDIENCE = "audience";

	static {
		
		INDEXED_RESOURCE_PREDICATES = new HashMap<String, String>();

		INDEXED_RESOURCE_PREDICATES.put(NIE.plainTextContent.toString(), TEXT_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NMO.plainTextMessageContent.toString(), TEXT_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NID3.unsynchronizedTextContent.toString(), TEXT_FIELD);

		INDEXED_RESOURCE_PREDICATES.put(DC.title.toString(), TITLE_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NIE.subject.toString(), TITLE_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NMO.messageSubject.toString(), TITLE_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NEXIF.imageDescription.toString(), TITLE_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NID3.title.toString(), TITLE_FIELD);
		
		INDEXED_RESOURCE_PREDICATES.put(NIE.comment.toString(), SUMMARY_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(DC.description.toString(), SUMMARY_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NCAL.description.toString(), SUMMARY_FIELD);
		INDEXED_RESOURCE_PREDICATES.put(NCO.note.toString(), SUMMARY_FIELD);
		
		INDEXED_RESOURCE_PREDICATES.put(SKOS.prefLabel.toString(), KEYWORD);
		INDEXED_RESOURCE_PREDICATES.put(DC.subject.toString(), KEYWORD);
		
		INDEXED_RESOURCE_PREDICATES.put(VGR.mesh.toString(), MESH);
		
		INDEXED_RESOURCE_PREDICATES.put(SKOS.altLabel.toString(), SYNONYMS);
		
		INDEXED_RESOURCE_PREDICATES.put(DC.language.toString(), LANGUAGE);
		INDEXED_RESOURCE_PREDICATES.put(NIE.language.toString(), LANGUAGE);
		
	}
	
}
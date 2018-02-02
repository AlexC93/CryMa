package ro.wade.cryma.internalDB.constants;

/**
 * Stores URIs of RDF properties
 */
public interface Properties {

	public static String WIKIDATA_NAMESPACE = "http://www.wikidata.org/prop/direct/";
	public static String WIKIBASE_NAMESPACE = "http://wikiba.se/ontology#";
	public static String CRYMA_NAMESPACE = "http://www.semanticweb.org/alex/ontologies/2018/0/cryma#";
	public static String LABEL = WIKIBASE_NAMESPACE + "label";
	public static String INSTANCE_OF = WIKIDATA_NAMESPACE + "P31";
	public static String REPOSITORY = WIKIDATA_NAMESPACE + "P1324";
	public static String LOGO_IMAGE = WIKIDATA_NAMESPACE + "P154";
	public static String SHORT_NAME = WIKIDATA_NAMESPACE + "P1813";
	public static String UNICODE_CHARACTER = WIKIDATA_NAMESPACE + "P487";
	public static String OFFICIAL_WEBSITE = WIKIDATA_NAMESPACE + "P856";
	public static String SUBREDDIT = WIKIDATA_NAMESPACE + "P3984";
	public static String QUORA_TOPIC_ID = WIKIDATA_NAMESPACE + "P3417";
	public static String CRYMA_HAD_VALUE = CRYMA_NAMESPACE + "hadValue";
	public static String CRYMA_HAS_COMMENT = CRYMA_NAMESPACE + "hasComment";
	public static String CRYMA_AT_DATE = CRYMA_NAMESPACE + "atDate";
	public static String CRYMA_VALUE = CRYMA_NAMESPACE + "value";
	public static String CRYMA_COMMENT = CRYMA_NAMESPACE + "comment";

}

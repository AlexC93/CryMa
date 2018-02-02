package ro.wade.cryma.internalDB.constants;

public interface Constants {

	public static String WIKIDATA_SPARQL_ENDPOINT = "https://query.wikidata.org/bigdata/namespace/wdq/sparql";
	public static String DBPEDIA_SPARQL_ENDPOINT = "http://dbpedia.org/sparql";
	public static String BD_PREFIX = "PREFIX bd: <http://www.bigdata.com/rdf#>";
	public static String WD_PREFIX = "PREFIX wd: <http://www.wikidata.org/entity/>";
	public static String WDT_PREFIX = "PREFIX wdt: <http://www.wikidata.org/prop/direct/>";
	public static String WIKIBASE_PREFIX = "PREFIX wikibase: <http://wikiba.se/ontology#>";
	public static String CRYMA_PREFIX = "PREFIX cryma: <http://www.semanticweb.org/alex/ontologies/2018/0/cryma#>";
	public static String QUERY_LIMIT = "LIMIT 1000";
	public static String CRYPOCURRENCY_WIKIDATA_INSTANCE = "http://www.wikidata.org/prop/direct/Q13479982";
}

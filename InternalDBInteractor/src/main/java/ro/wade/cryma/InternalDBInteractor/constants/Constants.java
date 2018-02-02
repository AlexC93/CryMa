package ro.wade.cryma.InternalDBInteractor.constants;

public interface Constants {

	public static String BD_PREFIX = "PREFIX bd: <http://www.bigdata.com/rdf#>";
	public static String WD_PREFIX = "PREFIX wd: <http://www.wikidata.org/entity/>";
	public static String WDT_PREFIX = "PREFIX wdt: <http://www.wikidata.org/prop/direct/>";
	public static String WIKIBASE_PREFIX = "PREFIX wikibase: <http://wikiba.se/ontology#>";
	public static String CRYMA_PREFIX = "PREFIX cryma: <http://www.semanticweb.org/alex/ontologies/2018/0/cryma#>";
	public static String QUERY_LIMIT = "LIMIT 100000";
	public static String CRYPOCURRENCY_WIKIDATA_INSTANCE = "https://www.wikidata.org/wiki/Q13479982";
	public static String INTERNALDB_SPARQL_ENDPOINT = "http://localhost:3030/crypto";
	
}

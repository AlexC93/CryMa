package ro.wade.cryma.internalDB.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;
import org.apache.log4j.Logger;

import ro.wade.cryma.internalDB.beans.Cryptocurrency;
import ro.wade.cryma.internalDB.constants.Constants;

/**
 * This class is used to retrieve cryptocurrency-related information from
 * certain SPARQL endpoints.
 * 
 * @author Alex
 *
 */
public class CryptoRetriever {

	private static Logger logger = Logger.getLogger(CryptoRetriever.class);

	public static List<Cryptocurrency> getCryptocurrencies() {
		String queryString = buildWikidataQuery();
		logger.info("Wikidata query built:\n\n" + queryString + "\n");
		List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
		List<String> uriHistory = new ArrayList<>();
		Query query = QueryFactory.create(queryString);

		try (QueryExecution qexec = QueryExecutionFactory.sparqlService(Constants.WIKIDATA_SPARQL_ENDPOINT, query)) {
			((QueryEngineHTTP) qexec).addParam("timeout", "10000");
			ResultSet rs = qexec.execSelect();
			while (rs.hasNext()) {
				QuerySolution solution = rs.nextSolution();
				Resource item = solution.getResource("item");
				if (!isDuplicate(uriHistory, item.getURI())) {
					Cryptocurrency currency = buildCryptocurrency(solution, item.getURI());
					cryptocurrencies.add(currency);
				}
			}
			ResultSetFormatter.out(System.out, rs);
			return cryptocurrencies;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static String buildWikidataQuery() {
		StringBuilder query = new StringBuilder(buildWikidataQueryPrefix());
		query.append("SELECT ?item ?itemLabel ?logo ?sname ?symbol ?website ?repo ?subreddit ?quora WHERE {")
				.append("\n");
		query.append("  ?item wdt:P31 wd:Q13479982.").append("\n"); // ?item instance-of cryptocurrency
		query.append("  OPTIONAL { ?item wdt:P1324 ?repo }.").append("\n"); // ?item source-code-repository ?repo
		query.append("  OPTIONAL { ?item wdt:P154 ?logo }.").append("\n"); // ?item logo-image ?logo
		query.append("  OPTIONAL { ?item wdt:P1813 ?sname }.").append("\n"); // ?item short-name ?sname
		query.append("  OPTIONAL { ?item wdt:P487 ?symbol }.").append("\n"); // ?item Unicode-character ?symbol
		query.append("  OPTIONAL { ?item wdt:P856 ?website }.").append("\n"); // ?item official-website ?website
		query.append("  OPTIONAL { ?item wdt:P3984 ?subreddit}.").append("\n"); // ?item subreddit ?subreddit
		query.append("  OPTIONAL { ?item wdt:P3417 ?quora}.").append("\n"); // ?item Quora-topic-ID ?quora
		query.append("SERVICE wikibase:label { bd:serviceParam ");
		query.append("wikibase:language \"[AUTO_LANGUAGE],en\". }").append("\n"); // finally getting the label
		query.append("}").append("\n");
		query.append(Constants.QUERY_LIMIT);
		return query.toString();
	}

	private static String buildWikidataQueryPrefix() {
		StringBuilder prefixes = new StringBuilder();
		prefixes.append(Constants.BD_PREFIX).append("\n");
		prefixes.append(Constants.WD_PREFIX).append("\n");
		prefixes.append(Constants.WDT_PREFIX).append("\n");
		prefixes.append(Constants.WIKIBASE_PREFIX).append("\n");
		return prefixes.toString();
	}

	private static boolean isDuplicate(List<String> uriHistory, String uri) {
		if (!uriHistory.contains(uri)) {
			uriHistory.add(uri);
			return false;
		}
		return true;
	}

	private static Cryptocurrency buildCryptocurrency(QuerySolution solution, String cryptoURI) {
		Cryptocurrency currency = new Cryptocurrency();
		currency.setURI(cryptoURI);
		Literal label = solution.getLiteral("itemLabel");
		if (label != null) {
			currency.setLabel(label.getString());
		}
		Resource logo = solution.getResource("logo");
		if (logo != null) {
			currency.setLogo(logo.getURI());
		}
		Literal sname = solution.getLiteral("sname");
		if (sname != null) {
			currency.setShortName(sname.getString());
		}
		Literal symbol = solution.getLiteral("symbol");
		if (symbol != null) {
			currency.setSymbol(symbol.getString());
		}
		Resource website = solution.getResource("website");
		if (website != null) {
			currency.setOfficialWebsite(website.getURI());
		}
		Resource repo = solution.getResource("repo");
		if (repo != null) {
			currency.setSourceCodeRepository(repo.getURI());
		}
		Literal quora = solution.getLiteral("quora");
		String id = quora != null ? quora.getString() : "";
		if (id != null && id.length() > 0) {
			currency.setQuora("www.quora.com/topic/" + id);
		}
		Literal subreddit = solution.getLiteral("subreddit");
		id = subreddit != null ? subreddit.getString() : "";
		if (id != null && id.length() > 0) {
			currency.setSubreddit("www.reddit.com/r/" + id);
		}

		return currency;
	}

}

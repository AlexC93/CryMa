package ro.wade.cryma.InternalDBInteractor;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import ro.wade.cryma.InternalDBInteractor.comment.CommentRetriever;
import ro.wade.cryma.InternalDBInteractor.constants.Constants;

/**
 * This class is used to retrieve cryptocurrency-related information from
 * certain SPARQL endpoints.
 * 
 * @author Alex
 *
 */
public class CryptoRetriever {

	private static Logger logger = Logger.getLogger(CryptoRetriever.class);

	public static List<Cryptocurrency> getCryptocurrencies(String internalDBEndpoint) {
		String queryString = buildCryptocurrenciesQuery();
		logger.info("Query built:\n\n" + queryString + "\n");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		List<Cryptocurrency> cryptocurrencies = new ArrayList<>();
		List<String> uriHistory = new ArrayList<>();
		
		Query query = QueryFactory.create(queryString);
		try (QueryExecution qexec = QueryExecutionFactory.sparqlService(internalDBEndpoint, query)) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Adding historical values to the cryptocurrencies
		for(Cryptocurrency currency: cryptocurrencies) {
			query = QueryFactory.create(buildHistoricalValuesQuery(currency.getLabel()));
			try (QueryExecution qexec = QueryExecutionFactory.sparqlService(internalDBEndpoint, query)) {
				((QueryEngineHTTP) qexec).addParam("timeout", "10000");
				ResultSet rs = qexec.execSelect();
				while (rs.hasNext()) {
					QuerySolution solution = rs.nextSolution();
					Literal date = solution.getLiteral("date");
					Literal value = solution.getLiteral("value");
					if(date != null && value != null) {
//						System.out.println("got date = " + date.getString());
//						System.out.println("got value = " + value.getDouble());
						currency.getHistoricalValues().put(sdf.parse(date.getString()), value.getDouble());
					}
//					try {
//						CommentRetriever retriever = new CommentRetriever();
//						Map<Date, String> comments = retriever.getComments(currency.getLabel());
//						currency.setComments(comments);
//					} catch (ClassNotFoundException | SQLException e) {
//						e.printStackTrace();
//					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cryptocurrencies;
	}
	
	public static Cryptocurrency getCryptocurrency(String internalDBEndpoint, String cryptocurrencyName) {
		Cryptocurrency currency = new Cryptocurrency();
		String queryString = buildCryptocurrencyQuery(cryptocurrencyName);
		logger.info("Query built:\n\n" + queryString + "\n");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		Query query = QueryFactory.create(queryString);
		try (QueryExecution qexec = QueryExecutionFactory.sparqlService(internalDBEndpoint, query)) {
			((QueryEngineHTTP) qexec).addParam("timeout", "10000");
			ResultSet rs = qexec.execSelect();
			while (rs.hasNext()) {
				QuerySolution solution = rs.nextSolution();
				Resource item = solution.getResource("item");
				currency = buildCryptocurrency(solution, item.getURI());
				currency.setLabel(cryptocurrencyName);
				// setting comments
				try {
					CommentRetriever retriever = new CommentRetriever();
					Map<Date, String> comments = retriever.getComments(currency.getLabel());
					currency.setComments(comments);
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		query = QueryFactory.create(buildHistoricalValuesQuery(currency.getLabel()));
		try (QueryExecution qexec = QueryExecutionFactory.sparqlService(internalDBEndpoint, query)) {
			((QueryEngineHTTP) qexec).addParam("timeout", "10000");
			ResultSet rs = qexec.execSelect();
			while (rs.hasNext()) {
				QuerySolution solution = rs.nextSolution();
				Literal date = solution.getLiteral("date");
				Literal value = solution.getLiteral("value");
				if(date != null && value != null) {
					currency.getHistoricalValues().put(sdf.parse(date.getString()), value.getDouble());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currency;
	}
	
	public static List<String> getCryptocurrencyNames(String internalDBEndpoint) {
		List<String> names = new ArrayList<>();
		String queryString = buildCryptocurrencyNamesQuery();
		Query query = QueryFactory.create(queryString);
		try (QueryExecution qexec = QueryExecutionFactory.sparqlService(internalDBEndpoint, query)) {
			((QueryEngineHTTP) qexec).addParam("timeout", "10000");
			ResultSet rs = qexec.execSelect();
			while (rs.hasNext()) {
				QuerySolution solution = rs.nextSolution();
				Literal lit = solution.getLiteral("label");
				if(lit != null) {
					names.add(lit.getString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return names;
	}

	private static String buildCryptocurrenciesQuery() {
		StringBuilder query = new StringBuilder(buildQueryPrefix());
		query.append("SELECT ?item ?itemLabel ?logo ?sname ?symbol ?website ?repo ?subreddit ?quora WHERE {")
				.append("\n");
		query.append("  ?item wikibase:label ?itemLabel").append("\n");
		query.append("  OPTIONAL { ?item wdt:P1324 ?repo }.").append("\n"); // ?item source-code-repository ?repo
		query.append("  OPTIONAL { ?item wdt:P154 ?logo }.").append("\n"); // ?item logo-image ?logo
		query.append("  OPTIONAL { ?item wdt:P1813 ?sname }.").append("\n"); // ?item short-name ?sname
		query.append("  OPTIONAL { ?item wdt:P487 ?symbol }.").append("\n"); // ?item Unicode-character ?symbol
		query.append("  OPTIONAL { ?item wdt:P856 ?website }.").append("\n"); // ?item official-website ?website
		query.append("  OPTIONAL { ?item wdt:P3984 ?subreddit}.").append("\n"); // ?item subreddit ?subreddit
		query.append("  OPTIONAL { ?item wdt:P3417 ?quora}.").append("\n"); // ?item Quora-topic-ID ?quora
		query.append("}").append("\n");
		query.append(Constants.QUERY_LIMIT);
		return query.toString();
	}
	
	private static String buildCryptocurrencyQuery(String cryptocurrency) {
		StringBuilder query = new StringBuilder(buildQueryPrefix());
		query.append("SELECT ?item ?itemLabel ?logo ?sname ?symbol ?website ?repo ?subreddit ?quora WHERE {")
				.append("\n");
		query.append("  ?item wikibase:label '").append(cryptocurrency).append("'.\n");
		query.append("  OPTIONAL { ?item wdt:P1324 ?repo }.").append("\n"); // ?item source-code-repository ?repo
		query.append("  OPTIONAL { ?item wdt:P154 ?logo }.").append("\n"); // ?item logo-image ?logo
		query.append("  OPTIONAL { ?item wdt:P1813 ?sname }.").append("\n"); // ?item short-name ?sname
		query.append("  OPTIONAL { ?item wdt:P487 ?symbol }.").append("\n"); // ?item Unicode-character ?symbol
		query.append("  OPTIONAL { ?item wdt:P856 ?website }.").append("\n"); // ?item official-website ?website
		query.append("  OPTIONAL { ?item wdt:P3984 ?subreddit}.").append("\n"); // ?item subreddit ?subreddit
		query.append("  OPTIONAL { ?item wdt:P3417 ?quora}.").append("\n"); // ?item Quora-topic-ID ?quora
		query.append("}").append("\n");
		query.append(Constants.QUERY_LIMIT);
		return query.toString();
	}
	
	private static String buildHistoricalValuesQuery(String cryptocurrency) {
		StringBuilder query = new StringBuilder(buildQueryPrefix());
		query.append("SELECT ?date ?value WHERE {").append("\n");
		query.append("  ?item wikibase:label '").append(cryptocurrency).append("'.\n");
		query.append("  ?item cryma:hadValue [cryma:atDate ?date; cryma:value ?value].").append("\n");
		query.append("}").append("\n");
		return query.toString();
	}
	
	private static String buildCryptocurrencyNamesQuery() {
		StringBuilder query = new StringBuilder(buildQueryPrefix());
		query.append("SELECT ?label WHERE {").append("\n");
		query.append("  ?item wikibase:label ?label").append(".\n");
		query.append("}").append("\n");
		return query.toString();
	}

	private static String buildQueryPrefix() {
		StringBuilder prefixes = new StringBuilder();
		prefixes.append(Constants.WDT_PREFIX).append("\n");
		prefixes.append(Constants.WIKIBASE_PREFIX).append("\n");
		prefixes.append(Constants.CRYMA_PREFIX).append("\n");
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
		Literal logo = solution.getLiteral("logo");
		if (logo != null) {
			currency.setLogo(logo.getString());
		}
		Literal sname = solution.getLiteral("sname");
		if (sname != null) {
			currency.setShortName(sname.getString());
		}
		Literal symbol = solution.getLiteral("symbol");
		if (symbol != null) {
			currency.setSymbol(symbol.getString());
		}
		Literal website = solution.getLiteral("website");
		if (website != null) {
			currency.setOfficialWebsite(website.getString());
		}
		Literal repo = solution.getLiteral("repo");
		if (repo != null) {
			currency.setSourceCodeRepository(repo.getString());
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

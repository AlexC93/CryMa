package ro.uaic.info.microservices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import ro.uaic.info.classes.Cryptocurrency;
import ro.wade.cryma.InternalDBInteractor.CryptoRetriever;
import ro.wade.cryma.InternalDBInteractor.Cryptocurrency;
import ro.wade.cryma.InternalDBInteractor.constants.Constants;

@Path("/details")
public class GetDetailsService {
	
	@GET
	@Path("/cryptocurrencies")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCryptocurrencies() {
		
		System.out.println("HEEEERE");
		/*
		Map<String, String> topCryptocurrencies = new HashMap<String, String>();
		String name1 = "Bitcoin";
		String value1 = "11763.20";
		String name2 = "Ethereum";
		String value2 = "1231.69";
		String name3 = "Ripple";
		String value3 = "1.32";
		String name4 = "Bitcoin Cash";
		String value4 = "1737.16";
		String name5 = "Cardano";
		String value5 = "0.66";
		topCryptocurrencies.put(name1, value1);
		topCryptocurrencies.put(name2, value2);
		topCryptocurrencies.put(name3, value3);
		topCryptocurrencies.put(name4, value4);
		topCryptocurrencies.put(name5, value5);
		*/
		
		List<String> topCryptocurrencies = CryptoRetriever.getCryptocurrencyNames(Constants.INTERNALDB_SPARQL_ENDPOINT);
		return Response.status(200).entity(topCryptocurrencies).build();
		
	}
	
	@GET
	@Path("/{cryptocurrencyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCryptocurrencyDetails(@PathParam("cryptocurrencyId") String id) {
			
		Cryptocurrency currency = CryptoRetriever.getCryptocurrency(Constants.INTERNALDB_SPARQL_ENDPOINT, id);
		
		
		
		
		String value = "";
		StringBuilder result = new StringBuilder();
		String parameter = id.toLowerCase();
		
		try {
			URL url = new URL("https://api.coinmarketcap.com/v1/ticker/" + parameter);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			while( (line = in.readLine()) != null) {
				result.append(line).append("\n");
			}
			//System.out.println("\nJersey REST service was successfully invoked.\nThe text is:\n\t" + connSb.toString());
			in.close();
		} catch(Exception e) {
			
			return Response.status(500).entity("The server encountered a problem").build();
		}
		
		//JSONParser parser = new JSONParser();
		String volume24h = "";
		String marketCap = "";
		String change24h = "";
		String change7d = "";
		try {
			JSONArray array = new JSONArray(result.toString());
			JSONObject object = (JSONObject) array.get(0);
			marketCap = object.get("market_cap_usd").toString();
			volume24h = object.get("24h_volume_usd").toString();
			change24h = object.get("percent_change_24h").toString();
			change7d = object.get("percent_change_7d").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		currency.setMarketCap(marketCap);
		currency.setChange24h(change24h + "%");
		currency.setChange7d(change7d + "%");
		currency.setVolume24h(volume24h);
		
		return Response.status(200).entity(currency).build();
		
	}
	
	@GET
	@Path("/{cryptocurrencyId}/value")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCryptocurrencyValue(@PathParam("cryptocurrencyId") String id) {
		
		//Cryptocurrency theCurrency = new Cryptocurrency();
		//theCurrency = getCryptocurrencyDetails(id);
		
		String value = "";
		StringBuilder result = new StringBuilder();
		String parameter = id.toLowerCase();
		
		try {
			URL url = new URL("https://api.coinmarketcap.com/v1/ticker/" + parameter);
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(15000);
			connection.setReadTimeout(15000);
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			String line;
			while( (line = in.readLine()) != null) {
				result.append(line).append("\n");
			}
			//System.out.println("\nJersey REST service was successfully invoked.\nThe text is:\n\t" + connSb.toString());
			in.close();
		} catch(Exception e) {
			
			return Response.status(500).entity("The server encountered a problem").build();
		}
		
		//JSONParser parser = new JSONParser();
		try {
			JSONArray array = new JSONArray(result.toString());
			JSONObject object = (JSONObject) array.get(0);
			value = object.get("price_usd").toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result.toString());
		return Response.status(200).entity(value.toString()).build();
		
	}
	
	@GET
	@Path("/{cryptocurrencyId}/prices")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getHistoricalPrices(@PathParam("cryptocurrencyId") String id) {
		
		//Cryptocurrency theCurrency = getCryptocurrencyDetails(id);
		Cryptocurrency theCurrency = CryptoRetriever.getCryptocurrency(Constants.INTERNALDB_SPARQL_ENDPOINT, id);
		
		//return Response.status(200).entity(theCurrency.getHistoricalValues()).build();
		return Response.status(200).entity(theCurrency.getHistoricalValues()).build();
		
	}
	
}
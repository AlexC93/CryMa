package ro.uaic.info.microservices;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Path("/news")
public class NewsService {
	
	@GET
	@Path("/general")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getGeneralNews() {
		
		StringBuilder result = new StringBuilder();
		
		String keyWord1 = "bitcoin";
		String keyWord2 = "crypto";
		String keyWord3 = "cryptocurrency";
		
		try {
			result.append(callNewsApi(keyWord1));
			result.append(callNewsApi(keyWord2));
			result.append(callNewsApi(keyWord3));
		} catch(Exception e) {
			
			return Response.status(500).entity("Bitcoin news could not be found").build();
		}
		
		
		
		//JSONParser parser = new JSONParser();
		System.out.println(result.toString());
		return Response.status(200).entity(result).build();
		
	}
	
	@GET
	@Path("/{cryptocurrencyId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSpecificNews(@PathParam("cryptocurrencyId") String id) {
		
		String idParameter = id.toLowerCase();
		String result = "";
		
		try {
			result = callNewsApi(idParameter);
		} catch(Exception e) {
			
			return Response.status(500).entity("Bitcoin news could not be found").build();
		}
		
		//JSONParser parser = new JSONParser();
		System.out.println(result.toString());
		return Response.status(200).entity(result.toString()).build();
		
	}
	
	public String callNewsApi(String keyword) {
		
		StringBuilder result = new StringBuilder();
		
		String apiKey = "&apiKey=020fd9c6691d453397dc494a21804cb1";
		String sortParameter = "&sortBy=popularity";
		
		// FROM - date format: 2018-01-29
		
		Date now = new Date();
		LocalDate nowDate = LocalDate.now();
		LocalDate monthOld = LocalDate.now().minusMonths(1);
		Date fromDate = new Date();
		Date toDate = new Date();
		try {
			fromDate = new SimpleDateFormat("yyyy-MM-dd").parse(monthOld.toString());
			toDate = new SimpleDateFormat("yyyy-MM-dd").parse(nowDate.toString());
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		//String fromParameter = "&from=" + fromDate.getYear() + "-" + fromDate.getMonth() + "-" + fromDate.getDate();
		//String toParameter = "&to=" + toDate.getYear() + "-" + toDate.getMonth() + "-" + toDate.getDate();
		
		String fromParameter = "&from=" + nowDate.getYear() + "-" + nowDate.getMonthValue() + "-" + nowDate.getDayOfMonth();
		String toParameter = "&to=" + monthOld.getYear() + "-" + monthOld.getMonthValue() + "-" + monthOld.getDayOfMonth();
		
		
		System.out.println("FROM DATE : " + fromParameter);
		System.out.println("TO DATE : " + toParameter);
		
		
		
		try {
			URL url = new URL("https://newsapi.org/v2/everything?q="
						+ keyword
						+ apiKey
						+ sortParameter
						+ fromParameter
						+ toParameter);
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
			
			//return Response.status(500).entity("Bitcoin news could not be found").build();
		}
		
		//JSONParser parser = new JSONParser();
		
		JSONObject theObject = new JSONObject();
		try {
			theObject = new JSONObject(result.toString());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		JSONArray array = new JSONArray();
		try {
			array = new JSONArray(theObject.get("articles").toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(result.toString());
		//return Response.status(200).entity(connSb.toString()).build();
		
		return array.toString();
	}
	
	
	
	
}

package ro.uaic.info.microservices;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;


import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.client.Client;


public class POSTClient {

	public static void main(String[] args) {
				
		
		
		String string = "{\"cryptocurrencyId\": \"Bitcoin\", \"comment\": \"To the mooon !!!\"}";
		try {
 
			JSONObject jsonObject = new JSONObject(string);
			System.out.println(jsonObject);
 
			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL("http://localhost:8080/CryMa/details/cryptocurrencies");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(15000);
				connection.setReadTimeout(15000);
			
				//OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				//out.write(jsonObject.toString());
				//out.write(jsonObject.toString());
				//out.close();
			
				
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				
				StringBuilder result = new StringBuilder();
				while ( (line = in.readLine()) != null) {
					result.append(line);
				}
				System.out.println("\nCrunchify REST Service Invoked Successfully...");
				System.out.println("RESULT IS : \n" + result);
				in.close();
			} catch (Exception e) {
				System.out.println("\nError while calling Crunchify REST Service");
				System.out.println(e);
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
		/*
		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject("{\n" +
					"\t\"cryptocurrencyId\": \"bitcoin\",\n" +
					"\t\"comment\": \"To the mooon !!!\"\n" +
					"}");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(jsonObject);

		String line;

		try {
			
			//URL url = new URL("http://localhost:8080/api/jerseyREST");
			URL url = new URL("http://localhost:8080/CryMa/comment");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(jsonObject.toString());
			out.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			
			StringBuilder connSb = new StringBuilder();
			while( (line = in.readLine()) != null) {
				connSb.append(line).append("\n");
			}
			System.out.println("\nJersey REST service was successfully invoked.\nThe text is:\n\t" + connSb.toString());
			in.close();
			
		} catch(Exception e) {
			System.out.println("\nError while calling Jersey REST service");
			System.out.println(e);
		}
		*/
		
		
		
		/*
		String result = client
        .target("http://localhost:8080/CryMa/")
        .path("details/cryptocurrencies")
        .request(MediaType.APPLICATION_JSON)
        .get(String.class);
		*/
		
		/*
		Response response =  client
        .target("http://localhost:8080/CryMa/comment")
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(theString, MediaType.APPLICATION_JSON));
		
		System.out.println("The result is: \n\t" + response.getEntity());
		*/
		
		//System.out.println("The result is: \n\t" + result);
		
		
		/*
		Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
		WebTarget webTarget = client.target("http://localhost:8080/JerseyDemos/rest").path("employees");
		 
		Employee emp = new Employee();
		emp.setId(1);
		emp.setName("David Feezor");
		 
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_XML);
		Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_XML));
		 
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
		*/

		System.out.println("\n\n\n");
		
		
		
		
		
		// TODO Auto-generated method stub
		/*
		Client client = Client.create();
		String theCurrency = "bitcoin";

		WebResource webResource = client.resource("http://localhost:8080/CryMa/comment");
		webResource.setProperty("cryptocurrencyId", theCurrency);
		String input = "This is the future";

		ClientResponse response = webResource.type("application/json")
		   .post(ClientResponse.class, input);
		*/
		
/*
		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
*/

		/*
		System.out.println("POST Output from Server .... \n");
		String output = response.getEntity(String.class);
		System.out.println(output);
		
		
		webResource = client.resource("http://localhost:8080/CryMa/details/Bitcoin");
		
		response = webResource.type("application/json").get(ClientResponse.class);
		*/
		
/*
		if (response.getStatus() != 201) {
			throw new RuntimeException("Failed : HTTP error code : "
			     + response.getStatus());
		}
*/

		/*
		System.out.println("GET Output from Server .... \n");
		output = response.getEntity(String.class);
		System.out.println(output);
		*/
		
		
	}
	
	public static void requestAlex() {
		try {

			Client client = Client.create();

			WebResource webResource = client
			   .resource("http://localhost:8080/CryMa/details/cryptocurrencies");

			ClientResponse response = webResource.accept("application/json")
	                   .get(ClientResponse.class);

			if (response.getStatus() != 200) {
			   throw new RuntimeException("Failed : HTTP error code : "
				+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		  } catch (Exception e) {

			e.printStackTrace();

		  }
	}

}

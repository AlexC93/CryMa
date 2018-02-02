package view;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.News;

@ManagedBean(name = "newsBean")
@ViewScoped
public class NewsBean implements Serializable {

	private static final long serialVersionUID = -8378581712078370508L;
	private List<News> latestNews;

	@PostConstruct
	public void init() {
		latestNews = new ArrayList<News>();
		StringBuilder result = new StringBuilder();
		
		
		try {
 
			// Step2: Now pass JSON File Data to REST Service
			try {
				URL url = new URL("http://localhost:8080/CryMa/news/general");
				URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(15000);
				connection.setReadTimeout(15000);
			
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line;
				
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
		
		String content = "";
		String url = "";
		String title = "";
		
		try {
			String lastResult = result.toString().substring(1, result.length() - 1).replaceAll("\\\"", "\"");
			JSONArray theArray = new JSONArray(lastResult.toString());
			for (int i = 0; i < theArray.length(); i++) {
				JSONObject object = theArray.getJSONObject(i);
				content = object.get("description").toString();
				url = object.get("url").toString();
				title = object.get("title").toString();
				latestNews.add(new News(content, url, title));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	public List<News> getLatestNews() {
		return latestNews;
	}

	public void setLatestNews(List<News> latestNews) {
		this.latestNews = latestNews;
	}
}
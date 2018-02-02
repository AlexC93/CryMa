package model;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Cryptocurrency {
	private String uri;
	private String label;
	private String shortName;
	private String symbol;
	private String officialWebsite;
	private String sourceCodeRepository;
	private String subreddit;
	private String twitterHashtag;
	private String logo;
	private String quora;
	private Map<Date, Double> historicalValues;
	private Map<Date, String> comments;
	
	public Cryptocurrency() {
		historicalValues = new TreeMap<>();
		comments = new TreeMap<>();
	}
	
	public String getURI() {
		return uri;
	}

	public void setURI(String uri) {
		this.uri = uri;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getOfficialWebsite() {
		return officialWebsite;
	}

	public void setOfficialWebsite(String officialWebsite) {
		this.officialWebsite = officialWebsite;
	}

	public String getSourceCodeRepository() {
		return sourceCodeRepository;
	}

	public void setSourceCodeRepository(String sourceCodeRepository) {
		this.sourceCodeRepository = sourceCodeRepository;
	}

	public String getSubreddit() {
		return subreddit;
	}

	public void setSubreddit(String subreddit) {
		this.subreddit = subreddit;
	}

	public String getTwitterHashtag() {
		return twitterHashtag;
	}

	public void setTwitterHashtag(String twitterHashtag) {
		this.twitterHashtag = twitterHashtag;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getQuora() {
		return quora;
	}

	public void setQuora(String quora) {
		this.quora = quora;
	}

	public Map<Date, Double> getHistoricalValues() {
		return historicalValues;
	}

	public void setHistoricalValues(Map<Date, Double> historicalValues) {
		this.historicalValues = historicalValues;
	}

	public Map<Date, String> getComments() {
		return comments;
	}

	public void setComments(Map<Date, String> comments) {
		this.comments = comments;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("uri = ").append(uri).append("\n");
		output.append("label = ").append(label).append("\n");
		output.append("shortName = ").append(shortName).append("\n");
		output.append("symbol = ").append(symbol).append("\n");
		output.append("officialWebsite = ").append(officialWebsite).append("\n");
		output.append("sourceCodeRepository = ").append(sourceCodeRepository).append("\n");
		output.append("subreddit = ").append(subreddit).append("\n");
		output.append("twitterHashtag = ").append(twitterHashtag).append("\n");
		output.append("logo = ").append(logo).append("\n");
		output.append("quora = ").append(quora).append("\n");
		output.append("historicalValues = ").append(historicalValues).append("\n");
		output.append("comments = ").append(comments).append("\n");
		return output.toString();
	}

}

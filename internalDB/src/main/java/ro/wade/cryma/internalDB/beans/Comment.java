package ro.wade.cryma.internalDB.beans;

public class Comment {
	
	private String cryptocoin;
	private String datePosted;
	private String content;

	public String getCryptocoin() {
		return cryptocoin;
	}

	public void setCryptocoin(String cryptocoin) {
		this.cryptocoin = cryptocoin;
	}

	public String getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(String commenterName) {
		this.datePosted = commenterName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}

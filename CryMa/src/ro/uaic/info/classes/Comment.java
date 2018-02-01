package ro.uaic.info.classes;

public class Comment {
	
	private String cryptocurrencyId;
	private String comment;
	
	public Comment(String cryptocurrencyId, String comment) {
		this.cryptocurrencyId = cryptocurrencyId;
		this.comment = comment;
	}

	public String getCryptocurrencyId() {
		return cryptocurrencyId;
	}

	public void setCryptocurrencyId(String cryptocurrencyId) {
		this.cryptocurrencyId = cryptocurrencyId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("Comment: cryptoID = ").append(cryptocurrencyId);
		output.append(" | comment = ").append(comment).append("\n");
		return output.toString();
	}

}

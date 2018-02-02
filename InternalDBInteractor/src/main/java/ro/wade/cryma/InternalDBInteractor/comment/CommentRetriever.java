package ro.wade.cryma.InternalDBInteractor.comment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class CommentRetriever {

	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/cryma";
	private Connection connection = null;

	public CommentRetriever() throws ClassNotFoundException, SQLException {
		initializeDBConnection();
	}

	public Map<Date, String> getComments(String cryptocurrency) {
		Map<Date, String> comments = new TreeMap<>();
		Statement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.createStatement();
			String sql = "SELECT comment, commentDate FROM cryma.comments WHERE LOWER(cryptocurrency) = '"
					+ cryptocurrency.toLowerCase() + "'";
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				String comment = rs.getString("comment");
				Date commentDate = new Date(rs.getTimestamp("commentDate").getTime());
				comments.put(commentDate, comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return comments;
	}

	public void saveComment(String cryptocurrency, String comment) {
		Statement statement = null;
		String sql = "INSERT INTO cryma.comments(cryptocurrency, comment) values ('" + cryptocurrency + "', '" + comment
				+ "')";
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * !!! Use it at the end to end the database connection
	 */
	public void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void initializeDBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		connection = DriverManager.getConnection(DB_URL, "alex", "pass123");
	}
}

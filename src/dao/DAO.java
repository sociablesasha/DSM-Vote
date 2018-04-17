package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {
	
	protected Connection connection = null;
	
	private final String databaseURL = "dbHost";
	private final String databaseID = "dbUser";
	private final String databasePW = "dbPassword";
	
	public DAO() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(databaseURL, databaseID, databasePW);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}

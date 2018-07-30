package app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnector {
	Connection conn;
	
	public DBConnector() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Driver Class Is Not Registered!");
		}
	}
	public Connection connInit() {
		String url = "jdbc:mysql://localhost:3306/usersjdbc";

		// please fill below your own SQL credentials

		String user = "root";
		String pass = "zatoichi";
		
		try {
		conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("Sorry...DB connection cannot be established!");

		}
		
		return conn;
	}
}



package com.excilys.formation.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum ConnexionDB {
	
	INSTANCE;
	private Connection conn;
	
	private ConnexionDB () {

	}
	
	public Connection getConnection() {
		Properties prop = new Properties();
		InputStream pathDB = getClass().getClassLoader().getResourceAsStream("config.properties");
			
		try {
			prop.load(pathDB);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		try {
			Class.forName(prop.getProperty("mysql.driver"));
			String urlDB = prop.getProperty("mysql.url");
			String user = prop.getProperty("mysql.user");
			String passwd = prop.getProperty("mysql.passwd");
			conn = DriverManager.getConnection(urlDB, user, passwd);
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void main (String[] args) throws SQLException {
		Connection conn = ConnexionDB.INSTANCE.getConnection();
		conn.close();
	}
	
}

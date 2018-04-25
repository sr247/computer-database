package com.excilys.formation.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnexionDB {
	private Connection conn;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ConnexionDB.class);
	
	private ConnexionDB () {}
	
	public Connection getConnection() {
		Properties prop = new Properties();
		InputStream pathDB = getClass().getClassLoader().getResourceAsStream("config.properties");
			
		try {
			prop.load(pathDB);
		} catch (IOException e) {
			logger.error("ConnexionDBError: {}", e.getMessage(), e);
		}	
		
		try {
			Class.forName(prop.getProperty("mysql.driver"));
			String urlDB = prop.getProperty("mysql.url");
			String user = prop.getProperty("mysql.user");
			String passwd = prop.getProperty("mysql.password");
			conn = DriverManager.getConnection(urlDB, user, passwd);
		}catch(SQLException | ClassNotFoundException e) {
			logger.error("ConnexionDBError: {}", e.getMessage(), e);
			
		}
		return conn;
	}
	
}

package com.excilys.formation.java.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
		String root = System.getProperty("user.dir");
		String fileConf = "config/db/config.properties";
		System.out.println(fileConf);
				
		try(FileInputStream fis = new FileInputStream(fileConf);){
			prop.load(fis);
			
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String driverName = prop.getProperty("mysql.Driver");
			String urlDB = prop.getProperty("mysql.Url");
			String user = prop.getProperty("mysql.user");
			String passwd = prop.getProperty("mysql.passwd");
			// Chargement du driver mysql et connexion a la base
			Class.forName(driverName);
			conn = DriverManager.getConnection(urlDB, user, passwd);
			
			/*
			 * Log into file : Connection succeded in data base
			 */
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

package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnexionDB {
	
	private static ConnexionDB _interface = null;
	private static Connection conn;
	
	private ConnexionDB () {
		try {
			// Chargement du driver mysql et connexion a la base
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db?useSSL=FALSE", "admincdb", "qwerty1234");	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ConnexionDB getInterface() {
		
		if(_interface == null) {
			_interface = new ConnexionDB();
		}
		return _interface;
	}
	
	public Connection getConnection() {
		return conn;
	}	
	
}

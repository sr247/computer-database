package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;





public class ConnexionDB {
		
	private static Connection conn;
	private int numComputers = -1;
	private int numCompanies = -1;
	
	public ConnexionDB () throws ClassNotFoundException, SQLException 
	{
			//Chargement du driver postgresql et connexion à la base
			Class.forName("com.mysql.jdbc.Driver");
			// conn = DriverManager.getConnection("jdbc:mysql://addresse_of_db??", "admincdb", "qwerty1234");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", "admincdb", "qwerty1234");	

	}	
	
	public Connection getConnection() {
		return conn;
	}

	
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		System.out.println("Test");
		ConnexionDB computerDataBase = new ConnexionDB();
		
		System.out.println("Done");
		
		CompagnyDB cpndb = new CompagnyDB();
		ComputerDB cptdb = new ComputerDB();
		ArrayList<Computeur> computers = new ArrayList<Compurter>();
		
	}
	
	
}

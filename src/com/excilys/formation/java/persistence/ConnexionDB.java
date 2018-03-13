package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.excilys.formation.java.mapper.CompanyMP;
import com.excilys.formation.java.mapper.ComputerMP;


public class ConnexionDB {
		
	protected static Connection conn;

	
	public ConnexionDB () {
		try {
			//Chargement du driver postgresql et connexion à la base
			Class.forName("com.mysql.jdbc.Driver");
			// conn = DriverManager.getConnection("jdbc:mysql://addresse_of_db??", "admincdb", "qwerty1234");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", "admincdb", "qwerty1234");	
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public Connection getConnection() {
		return conn;
	}

	
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
//		System.out.println("Test");
//		ConnexionDB computerDataBase = new ConnexionDB();		
//		System.out.println("Done");		
//		ComputerDB cmpdb = new ComputerDB();
//
//		for(ComputerMP cmp : cmpdb.getComputerList(0, 10)) {
//			System.out.println(cmp);
//		}
//		
//		System.out.println(cmpdb.getComputerByID(5));
//		
//		/*
//		CompagnyDB cpndb = new CompagnyDB();
//		for(CompanyMP cpn : cpndb.getCompanyList(conn)) {
//			System.out.println(cpn);
//		}*/
//		
//		
//		int id = cmpdb.getNumComputers(conn);
//		ComputerMP cp = new ComputerMP(id, "hello"+id, new Date(0), null, 1);
//		//cmpdb.create(cp, conn);
//		cmpdb.delete(cp, conn);
		
		
	}
	
	
}

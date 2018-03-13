package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.excilys.formation.java.mapper.CompanyMP;
import com.excilys.formation.java.mapper.ComputerMP;




public class ConnexionDB {
		
	private static Connection conn;
	private ComputerDB cmpdb;
	private CompagnyDB cpndb;
	
	/**
	 * @return the cmpdb
	 */
	public ComputerDB getCmpdb() {
		return cmpdb;
	}

	/**
	 * @return the cpndb
	 */
	public CompagnyDB getCpndb() {
		return cpndb;
	}

	
	// Est-ce vraiment nécéssaire de Set ces objets ??
	/**
	 * @param cmpdb the cmpdb to set
	
	public void setCmpdb(ComputerDB cmpdb) {
		this.cmpdb = cmpdb;
	}

	/**
	 * @param cpndb the cpndb to set
	
	public void setCpndb(CompagnyDB cpndb) {
		this.cpndb = cpndb;
	}
	 */
	
	public ConnexionDB () {
		try {
			//Chargement du driver postgresql et connexion Ã  la base
			Class.forName("com.mysql.jdbc.Driver");
			// conn = DriverManager.getConnection("jdbc:mysql://addresse_of_db??", "admincdb", "qwerty1234");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/computer-database-db", "admincdb", "qwerty1234");	
			cmpdb = new ComputerDB();
			cpndb = new CompagnyDB();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}	
	
	public Connection getConnection() {
		return conn;
	}

	
	public static void main(String args[]) throws ClassNotFoundException, SQLException{
		System.out.println("Test");
		ConnexionDB computerDataBase = new ConnexionDB();		
		System.out.println("Done");
	
		
		ComputerDB cpmdb = new ComputerDB();
		for(ComputerMP cmp : cpmdb.getComputerList(conn, 0, 10)) {
			System.out.println(cmp);
		}
		
		System.out.println(cpmdb.getComputer(5, conn));
		
		/*
		CompagnyDB cpndb = new CompagnyDB();
		for(CompanyMP cpn : cpndb.getCompanyList(conn)) {
			System.out.println(cpn);
		}*/
		
		
	}
	
	
}

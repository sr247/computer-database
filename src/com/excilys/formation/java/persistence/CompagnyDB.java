package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.mapper.CompanyMP;

public class CompagnyDB {
	private int numCompanies;
	
	
	public int getNumCompanies(Connection conn) throws SQLException {
		if (numCompanies == -1){
			Statement s = conn.createStatement();
			ResultSet res = s
					.executeQuery("SELECT COUNT(*) AS NUM FROM MOVIE");
			res.next();
			numCompanies = res.getInt("NUM");
		}
		
		return numCompanies;
	}
	
	
	public ArrayList<Company> getComputerList(Connection conn) throws SQLException {
		ArrayList<Company> companies = new ArrayList<Company>();
		// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
		Statement s = conn.createStatement();
		ResultSet res = s.executeQuery("SELECT * FROM COMPUTERS ORDER BY TITLE");
		
		while (res.next())
			companies.add(CompanyMP.map(res));
	
		return companies;
	}
	
	
}

package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.mapper.CompanyMP;
import com.mysql.jdbc.PreparedStatement;

public class CompagnyDB {
	
	private int numCompanies = -1;	
	
	public int getNumCompanies(Connection conn) throws SQLException {
		if (numCompanies == -1){
			Statement s = conn.createStatement();
			ResultSet res = s
					.executeQuery("SELECT COUNT(*) AS NUM FROM company");
			res.next();
			numCompanies = res.getInt("NUM");
		}
		
		return numCompanies;
	}	
	
	public ArrayList<CompanyMP> getCompanyList(Connection conn) {
		ArrayList<CompanyMP> companies = new ArrayList<CompanyMP>();
		// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
		try {
			Statement s = conn.createStatement();
			ResultSet res = s.executeQuery("SELECT * FROM company ORDER BY ID");
			
			while (res.next())
				companies.add(CompanyMP.map(res));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return companies;
	}
	
	public ArrayList<CompanyMP> getCompanyList(Connection conn, int from, int to) {
		ArrayList<CompanyMP> companies = new ArrayList<CompanyMP>();
		// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
		try {
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement("SELECT * FROM company"
							+ " ORDER BY ID"
							+ " LIMIT ? OFFSET ?");
			ps.setInt(1, to-from);
			ps.setInt(2, from);
			ResultSet res = ps.executeQuery();
			
			while (res.next())
				companies.add(CompanyMP.map(res));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	
		return companies;
	}
	
	
	
	
}

package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.mapper.CompanyMP;
import com.mysql.jdbc.PreparedStatement;

public class CompagnyDB {
	
	private static int numCompanies = -1;	
	
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
	

	public void create(CompanyMP cp, Connection conn) throws SQLException {
		PreparedStatement crt = (PreparedStatement) conn.prepareStatement("INSERT INTO computer (ID, NAME)"
				+ "VALUES 	(?, ?");
		
		crt.setInt(1, cp.getId());
		crt.setString(2, cp.getName());
		
		crt.executeUpdate();
		conn.commit();
		
	}
	
	/*
	 * Ici check : Field doit déja convenir au champs équivalent requis dans la table.
	 * Et n'oubli pas de faire des Test Genre MAINTENANT !
	 */
	
	public void update(String field, CompanyMP cmp, Connection conn) {
		PreparedStatement upd;
		try {
			upd = (PreparedStatement) conn.prepareStatement("UPDATE company "
					+ "SET ?=?"
					+ "WHERE ID=?");
		
	
			upd.setString(1, field);
			if("NAME".equals(field)) {
				upd.setString(2, cmp.getName());
			}
			upd.setInt(3, cmp.getId());
			
			upd.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void delete(CompanyMP cmp, Connection conn) {
		try {
			PreparedStatement upd = (PreparedStatement) conn.prepareStatement("DELETE FROM company"
					+ " WHERE ID=?");
			upd.setInt(1, cmp.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}

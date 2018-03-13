package com.excilys.formation.java.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.mapper.CompanyMP;
import com.mysql.jdbc.PreparedStatement;

public class CompanyDB extends ConnexionDB {
	
	private static int numCompanies = -1;	
	
	public int getNumCompanies() {
		if (numCompanies == -1) {
			Statement s;
			try {
				s = conn.createStatement();
				ResultSet res = s
						.executeQuery("SELECT COUNT(*) AS NUM FROM company");
				res.next();
				numCompanies = res.getInt("NUM");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return numCompanies;
	}	
	
	public ArrayList<CompanyMP> getCompanyList() {
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
	
	public ArrayList<CompanyMP> getCompanyList(int from, int to) {
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
	

	public void create(String name) {
		PreparedStatement crt;
		int id = getNumCompanies()+1;
		try {
			crt = (PreparedStatement) conn.prepareStatement("INSERT INTO computer (ID, NAME)"
					+ "VALUES 	(?, ?");
			
			crt.setInt(1, id);
			crt.setString(2, name);
			
			crt.executeUpdate();
			// Can't call commit, when autocommit:true
			// conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CompanyMP cpy = new CompanyMP(id, name);
		System.out.println("Created:" + cpy);
		
	}
	
	/*
	 * Ici check : Field doit déja convenir au champs équivalent requis dans la table.
	 * Et n'oubli pas de faire des Test Genre MAINTENANT !
	 */
	
	public void update(String field, CompanyMP cmp) {
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
			// Can't call commit, when autocommit:true
			// conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void delete(CompanyMP cmp) {
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

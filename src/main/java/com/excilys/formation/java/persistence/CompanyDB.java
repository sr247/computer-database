package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.mapper.CompanyMapper;
import com.excilys.formation.java.model.Company;

public enum CompanyDB {
	
	INSTANTCE;
		
	private static int numCompanies = -1;	
	
	private final static String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM company;";
	private final static String SELECT_ONE = "SELECT * FROM company WHERE ID=?;";
	private final static String SELECT_UNLIMITED_LIST = "SELECT * FROM company ORDER BY ID;";
	private final static String SELECT_LIMITED_LIST = "SELECT * FROM company ORDER BY ID LIMIT ? OFFSET ?;";
	private final static String CREATE_REQUEST  = "INSERT INTO computer (ID, NAME) VALUES (?, ?);";
	private final static String UPDTATE_REQUEST = "UPDATE company SET ?=? WHERE ID=?;";
	private final static String DELETE_REQUEST  = "DELETE FROM company WHERE ID=?";
	
	private CompanyDB() {
		
	}
	
	public int getNumCompanies() {
		
		if (numCompanies == -1) {
			Statement s;
			try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
				s = conn.createStatement();
				ResultSet res = s
						.executeQuery(COUNT_NUMBER_OF);
				res.next();
				numCompanies = res.getInt("NUM");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return numCompanies;
	}
	
	public Company getCompanyByID(int id) {
			PreparedStatement sel = null;
			ResultSet res = null;
			Company cpy = null;
			try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
				sel = (PreparedStatement) 
						conn.prepareStatement(SELECT_ONE);
				sel.setInt(1, id);
				res = sel.executeQuery();				
				res.next();
				cpy = CompanyMapper.map(res);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cpy;
	}
	
	public List<Company> getCompanyList() {
		List<Company> companies = new ArrayList<Company>();
		// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			Statement s = conn.createStatement();
			ResultSet res = s.executeQuery(SELECT_UNLIMITED_LIST);
			
			while (res.next())
				companies.add(CompanyMapper.map(res));
			
		}catch (Exception e) {
			e.printStackTrace();
		}		
		return companies;
	}
	
	public List<Company> getCompanyList(int from, int to) {
		List<Company> companies = new ArrayList<Company>();
		// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_LIMITED_LIST);
			ps.setInt(1, to-from);
			ps.setInt(2, from);
			ResultSet res = ps.executeQuery();
			
			while (res.next())
				companies.add(CompanyMapper.map(res));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	
		return companies;
	}
	

	private void create(String name) {
		PreparedStatement crt;
		int id = getNumCompanies()+1;
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			crt = (PreparedStatement) conn.prepareStatement(CREATE_REQUEST);
			
			crt.setInt(1, id);
			crt.setString(2, name);
			
			crt.executeUpdate();
			// Can't call commit, when autocommit:true
			// conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Company cpy = new Company(id, name);
		System.out.println("Created:" + cpy);
		
	}

	
	private void update(String field, Company cmp) {
		PreparedStatement upd;
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			upd = (PreparedStatement) conn.prepareStatement(UPDTATE_REQUEST);

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
	
	private void delete(Company cmp) {
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement upd = (PreparedStatement) conn.prepareStatement(DELETE_REQUEST);
			upd.setInt(1, cmp.getId());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
}

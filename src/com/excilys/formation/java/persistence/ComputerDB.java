package com.excilys.formation.java.persistence;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.java.mapper.ComputerMapper;
import com.excilys.formation.java.model.Computer;
import java.sql.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public enum ComputerDB {
	
	INSTANCE;
	
	private static int numComputers = -1;
	
	private final static String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM computer;";
	private final static String SELECT_ONE = "SELECT * FROM computer WHERE ID=?;";
	private final static String SELECT_UNLIMITED_LIST ="SELECT * FROM computer ORDER BY ID;";
	private final static String SELECT_LIMIT_LIST = "SELECT * FROM computer ORDER BY ID LIMIT ? OFFSET ?;";
	private final static String CREATE_REQUEST  = "INSERT INTO computer (NAME, INTRODUCED, DISCONTINUED, COMPANY_ID) VALUES (?, ?, ?, ?);";
	private final static String UPDTATE_REQUEST = "UPDATE computer SET NAME=?, INTRODUCED=?, DISCONTINUED=?, COMPANY_ID=? WHERE ID=?;";
	private final static String DELETE_REQUEST  = "DELETE FROM computer WHERE ID=?;";
		
	private ComputerDB() {
		
	}


	public int getNumComputers() {
		Statement s;
		try (Connection conn = ConnexionDB.INSTANCE.getConnection();)
		{
			s = conn.createStatement();
			ResultSet res = s
					.executeQuery(COUNT_NUMBER_OF);
			res.next();
			numComputers = res.getInt("NUM");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return numComputers;
	}
	
	public Computer getComputerByID(int id) {
		PreparedStatement ps = null;
		ResultSet res = null;
		Computer cmp = null;
		try (Connection conn = ConnexionDB.INSTANCE.getConnection();){
			ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_ONE);
			ps.setInt(1, id);
			res = ps.executeQuery();
			res.next();
			cmp = ComputerMapper.map(res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cmp;
	}
	
	public List<Computer> getComputerList() {
		
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_UNLIMITED_LIST);
			ResultSet res = ps.executeQuery();
			while (res.next())
				computers.add(ComputerMapper.map(res));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return computers;
	}
	
	public List<Computer> getComputerList(int from, int to) {		
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_LIMIT_LIST);
			ps.setInt(1, to-from);
			ps.setInt(2, from);
			ResultSet res = ps.executeQuery();
			while (res.next())
				computers.add(ComputerMapper.map(res));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return computers;
	}
	
	
	public PreparedStatement setDateProperly(Date dt, PreparedStatement ps, int i) throws SQLException {
		if(dt == null) {
			ps.setNull(i, java.sql.Types.DATE);
		} else {
			ps.setDate(i, dt);				
		} 
		return ps;
	}
	
	
	public void create(Computer cmp) {
		//Can't call commit, when autocommit:true
		PreparedStatement crt;
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			crt = (PreparedStatement) 
					conn.prepareStatement(CREATE_REQUEST);				
			crt.setString(1, cmp.getName());
			crt = setDateProperly(cmp.getIntroduced(), crt, 2);
			crt = setDateProperly(cmp.getDiscontinued(), crt, 3);
			crt.setInt(4, cmp.getCompanyId());
			crt.executeUpdate();
			// Ici le out deviendra un log
			System.out.println("Created:" + cmp);
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// System.err.println(e.getMessage());
			System.err.println("Erreur: clé de company inexistante");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/*
	 * Ici on update forcément tout.. Bonne ou mauvaise solution ?
	 * Essayer une version qui update seulement x champs.
	 */
	public void update(Computer cmp) {
		PreparedStatement upd;
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			upd = (PreparedStatement) conn.prepareStatement(UPDTATE_REQUEST);		

			upd.setString(2, cmp.getName());		
			upd.setDate(2, cmp.getIntroduced());		
			upd.setDate(2, cmp.getDiscontinued());		
			upd.setInt(2, cmp.getCompanyId());		
			upd.setInt(3, cmp.getId());
			upd.executeUpdate();
			
			// Ici le out deviendra un log
			System.out.println("Updated:" + cmp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// Pour le moment on recoit juste l'id
	public void delete(Computer cmp) {
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement del = (PreparedStatement) conn.prepareStatement(DELETE_REQUEST);
			del.setInt(1, cmp.getId());
			del.executeUpdate();
			
			// Ici le out deviendra un log
			System.out.println("Deleted:" + cmp);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

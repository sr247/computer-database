package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerDB {
	
	INSTANCE;
	
	private static int numComputers = -1;
	
	private final static String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM computer;";
	private final static String SELECT_ONE = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, " + 
			"company.id as caId, company.name as caName " + 
			"FROM computer " + 
			"LEFT JOIN company ON company.id = ?;";
	
	// Il y a surement moyen de faire une seul requete avec les deux suivantes 
	private final static String SELECT_UNLIMITED_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "ORDER BY ID;";
	private final static String SELECT_LIMIT_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "ORDER BY ID LIMIT ? OFFSET ?;";
	
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
			cmp = ComputerMapper.map(res).get();
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
				computers.add(ComputerMapper.map(res).get());
			
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
				computers.add(ComputerMapper.map(res).get());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return computers;
	}
	
	
	public PreparedStatement setDateProperly(LocalDate date, PreparedStatement ps, int i) throws SQLException {
		if(date == null) {
			ps.setNull(i, java.sql.Types.DATE);
		} else {
			Date dt = Date.valueOf(date);
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
			crt.setInt(4, cmp.getCompany().getId());
			crt.executeUpdate();
			// Ici le out deviendra un log
			System.out.println("Created:" + cmp);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			// System.err.println(e.getMessage());
//			System.err.println("Erreur: clé de company inexistante");
//		
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
			upd.setDate(2, Date.valueOf(cmp.getIntroduced()));		
			upd.setDate(2, Date.valueOf(cmp.getDiscontinued()));		
			upd.setInt(2, cmp.getCompany().getId());		
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

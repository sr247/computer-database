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
import java.util.NoSuchElementException;
import java.util.Optional;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.exceptions.ModifyDatabaseException;
import com.excilys.formation.cdb.exceptions.NumberOfInstanceException;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerDB {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyDB.class);
	
	private static int numComputers = -1;	
	private final static String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM computer;";
	private final static String SELECT_ONE = 
			"SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, company_id, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "WHERE computer.id = ?;"; 
	private final static String SELECT_UNLIMITED_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "ORDER BY computer.id;";
	private final static String SELECT_LIMITED_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "ORDER BY computer.id LIMIT ?, ?;";
	private final static String CREATE_REQUEST  = "INSERT INTO computer (NAME, INTRODUCED, DISCONTINUED, COMPANY_ID) VALUES (?, ?, ?, ?);";
	private final static String UPDTATE_REQUEST = "UPDATE computer SET NAME=?, INTRODUCED=?, DISCONTINUED=?, COMPANY_ID=? WHERE ID=?;";
	private final static String DELETE_REQUEST  = "DELETE FROM computer WHERE ID=?;";
		
	private ComputerDB() {
		
	}

	public int getNumComputers() throws NumberOfInstanceException {
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
			logger.error("NumberOfInstanceError: ", e.getMessage(), e);
			throw new NumberOfInstanceException("NumberOfInstanceError: " + e.getMessage(), e);
		} catch (NullPointerException e) {
			logger.error("CreateStatement: ", e.getMessage(), e);
			throw new NumberOfInstanceException("CreateStatement: " + e.getMessage(), e);
		}
		return numComputers;
	}
	
	public Computer getComputerByID(int id) throws InstanceNotInDatabaseException {
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
		} catch (SQLException|NoSuchElementException e) {
			// TODO Auto-generated catch block
			logger.error("InstanceNotInDatabaseError: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: computer not found.");
		}
		return cmp;
	}
	
	public List<Computer> getComputerList() throws InstanceNotInDatabaseException {
		
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_UNLIMITED_LIST);
			ResultSet res = ps.executeQuery();
			while (res.next())
				computers.add(ComputerMapper.map(res).get());
			
		} catch(Exception e) {
			logger.warn("Warning: " + e.getMessage());
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: computers not found");
		}	
		return computers;
	}
	
	public List<Computer> getComputerList(int limit, int offset) throws InstanceNotInDatabaseException {
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_LIMITED_LIST);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			ResultSet res = ps.executeQuery();
			while (res.next())
				computers.add(ComputerMapper.map(res).get());
			
		}catch(Exception e) {
			logger.warn("Warning: " + e.getMessage());
			throw new InstanceNotInDatabaseException("Erreur: ordinateur introuvable");
		}
		return computers;
	}
	
	
	public PreparedStatement setDateProperly(Optional<LocalDate> date, PreparedStatement ps, int i) throws SQLException {
		if(!date.isPresent()) {
			ps.setNull(i, java.sql.Types.DATE);
		} else {
			Date dt = Date.valueOf(date.get());
			ps.setDate(i, dt);				
		} 
		return ps;
	}
	
	
	public void create(Computer cmp) throws ModifyDatabaseException {
		//Can't call commit, when autocommit:true
		PreparedStatement crt;
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			crt = (PreparedStatement) 
					conn.prepareStatement(CREATE_REQUEST);				
			crt.setString(1, cmp.getName());
			crt = setDateProperly(Optional.ofNullable(cmp.getIntroduced()), crt, 2);
			crt = setDateProperly(Optional.ofNullable(cmp.getDiscontinued()), crt, 3);
			crt.setInt(4, cmp.getCompany().getId());
			crt.executeUpdate();
			// Ici le out deviendra un log
			logger.warn("Created:" + cmp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("CreationOfInstanceError: " + e.getMessage());
			throw new ModifyDatabaseException("CreationOfInstanceError: computer couldn't be created", e);
		} 
	}
	
	/*
	 * Ici on update forcément tout.. Bonne ou mauvaise solution ?
	 * Essayer une version qui update seulement x champs.
	 */
	public void update(Computer cmp) throws ModifyDatabaseException {
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
			logger.info("Updated:" + cmp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("UpdateOfInstanceError: " + e.getMessage());
			throw new ModifyDatabaseException("UpdateOfInstanceError: computer couldn't be updated", e);
		}
	}	
	
	// Pour le moment on recoit juste l'id
	public void delete(Computer cmp) throws ModifyDatabaseException {
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement del = (PreparedStatement) conn.prepareStatement(DELETE_REQUEST);
			del.setInt(1, cmp.getId());
			del.executeUpdate();
			logger.info("Deleted:" + cmp);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("DeletionOfInstanceError: " + e.getMessage());
			throw new ModifyDatabaseException("DeletionOfInstanceError: computer couldn't be deleted", e);
		}
	}
}

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
	private static final String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM computer;";
	private static final String SELECT_ONE = 
			"SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, company_id, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "WHERE computer.id = ?;"; 
	private static final String SELECT_UNLIMITED_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "ORDER BY computer.id;";
	private static final String SELECT_LIMITED_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, " 
			+ "company.id as caId, company.name as caName "
			+ "FROM computer "
			+ "LEFT JOIN company ON company.id = computer.company_id "
			+ "ORDER BY computer.id LIMIT ?, ?;";
	private static final String CREATE_REQUEST  = "INSERT INTO computer (NAME, INTRODUCED, DISCONTINUED, COMPANY_ID) VALUES (?, ?, ?, ?);";
	private static final String UPDTATE_REQUEST = 
			"UPDATE computer SET NAME=?, INTRODUCED=?, DISCONTINUED=?, COMPANY_ID=? WHERE ID=?;";
	private static final String DELETE_REQUEST  = "DELETE FROM computer WHERE ID=?;";
		
	private ComputerDB() {
		
	}

	public int getNumComputers() throws NumberOfInstanceException {
		Statement s;
		try (Connection conn = (Connection) DataSource.getConnection();){
			s = conn.createStatement();
			ResultSet res = s.executeQuery(COUNT_NUMBER_OF);
			res.next();
			numComputers = res.getInt("NUM");
		} catch (SQLException e) {
			logger.error("NumberOfInstanceError: ", e.getMessage(), e);
			throw new NumberOfInstanceException("NumberOfInstanceError: " + e.getMessage(), e);
		} catch (NullPointerException e) {
			logger.error("ComputerDB: ", e.getMessage(), e);
			throw new NumberOfInstanceException("ComputerDB: " + e.getMessage(), e);
		}
		return numComputers;
	}
	
	public Computer getComputerByID(int id) throws InstanceNotInDatabaseException {
		PreparedStatement ps = null;
		ResultSet res = null;
		Computer cmp = null;
		try (Connection conn = (Connection) DataSource.getConnection();){
			ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_ONE);
			ps.setInt(1, id);
			res = ps.executeQuery();
			res.next();
			cmp = ComputerMapper.map(res).get();
		} catch (SQLException|NoSuchElementException e) {
			logger.debug("InstanceNotInDatabaseError: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: computer not found.");
		}
		return cmp;
	}
	
	public List<Computer> getComputerList() throws InstanceNotInDatabaseException {
		
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection conn = (Connection) DataSource.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_UNLIMITED_LIST);
			ResultSet res = ps.executeQuery();
			while (res.next())
				computers.add(ComputerMapper.map(res).get());
			
		} catch(Exception e) {
			logger.debug("ComputerDB: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: computers not found");
		}
		return computers;
	}
	
	public List<Computer> getComputerList(int limit, int offset) throws InstanceNotInDatabaseException {
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection conn = (Connection) DataSource.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_LIMITED_LIST);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			ResultSet res = ps.executeQuery();
			while (res.next())
				computers.add(ComputerMapper.map(res).get());
			
		}catch(Exception e) {
			logger.debug("ComputerDB: {}", e.getMessage(), e);
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
		try (Connection conn = (Connection) DataSource.getConnection();){
			crt = (PreparedStatement) 
					conn.prepareStatement(CREATE_REQUEST);				
			crt.setString(1, cmp.getName());
			crt = setDateProperly(Optional.ofNullable(cmp.getIntroduced()), crt, 2);
			crt = setDateProperly(Optional.ofNullable(cmp.getDiscontinued()), crt, 3);
			crt.setInt(4, cmp.getCompany().getId());
			crt.executeUpdate();
			logger.info("Created: {}", cmp);
		} catch (SQLException e) {
			logger.debug("CreationOfInstanceError: {}", e.getMessage(), e);
			throw new ModifyDatabaseException("CreationOfInstanceError: computer couldn't be created", e);
		} 
	}
	
	public void update(Computer cmp) throws ModifyDatabaseException {
		PreparedStatement upd;
		try (Connection conn = (Connection) DataSource.getConnection();){
			upd = (PreparedStatement) conn.prepareStatement(UPDTATE_REQUEST);
			upd.setString(1, cmp.getName());
			setDateProperly(Optional.ofNullable(cmp.getIntroduced()), upd, 2);
			setDateProperly(Optional.ofNullable(cmp.getDiscontinued()), upd, 3);		
			upd.setInt(4, cmp.getCompany().getId());		
			upd.setInt(5, cmp.getId());
			upd.executeUpdate();
			logger.info("Updated: {}", cmp);
		} catch (SQLException e) {
			logger.debug("UpdateOfInstanceError: {}", e.getMessage(), e);
			throw new ModifyDatabaseException("UpdateOfInstanceError: computer couldn't be updated", e);
		}
	}	
	
	public void delete(Computer cmp) throws ModifyDatabaseException {
		try (Connection conn = (Connection) DataSource.getConnection();){
			PreparedStatement del = (PreparedStatement) conn.prepareStatement(DELETE_REQUEST);
			del.setInt(1, cmp.getId());
			del.executeUpdate();
			logger.info("Deleted: {}", cmp);
		} catch (SQLException e) {
			logger.debug("DeletionOfInstanceError: {}", e.getMessage(), e);
			throw new ModifyDatabaseException("DeletionOfInstanceError: computer couldn't be deleted", e);
		}
	}
}

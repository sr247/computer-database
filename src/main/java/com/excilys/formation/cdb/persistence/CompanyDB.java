package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.exceptions.ModifyDatabaseException;
import com.excilys.formation.cdb.exceptions.NumberOfInstanceException;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;

@Repository
public class CompanyDB {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyDB.class);
	
	private static int numCompanies;	
	private static final String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM company;";
	private static final String SELECT_ONE = "SELECT ID as caId, NAME as caName FROM company WHERE ID=?;";
	private static final String SELECT_UNLIMITED_LIST = "SELECT ID as caId, NAME as caName FROM company ca ORDER BY ID;";
	private static final String SELECT_LIMITED_LIST = "SELECT ID as caId, NAME as caName FROM company ORDER BY ID LIMIT ?, ?;";
	private static final String CREATE_REQUEST  = "INSERT INTO company NAME VALUES ?;";
	private static final String UPDTATE_REQUEST = "UPDATE company SET ?=? WHERE ID=?;";
	private static final String DELETE_REQUEST  = "DELETE FROM company WHERE ID=?";
	private static final String INSTANCE_ERROR_LOGGER = "InstanceNotInDatabaseError: {}";
	private static final String INSTANCE_ERROR_EXCEPTION = "InstanceNotInDatabaseError: %s";
		
	
	@Autowired
	private ComputerDB computerDB;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private DataSource datasource;
	
	
	public int getNumCompanies() throws NumberOfInstanceException {
		ResultSet res = null;
		try (Connection conn = datasource.getConnection();
			Statement state = conn.createStatement(); ){
			res = state.executeQuery(COUNT_NUMBER_OF);
			res.next();
			numCompanies = res.getInt("NUM");
		} catch (SQLException e) {
			logger.error("NumberOfInstanceException: {}", e.getMessage(), e);
			throw new NumberOfInstanceException(String.format("NumberOfInstanceException: %s", e.getMessage()), e);
		}
		return numCompanies;
	}
	
	public Optional <Company> getCompanyByID(int id) throws InstanceNotInDatabaseException {
		PreparedStatement ps = null;
		ResultSet res = null;
		Optional<Company> cpy = Optional.empty();
		try (Connection conn = datasource.getConnection();){
			ps = conn.prepareStatement(SELECT_ONE);
			ps.setInt(1, id);
			res = ps.executeQuery();
			res.next();
			cpy = companyMapper.map(res);
		} catch (SQLException e) {
			logger.error(INSTANCE_ERROR_LOGGER, e.getMessage(), e);
			throw new InstanceNotInDatabaseException(String.format(INSTANCE_ERROR_EXCEPTION, "company not found"), e);
		}
		return cpy;
	}

	public List<Company> getCompanyList() throws InstanceNotInDatabaseException {
		List<Company> companies = new ArrayList<>();
		try (Connection conn = datasource.getConnection();
			Statement state = conn.createStatement();
			ResultSet res = state.executeQuery(SELECT_UNLIMITED_LIST); ){
			while (res.next()) {
				Optional<Company> company = companyMapper.map(res);
				if(company.isPresent())
					companies.add(company.get());				
			}
		}catch (SQLException e) {
			logger.error(INSTANCE_ERROR_LOGGER, e.getMessage(), e);
			throw new InstanceNotInDatabaseException(String.format(INSTANCE_ERROR_EXCEPTION, "companies not found"), e);
		}
		return companies;
	}
	
	public List<Company> getCompanyList(int limit, int offset) throws DAOException {
		List<Company> companies = new ArrayList<>();
		try (Connection conn = datasource.getConnection();
			PreparedStatement ps = conn.prepareStatement(SELECT_LIMITED_LIST); ){
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			ResultSet res = ps.executeQuery();			
			while (res.next()) {
				Optional<Company> company = companyMapper.map(res);
				if(company.isPresent())
					companies.add(company.get());
			}
		}catch (SQLException e) {
			logger.error(INSTANCE_ERROR_LOGGER, e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: companies not found", e);
		}
		return companies;
	}
	

	private void create(Company cpy) throws DAOException { 
		// Future
	}

	
	private void update(String field, Company cmp) throws DAOException {
		// Future
	}
	
	public void delete(Company cpy) throws DAOException {
		Connection conn = null;
		PreparedStatement upd = null;
		try {
			conn = datasource.getConnection();
			conn.setAutoCommit(false);
			List<Integer> computers = computerDB.getAllComputersRelatedToCompanyWithID(cpy.getId());
			ComputerDB.deleteTransactionalFromIDList(computers, conn);
			upd = conn.prepareStatement(DELETE_REQUEST);
			upd.setInt(1, cpy.getId());
			upd.executeQuery();
			conn.commit();
		} catch (SQLException e1) {
			logger.error("DeletionOfInstanceError: {}", e1.getMessage(), e1);
			try {
				if(conn != null)
					conn.rollback();
			}catch(SQLException e) {
				logger.error("DeletionOfInstanceError: {}", e.getMessage(), e);
				throw new ModifyDatabaseException("DeletionOfInstanceError: Rollback has failed.", e);
			}
		} catch(InstanceNotInDatabaseException e2) {
			logger.error("DeletionOfInstanceError: {}", e2.getMessage(), e2);
		} finally {
			try {
				if(upd != null)
					upd.close();
				if(conn != null)
					conn.close();
			} catch (SQLException e) {
				logger.error("CloseConnectionException: {}", e.getMessage(), e);
			}
		}	
	}
	
	
	
	
}

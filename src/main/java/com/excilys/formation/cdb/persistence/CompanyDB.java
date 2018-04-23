package com.excilys.formation.cdb.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NestedRuntimeException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.exceptions.NumberOfInstanceException;
import com.excilys.formation.cdb.mapper.row.CompanyRowMapper;
import com.excilys.formation.cdb.model.Company;

@Repository
public class CompanyDB {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyDB.class);
	
	private static int numCompanies;	
	private static final String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM company;";
	private static final String SELECT_BY_ID = "SELECT ID as id, NAME as name FROM company WHERE ID=?;";
	private static final String SELECT_UNLIMITED_LIST = "SELECT ID as id, NAME as name FROM company ca ORDER BY ID;";
	private static final String SELECT_LIMITED_LIST = "SELECT ID as id, NAME as name FROM company ORDER BY ID LIMIT ?, ?;";
	private static final String CREATE_REQUEST  = "INSERT INTO company NAME VALUES ?;";
	private static final String UPDTATE_REQUEST = "UPDATE company SET ?=? WHERE ID=?;";
	private static final String DELETE_REQUEST  = "DELETE FROM company WHERE ID=?";
	private static final String INSTANCE_ERROR_LOGGER = "InstanceNotInDatabaseError: {}";
	private static final String INSTANCE_ERROR_EXCEPTION = "InstanceNotInDatabaseError: %s";
	
	private JdbcTemplate jdbcTemplate;
	private ComputerDB computerDB;
	
	@Autowired
	public CompanyDB(DataSource datasource, ComputerDB computerDB) {
		this.computerDB = computerDB;
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}
	
	
	private static synchronized void updateNumberOfComputer(int num) {
		numCompanies = num;
	}
	
	
	public int getNumCompanies() throws DAOException {
		try {			
			updateNumberOfComputer(jdbcTemplate.queryForObject(COUNT_NUMBER_OF, Integer.class));
		} catch (NestedRuntimeException e) {
			logger.error("NumberOfInstanceError: {}", e.getMessage(), e);
			throw new NumberOfInstanceException(String.format("NumberOfInstanceError: %s", e.getMessage()), e);
		}
		return numCompanies;
	}
	
	public Optional<Company> getCompanyByID(int id) {
			Object[] params = {id};
			return jdbcTemplate.queryForObject(SELECT_BY_ID, params, new CompanyRowMapper());
	}

	public List<Company> getCompanyList() throws DAOException {
		List<Company> companies = new ArrayList<>();
		try {
			companies =
					jdbcTemplate.query(SELECT_UNLIMITED_LIST, new CompanyRowMapper())
					.stream()
					.map(o -> o.get())
					.collect(Collectors.toList());
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error(INSTANCE_ERROR_LOGGER, e.getMessage(), e);
			throw new InstanceNotInDatabaseException(String.format(INSTANCE_ERROR_EXCEPTION, "computers not found."), e);
		}
		return companies;
	}
	
	public List<Company> getCompanyList(int limit, int offset) throws DAOException {
		List<Company> companies = new ArrayList<>();
		try {
			Object[] params = {limit, offset};
			companies =
					jdbcTemplate.query(SELECT_LIMITED_LIST, params, new CompanyRowMapper())
					.stream()
					.map(o -> o.get())
					.collect(Collectors.toList());
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error(INSTANCE_ERROR_LOGGER, e.getMessage(), e);
			throw new InstanceNotInDatabaseException(String.format(INSTANCE_ERROR_EXCEPTION, "computers not found"), e);
		}
		return companies;
	}
	

	private void create(Company cpy) throws DAOException { 
		// Future
	}

	
	private void update(String field, Company cmp) throws DAOException {
		// Future
	}
	 
//	public void delete(Company cpy) throws DAOException {
//		Connection conn = null;
//		PreparedStatement upd = null;
//		try {
//			conn = datasource.getConnection();
//			conn.setAutoCommit(false);
//			List<Integer> computers = computerDB.getAllComputersRelatedToCompanyWithID(cpy.getId());
//			ComputerDB.deleteTransactionalFromIDList(computers, conn);
//			upd = conn.prepareStatement(DELETE_REQUEST);
//			upd.setInt(1, cpy.getId());
//			upd.executeQuery();
//			conn.commit();
//		} catch (SQLException e1) {
//			logger.error("DeletionOfInstanceError: {}", e1.getMessage(), e1);
//			try {
//				if(conn != null)
//					conn.rollback();
//			}catch(SQLException e) {
//				logger.error("DeletionOfInstanceError: {}", e.getMessage(), e);
//				throw new ModifyDatabaseException("DeletionOfInstanceError: Rollback has failed.", e);
//			}
//		} catch(InstanceNotInDatabaseException e2) {
//			logger.error("DeletionOfInstanceError: {}", e2.getMessage(), e2);
//		} finally {
//			try {
//				if(upd != null)
//					upd.close();
//				if(conn != null)
//					conn.close();
//			} catch (SQLException e) {
//				logger.error("CloseConnectionException: {}", e.getMessage(), e);
//			}
//		}	
//	}
//	
//	
	
	
}

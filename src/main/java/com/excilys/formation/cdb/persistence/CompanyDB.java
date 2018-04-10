package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.exceptions.ModifyDatabaseException;
import com.excilys.formation.cdb.exceptions.NumberOfInstanceException;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;

public enum CompanyDB {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyDB.class);
	
	private static int numCompanies = -1;	
	private final static String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM company;";
	private final static String SELECT_ONE = "SELECT ID as caId, NAME as caName FROM company WHERE ID=?;";
	private final static String SELECT_UNLIMITED_LIST = "SELECT ID as caId, NAME as caName FROM company ca ORDER BY ID;";
	private final static String SELECT_LIMITED_LIST = "SELECT ID as caId, NAME as caName FROM company ORDER BY ID LIMIT ?, ?;";
	private final static String CREATE_REQUEST  = "INSERT INTO company NAME VALUES ?;";
	private final static String UPDTATE_REQUEST = "UPDATE company SET ?=? WHERE ID=?;";
	private final static String DELETE_REQUEST  = "DELETE FROM company WHERE ID=?";
	
	private CompanyDB() {
		
	}
	
	public int getNumCompanies() throws NumberOfInstanceException {
		
		Statement s;
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			s = conn.createStatement();
			ResultSet res = s.executeQuery(COUNT_NUMBER_OF);
			res.next();
			numCompanies = res.getInt("NUM");
		} catch (SQLException e) {
			logger.error("NumberOfInstanceException: {}", e.getMessage(), e);
			throw new NumberOfInstanceException("NumberOfInstanceException: " + e.getMessage(), e);
		}
		return numCompanies;
	}
	
	public Optional <Company> getCompanyByID(int id) throws InstanceNotInDatabaseException {
			PreparedStatement sel = null;
			ResultSet res = null;
			Company cpy = null;
			try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
				sel = (PreparedStatement) 
						conn.prepareStatement(SELECT_ONE);
				sel.setInt(1, id);
				res = sel.executeQuery();				
				res.next();
				cpy = CompanyMapper.map(res).get();
			} catch (SQLException e) {
				logger.error("InstanceNotInDatabaseError: {}", e.getMessage(), e);
				throw new InstanceNotInDatabaseException("NumberOfInstanceError: company not found", e);
			}
			return Optional.ofNullable(cpy);
	}
	
	public List<Company> getCompanyList() throws InstanceNotInDatabaseException {
		List<Company> companies = new ArrayList<Company>();
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			Statement s = conn.createStatement();
			ResultSet res = s.executeQuery(SELECT_UNLIMITED_LIST);
			
			while (res.next())
				companies.add(CompanyMapper.map(res).get());
			
		}catch (SQLException e) {
			logger.error("InstanceNotInDatabaseError: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: companies not found", e);
		}		
		return companies;
	}
	
	public List<Company> getCompanyList(int limit, int offset) throws InstanceNotInDatabaseException {
		List<Company> companies = new ArrayList<Company>();
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement(SELECT_LIMITED_LIST);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			ResultSet res = ps.executeQuery();
			
			while (res.next())
				companies.add(CompanyMapper.map(res).get());
			
		}catch (SQLException e) {
			logger.error("InstanceNotInDatabaseError: ", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: companies not found", e);
		}
	
		return companies;
	}
	

	private void create(Company cpy) throws NumberOfInstanceException, ModifyDatabaseException {
		PreparedStatement crt;
		ResultSet generatedKey = null;
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			crt = (PreparedStatement) conn.prepareStatement(CREATE_REQUEST);			
			crt.setString(2, cpy.getName());			
			crt.executeUpdate();
			
			generatedKey = crt.getGeneratedKeys();
			int id = generatedKey.getInt("ID");
			
			cpy.setId(id);
			logger.info("Created: " + cpy);		
		} catch (SQLException e) {
			logger.error("CreationOfInstanceError: ", e.getMessage(), e);
			throw new ModifyDatabaseException("CreationOfInstanceError: company couldn't be created", e);
		}
	}

	
	private void update(String field, Company cmp) throws ModifyDatabaseException {
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
			logger.error("UpdateOfInstanceError: ", e.getMessage(), e);
			throw new ModifyDatabaseException("UpdatenOfInstanceError: company couldn't be updated", e);
		}
		
	}
	
	private void delete(Company cmp) throws ModifyDatabaseException {
		try (Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();){
			PreparedStatement upd = (PreparedStatement) conn.prepareStatement(DELETE_REQUEST);
			upd.setInt(1, cmp.getId());
		} catch (SQLException e) {
			logger.error("DeletionOfInstanceError: ", e.getMessage(), e);
			throw new ModifyDatabaseException("DeletionOfInstanceError: company couldn't be deleted", e);
		}
	}
	
	
	
	
}

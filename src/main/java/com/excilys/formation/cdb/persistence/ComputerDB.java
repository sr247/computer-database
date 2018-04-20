package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.springframework.core.NestedRuntimeException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.exceptions.ModifyDatabaseException;
import com.excilys.formation.cdb.exceptions.NumberOfInstanceException;
import com.excilys.formation.cdb.mapper.row.ComputerRowMapper;
import com.excilys.formation.cdb.model.Computer;

@Repository
public class ComputerDB {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyDB.class);

	private JdbcTemplate jdbcTemplate;
	private DataSource datasource;

	// Auto Autowired
	public ComputerDB(DataSource datasource) {
		this.datasource = datasource;
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	// ça meriterait une petite enum pour retirer tout ça...
	private static int numComputers;
	private static final String COMPANY_COLUMN = "company.id as caId, company.name as caName ";
	private static final String LEFT_JOIN_ON_COMPANY = "LEFT JOIN company ON company.id = computer.company_id ";

	private static final String COUNT_NUMBER_OF = "SELECT COUNT(*) AS NUM FROM computer;";
	private static final String SELECT_BY_ID = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, company_id, "
			+ COMPANY_COLUMN + "FROM computer " + LEFT_JOIN_ON_COMPANY + "WHERE computer.id = ?;";
	private static final String SELECT_UNLIMITED_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, "
			+ COMPANY_COLUMN + "FROM computer " + LEFT_JOIN_ON_COMPANY + "ORDER BY computer.id;";
	private static final String SELECT_LIMITED_LIST = "SELECT computer.id as cmpId, computer.name as cmpName, introduced, discontinued, "
			+ COMPANY_COLUMN + "FROM computer " + LEFT_JOIN_ON_COMPANY + "ORDER BY computer.id LIMIT ?, ?;";
	private static final String CREATE_REQUEST = "INSERT INTO computer (NAME, INTRODUCED, DISCONTINUED, COMPANY_ID) VALUES (?, ?, ?, ?);";

	private static final String UPDTATE_REQUEST = "UPDATE computer SET NAME=?, INTRODUCED=?, DISCONTINUED=?, COMPANY_ID=? WHERE ID=?;";

	private static final String DELETE_REQUEST = "DELETE FROM computer WHERE ID=?;";

	private static final String SELECT_RELATED_TO_COMPANY = "SELECT computer.id FROM computer WHERE company_id=?;";

	private static final String DELETE_LOGGER = "DeletionOfInstanceError: {}";

	private static synchronized void updateNumberOfComputer(int num) {
		numComputers = num;
	}

	public int getNumComputers() throws DAOException {
		try {
			updateNumberOfComputer(jdbcTemplate.queryForObject(COUNT_NUMBER_OF, Integer.class));
		} catch (NestedRuntimeException e) {
			logger.error("NumberOfInstanceError: {}", e.getMessage(), e);
			throw new NumberOfInstanceException("NumberOfInstanceError: " + e.getMessage(), e);
		}
		return numComputers;
	}

	public Optional<Computer> getComputerByID(int id) {
		Object[] params = { id };
		return jdbcTemplate.queryForObject(SELECT_BY_ID, params, new ComputerRowMapper());
	}

	public List<Computer> getComputerList() throws DAOException {
		List<Computer> computers = new ArrayList<>();
		try {
			computers = jdbcTemplate.query(SELECT_UNLIMITED_LIST, new ComputerRowMapper()).stream().map(o -> o.get())
					.collect(Collectors.toList());
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error("InstanceNotInDatabaseError: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: computers not found");
		}
		return computers;
	}

	public List<Computer> getComputerList(int limit, int offset) throws DAOException {
		List<Computer> computers = new ArrayList<>();
		try {
			Object[] params = { limit, offset };
			computers = jdbcTemplate.query(SELECT_LIMITED_LIST, params, new ComputerRowMapper()).stream()
					.map(o -> o.get()).collect(Collectors.toList());
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error("InstanceNotInDatabaseError: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: computers not found");
		}
		return computers;
	}

	public List<Integer> getAllComputersRelatedToCompanyWithID(int id) throws DAOException {
		List<Integer> computersID = new ArrayList<>();
		try {
			Object[] params = { id };
			computersID = jdbcTemplate.queryForList(SELECT_RELATED_TO_COMPANY, params, Integer.class);
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error("InstanceNotInDatabaseError: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("InstanceNotInDatabaseError: computers not found");
		}
		return computersID;
	}

	public void create(Computer cmp) throws DAOException {
		try {
			Object[] params = { cmp.getName(), cmp.getIntroduced(), cmp.getDiscontinued(), cmp.getCompany().getId() };
			jdbcTemplate.update(CREATE_REQUEST, params);
			logger.info("Created: {}", cmp);
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error("CreationOfInstanceError: {}", e.getMessage(), e);
			throw new ModifyDatabaseException("CreationOfInstanceError: computer couldn't be created", e);
		}
	}

	public void update(Computer cmp) throws DAOException {
		try {
			Object[] params = { cmp.getName(), cmp.getIntroduced(), cmp.getDiscontinued(), cmp.getCompany(),
					cmp.getId() };
			jdbcTemplate.update(UPDTATE_REQUEST, params);
			logger.info("Updated: {}", cmp);
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error("UpdateOfInstanceError: {}", e.getMessage(), e);
			throw new ModifyDatabaseException("UpdateOfInstanceError: computer couldn't be updated", e);
		}
	}

	public void delete(Computer cmp) throws DAOException {
		try {
			Object[] params = { cmp.getId() };
			jdbcTemplate.update(DELETE_REQUEST, params);
			logger.info("Deleted: {}", cmp);
		} catch (NullPointerException | NestedRuntimeException e) {
			logger.error(DELETE_LOGGER, e.getMessage(), e);
			throw new ModifyDatabaseException("DeletionOfInstanceError: computer couldn't be deleted", e);
		}
	}

	public static void deleteTransactionalFromIDList(List<Integer> idList, Connection conn) throws DAOException {
		try (PreparedStatement ps = conn.prepareStatement(DELETE_REQUEST)) {
			for (Integer id : idList) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error(DELETE_LOGGER, e);
			throw new ModifyDatabaseException("Couldn't delete the provided computers' list.", e);
		}
	}

	public void deleteFromIDList(List<Integer> idList) throws DAOException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = datasource.getConnection();
			ps = conn.prepareStatement(DELETE_REQUEST);
			conn.setAutoCommit(false);
			for (Integer id : idList) {
				ps.setInt(1, id);
				ps.executeUpdate();
				logger.info("Delete: {}", id);
			}
			conn.commit();
		} catch (SQLException e1) {
			logger.error(DELETE_LOGGER, e1.getMessage(), e1);
			try {
				if (conn != null)
					conn.rollback();
			} catch (SQLException e) {
				logger.error(DELETE_LOGGER, e.getMessage(), e);
				throw new ModifyDatabaseException("DeletionOfInstanceError: Rolling back has failed.", e);
			}
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				logger.error("CloseConnectionException: {}", e.getMessage(), e);
			}
		}
	}
}

package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * Classe Mapper pour mapper les ordinateurs.
 * @author sr247
 */
public class ComputerMapper {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);
	
	private ComputerMapper() {}
	
	public Optional<Computer> map(ResultSet res) throws InstanceNotInDatabaseException {
		Computer cmp = null;		
		try {
			if(!res.equals(null)) {
				int id = res.getInt("cmpId");
				String name = res.getString("cmpName");
				Date intro = res.getDate("INTRODUCED");
				Date discon = res.getDate("DISCONTINUED");
				LocalDate introduced = intro == null ? null : intro.toLocalDate();
				LocalDate discontinued = discon == null ? null : discon.toLocalDate();
				Company company = CompanyMapper.map(res).get();
				cmp = new Computer(id, name, introduced, discontinued, company);
				
			}
		}catch(SQLException e) {
			logger.error("Error in database: {}", e.getMessage(), e);
			throw new InstanceNotInDatabaseException("Error in database:", e);
		}	
		return Optional.ofNullable(cmp);
	}
	
}

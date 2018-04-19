package com.excilys.formation.cdb.mapper.row;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public class ComputerRowMapper implements RowMapper<Optional<Computer>> {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerRowMapper.class);
		
	@Override
	public Optional<Computer> mapRow(ResultSet res, int id) throws SQLException {
		Computer computer = null;		
		try {
				int idComputer = res.getInt("cmpId");
				String name = res.getString("cmpName");
				Date intro = res.getDate("INTRODUCED");
				Date discon = res.getDate("DISCONTINUED");
				LocalDate introduced = intro == null ? null : intro.toLocalDate();
				LocalDate discontinued = discon == null ? null : discon.toLocalDate();
				Optional<Company> company = new CompanyMapper().map(res);
				if(company.isPresent())
					computer = new Computer(idComputer, name, introduced, discontinued, company.get());
		}catch(SQLException e) {
			logger.error("ComputerRowMapperException: {}", e.getMessage(), e);
			throw e;
		}	
		return Optional.ofNullable(computer);
	}


}

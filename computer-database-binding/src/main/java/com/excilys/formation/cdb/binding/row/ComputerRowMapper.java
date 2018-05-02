package com.excilys.formation.cdb.binding.row;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.cdb.binding.CompanyMapper;
import com.excilys.formation.cdb.core.Company;
import com.excilys.formation.cdb.core.Computer;

public class ComputerRowMapper implements RowMapper<Optional<Computer>> {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerRowMapper.class);
		
	@Override
	public Optional<Computer> mapRow(ResultSet res, int id) throws SQLException {
		Computer computer = null;		
		try {
			int idComputer = res.getInt("id");
			String name = res.getString("name");
			Date intro = res.getDate("introduced");
			Date discon = res.getDate("discontinued");
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

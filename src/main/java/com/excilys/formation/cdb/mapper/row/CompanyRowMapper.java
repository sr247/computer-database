package com.excilys.formation.cdb.mapper.row;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.cdb.model.Company;

public class CompanyRowMapper implements RowMapper<Optional<Company>> {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerRowMapper.class);
		
	@Override
	public Optional<Company> mapRow(ResultSet res, int id) throws SQLException {
		Company company = null;		
		try {
			String name = res.getString("caName");
			company = new Company(id, name);
		} catch (SQLException e) {
			logger.error("CompanyRowMapperException: {}", e.getMessage(), e);
			throw e;
		}
		return Optional.ofNullable(company);
	}

}

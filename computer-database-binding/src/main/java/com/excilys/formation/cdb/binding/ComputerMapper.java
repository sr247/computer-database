package com.excilys.formation.cdb.binding;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.core.Company;
import com.excilys.formation.cdb.core.Computer;

/**
 * Classe Mapper pour mapper les ordinateurs.
 * 
 * @author sr247
 */
@Component
public class ComputerMapper {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);

	@Autowired
	private CompanyMapper companyMapper;

	public Optional<Computer> map(ResultSet res) throws SQLException {
		Computer cmp = null;
		try {
			if (!res.equals(null)) {
				long id = res.getLong("cmpId");
				String name = res.getString("cmpName");
				Date intro = res.getDate("INTRODUCED");
				Date discon = res.getDate("DISCONTINUED");
				LocalDate introduced = intro == null ? null : intro.toLocalDate();
				LocalDate discontinued = discon == null ? null : discon.toLocalDate();
				Optional<Company> company = companyMapper.map(res);
				if (company.isPresent())
					cmp = new Computer(id, name, introduced, discontinued, company.get());
			}
		} catch (SQLException e) {
			logger.error("Error in database: {}", e.getMessage(), e);
			throw e;
		}
		return Optional.ofNullable(cmp);
	}	
}

package com.excilys.formation.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;

public enum ComputerMapper {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);
	
	private ComputerMapper() {
		
	}
	
	public static Optional<Computer> map(ResultSet res) {
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
		}catch(Exception e) {
			logger.warn(e.getMessage());
		}	
		return Optional.ofNullable(cmp);
	}
	
}

package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;

public enum CompanyMapper {

	INSTANCE;
	
	private CompanyMapper() {
		
	}
	
	public static Optional<Company> map(ResultSet res) {
		Company cpn = null;		
		try {
			if(!res.equals(null)) {
				int id = res.getInt("ID");
				String name = res.getString("NAME");
				cpn = new Company(id, name);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(cpn);
	}
}

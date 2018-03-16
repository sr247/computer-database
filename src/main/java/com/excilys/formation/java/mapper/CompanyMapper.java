package com.excilys.formation.java.mapper;

import java.sql.ResultSet;

import com.excilys.formation.java.model.Company;

public enum CompanyMapper {

	INSTANCE;
	
	private CompanyMapper() {
		
	}
	
	public static Company map(ResultSet res) {
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
		return cpn;
	}
}

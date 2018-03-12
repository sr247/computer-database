package com.excilys.formation.java.mapper;

import java.sql.Date;
import java.sql.ResultSet;

import com.excilys.formation.java.model.Company;

public class CompanyMP {

	
	public CompanyMP() {
		
	}
	
	public static Company map(ResultSet res) {
		
		
		int id = -1;
		String name = "";

		/*
		try {
			id = res.getInt("ID");
			name = res.getString("NAME");
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}*/
		
		id = res.getInt("ID");
		name = res.getString("NAME");
		return new Company(id, name);
	}
}

}

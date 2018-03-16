package com.excilys.formation.java.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.excilys.formation.java.model.Computer;

public enum ComputerMapper {
	
	INSTANCE;
	
	private ComputerMapper() {
		
	}
	
	public static Computer map(ResultSet res) {
		Computer cmp = null;		
		try {
			if(!res.equals(null)) {
				int id = res.getInt("ID");
				String name = res.getString("NAME");
				Date intro = res.getDate("INTRODUCED");
				Date discon = res.getDate("DISCONTINUED");
				LocalDate introduced = intro == null ? null : intro.toLocalDate();
				LocalDate discontinued = discon == null ? null : discon.toLocalDate();
				int company_id = res.getInt("COMPANY_ID");
				cmp = new Computer(id, name, introduced, discontinued, company_id);
				
			}
		}catch(Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}	
		return cmp;
	}
	
}

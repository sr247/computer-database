package com.excilys.formation.java.mapper;

import java.sql.Date;
import java.sql.ResultSet;

import com.excilys.formation.java.model.Computer;

public class ComputerMP {
	

	
	public ComputerMP() {
		
	}
	
	
	public static Computer map(ResultSet res) {
		
		
		int id = -1;
		String name = "";
		Date introduced = new Date(0);
		Date discontinued = new Date(0);
		int company_id  = -1; 
		
		try {
			id = res.getInt("ID");
			name = res.getString("NAME");
			introduced = res.getDate("INTRODUCED");
			discontinued = res.getDate("DISCONTINUED");
			company_id = res.getInt("COMPANY_ID");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}
		
		return new Computer(id, name, introduced, discontinued, company_id);
	}
}
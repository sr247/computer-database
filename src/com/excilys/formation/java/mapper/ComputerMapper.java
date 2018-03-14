package com.excilys.formation.java.mapper;

import java.sql.Date;
import java.sql.ResultSet;

import com.excilys.formation.java.model.Computer;

public class ComputerMapper {
	private static ComputerMapper _interface = null;
	
	private ComputerMapper() {
		
	}
	
	public static ComputerMapper getInterface() {
		if(_interface == null) {
			_interface = new ComputerMapper();
		}
		return _interface;
	}

	
	public static Computer map(ResultSet res) {
		Computer cmp = null;		
		try {
			if(!res.equals(null)) {
				int id = res.getInt("ID");
				String name = res.getString("NAME");
				Date introduced = res.getDate("INTRODUCED");
				Date discontinued = res.getDate("DISCONTINUED");
				int company_id = res.getInt("COMPANY_ID");
				cmp = new Computer(id, name, introduced, discontinued, company_id);
			}			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}		
		return cmp;
	}
	
}

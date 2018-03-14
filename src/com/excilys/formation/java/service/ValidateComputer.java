package com.excilys.formation.java.service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.java.exceptions.IncorrectFieldException;
import com.excilys.formation.java.exceptions.InstanceNotFoundException;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.persistence.CompanyDB;
import com.mysql.jdbc.PreparedStatement;

// Cette classe devient un singleton
public class ValidateComputer {
	private static ValidateComputer _interface = null;
	
	
	private ValidateComputer() {
		
	}
	
	public static ValidateComputer getinterface() {
		if(_interface == null) {
			_interface = new ValidateComputer();
		}
		return _interface;
	}
	
	public String checkName(String nm) {
		// Ici on check une belle regex pour elever les symboles bizarre, ou pas
		// Future
		return nm;
	}
	
	
	public Date checkDate(String dt) throws IncorrectFieldException {
		Date date = null;
		try {
			if(!("".equals(dt)) 
					&& !("null".equals(dt))) {
				date = Date.valueOf(dt);
			}					
		} catch(IllegalArgumentException e) {
			System.err.println("Invalid format date");
			throw new IncorrectFieldException();
		}
		return date;
	}
	

	public int checkForeignKey(String fk) throws InstanceNotFoundException {
		CompanyDB cpyDB = CompanyDB.getInterface();
		Company cpy = cpyDB.getCompanyByID(Integer.valueOf(fk));
		
		if(cpy == null) {
			throw new InstanceNotFoundException();
		}		
		return cpy.getId();
	}

}

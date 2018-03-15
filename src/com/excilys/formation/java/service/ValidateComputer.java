package com.excilys.formation.java.service;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
		SimpleDateFormat sdfJAVA = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tmp = new java.util.Date(0);		
		try {
			if(!("".equals(dt)) 
					&& !("null".equals(dt))) {
				tmp = sdfJAVA.parse(dt);
				String dtt = sdfSQL.format(tmp);
				date = Date.valueOf(dtt);
			}					
		} catch(IllegalArgumentException e) {
			System.err.println("Invalid format date");
			throw new IncorrectFieldException();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.err.println("Invalid format date");
			e.printStackTrace();
		}
		return date;
	}
	

	public int checkForeignKey(String fk) throws InstanceNotFoundException {
		CompanyDB cpyDB = CompanyDB.getInterface();
		Company cpy = cpyDB.getCompanyByID(Integer.valueOf(fk));
		
		if(cpy == null) {
			throw new InstanceNotFoundException();
		}
		System.err.println(cpy.getId());
		return cpy.getId();
	}

}

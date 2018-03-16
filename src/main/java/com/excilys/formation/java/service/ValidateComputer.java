package com.excilys.formation.java.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.excilys.formation.java.exceptions.IncorrectFieldException;
import com.excilys.formation.java.exceptions.InstanceNotFoundException;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.persistence.CompanyDB;


// Cette classe devient un singleton
public enum ValidateComputer {
	INSTANCE;	
	
	private ValidateComputer() {
		
	}
	
	public String checkName(String nm) {
		// Ici on check une belle regex pour elever les symboles bizarre, ou pas
		// Future
		return nm;
	}
	
	
	public LocalDate checkDate(String dt) throws IncorrectFieldException {
		LocalDate date = null;
		try {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
			date = LocalDate.parse(dt, fmt);
			return date;
		}catch (DateTimeParseException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IncorrectFieldException();
		}

	}
	

	public int checkForeignKey(String fk) throws InstanceNotFoundException {
		CompanyDB cpyDB = CompanyDB.INSTANTCE;
		Company cpy = cpyDB.getCompanyByID(Integer.valueOf(fk));
		
		if(cpy == null) {
			throw new InstanceNotFoundException();
		}
		System.err.println(cpy.getId());
		return cpy.getId();
	}

}

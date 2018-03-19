package com.excilys.formation.cdb.service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.InstanceNotFoundException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;


// Cette classe devient un singleton
public enum ValidateComputer {
	INSTANCE;	
	
	private ValidateComputer() {
		
	}
	
	
	public void validate(Computer cmp) throws IncorrectFieldException, InstanceNotFoundException {
		checkName(cmp.getName());
		checkDate(cmp.getIntroduced(), cmp.getDiscontinued());
		checkCompany(cmp.getCompany());
	}
	
	public void checkName(String name) {
		
	}
	
	public void checkDate(LocalDate d1, LocalDate d2) throws IncorrectFieldException {
		if(d1.isAfter(LocalDate.now())) {
			throw new IncorrectFieldException();
		}
		if(d1.isAfter(d2)) {
			throw new IncorrectFieldException();
		}
	}
	

	public void checkCompany(Company cpy) throws InstanceNotFoundException {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		if(!cpyDB.getCompanyByID(cpy.getId()).isPresent()) {		
			throw new InstanceNotFoundException();
		}
	}

}

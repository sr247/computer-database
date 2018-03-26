package com.excilys.formation.cdb.service;


import java.time.LocalDate;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;


// Cette classe devient un singleton
public enum ValidateComputer {
	INSTANCE;	
	
	private ValidateComputer() {
		
	}	
	
	public void validate(Computer cmp) throws IncorrectFieldException, InstanceNotInDatabaseException {
		checkName(cmp.getName());
		checkDate(cmp.getIntroduced(), cmp.getDiscontinued());
		checkCompany(cmp.getCompany());
	}
	
	public void checkName(String name) {
		// Eventuellement tester les noms trop bizarres
	}
	
	public void checkDate(LocalDate d1, LocalDate d2) throws IncorrectFieldException {
		if(d1.isAfter(LocalDate.now())) {
			throw new IncorrectFieldException("Error: Computer can't have a introduced date in the future.");
		}
		if(d1.isAfter(d2)) {
			throw new IncorrectFieldException("Error: Computer can't have a introduced date after the discontinued date.");
		}
	}
	

	public void checkCompany(Company cpy) throws InstanceNotInDatabaseException {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		if(!cpyDB.getCompanyByID(cpy.getId()).isPresent()) {		
			throw new InstanceNotInDatabaseException("");
		}
	}

}

package com.excilys.formation.cdb.service;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;

@Service
public class ValidateComputer {
	
	@Autowired
	private CompanyDB companyDB;
	
	private ValidateComputer() {}	
	
	public void validate(Computer cmp) throws IncorrectFieldException, InstanceNotInDatabaseException {
		checkName(Optional.ofNullable(cmp.getName()));
		checkDate(Optional.ofNullable(cmp.getIntroduced()), Optional.ofNullable(cmp.getDiscontinued()) );
		checkCompany(Optional.ofNullable(cmp.getCompany()));
	}
	
	private void checkName(Optional<String> name) throws IncorrectFieldException {
		if(!name.isPresent() || name.get().equals("")) {
			throw new IncorrectFieldException("ValidateError: Computer must have a name.");
		}
	}
	
	
	private void checkDate(Optional<LocalDate> d1, Optional<LocalDate> d2) throws IncorrectFieldException {
		if(d1.isPresent() && d1.get().isAfter(LocalDate.now())) {
				throw new IncorrectFieldException("ValidateError: Computer can't have a introduced date in the future.");
		}
		if(d1.isPresent() && d2.isPresent() && d1.get().isAfter(d2.get())) {
				throw new IncorrectFieldException("ValidateError: Computer can't have a introduced date after the discontinued date.");
		}
	}
	

	private void checkCompany(Optional<Company> cpy) throws InstanceNotInDatabaseException {
		if(cpy.isPresent()) {
			if(!companyDB.getCompanyByID(cpy.get().getId()).isPresent()) {		
				throw new InstanceNotInDatabaseException("ValidateError: Company not in database");
			}			
		}else {
			throw new InstanceNotInDatabaseException("ValidateError: Empty company field.");
		}
	}

}

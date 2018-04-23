package com.excilys.formation.cdb.service;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.InexistentEntityException;
import com.excilys.formation.cdb.exceptions.NullValueException;
import com.excilys.formation.cdb.exceptions.ValidatorException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;

@Component
public class ValidateComputer {
	
	@Autowired
	private CompanyDB companyDB;
	
	private ValidateComputer() {}	
	
	public void validate(Computer computer) throws ValidatorException {
		checkName(Optional.ofNullable(computer.getName()));
		checkDate(Optional.ofNullable(computer.getIntroduced()), Optional.ofNullable(computer.getDiscontinued()) );
		checkCompany(Optional.ofNullable(computer.getCompany()));
	}
	
	public void checkIsNull(Optional<Computer> computer) throws ValidatorException {
		if(!computer.isPresent()) {
			throw new NullValueException("ValidatorException: Computer must have a name.");
		}
	}
	
	private void checkName(Optional<String> name) throws ValidatorException {
		if(!name.isPresent() || name.get().equals("")) {
			throw new IncorrectFieldException("ValidatorException: Computer must have a name.");
		}
	}	
	
	private void checkDate(Optional<LocalDate> d1, Optional<LocalDate> d2) throws ValidatorException {
		if(d1.isPresent() && d1.get().isAfter(LocalDate.now())) {
				throw new IncorrectFieldException("ValidatorException: Computer can't have a introduced date in the future.");
		}
		if(d1.isPresent() && d2.isPresent() && d1.get().isAfter(d2.get())) {
				throw new IncorrectFieldException("ValidatorException: Computer can't have a introduced date after the discontinued date.");
		}
	}
	
	private void checkCompany(Optional<Company> company) throws ValidatorException {
		if(company.isPresent()) {			
			if(!companyDB.getCompanyByID(company.get().getId()).isPresent()) {		
				throw new InexistentEntityException("ValidatorException: Company not in database");
			}
		}else {
			throw new InexistentEntityException("ValidatorException: Empty company field.");
		}
	}

}

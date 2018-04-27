package com.excilys.formation.cdb.service.validator;


import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.core.Company;
import com.excilys.formation.cdb.core.Computer;
import com.excilys.formation.cdb.core.entity.CompanyEntity;
import com.excilys.formation.cdb.core.entity.ComputerEntity;
import com.excilys.formation.cdb.persistence.repositories.CompanyRepository;

@Component
public class ValidateComputer {
	
	@Autowired
	private CompanyRepository companyREP;
	
	public void validate(ComputerEntity computer) throws ValidatorException {
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
	
	private void checkCompany(Optional<CompanyEntity> company) throws ValidatorException {
		if(company.isPresent()) {			
			if(!companyREP.existsById(company.get().getId())) {		
				throw new InexistentEntityException("ValidatorException: Company not in database");
			}
		}else {
			throw new InexistentEntityException("ValidatorException: Empty company field.");
		}
	}

}

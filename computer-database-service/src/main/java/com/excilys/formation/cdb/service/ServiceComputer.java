package com.excilys.formation.cdb.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.core.ComputerDTO;
import com.excilys.formation.cdb.core.entity.CompanyEntity;
import com.excilys.formation.cdb.core.entity.ComputerEntity;
import com.excilys.formation.cdb.persistence.repositories.CompanyRepository;
import com.excilys.formation.cdb.persistence.repositories.ComputerRepository;
import com.excilys.formation.cdb.service.validator.ValidateComputer;
import com.excilys.formation.cdb.service.validator.ValidatorException;

@Service
@EnableTransactionManagement
public class ServiceComputer {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceComputer.class);
	private static final String SERVICE_COMPUTER_EXCEPTION = "ServiceComputer: %s";
	private static final String SERVICE_COMPUTER_LOGGER = "ServiceComputer: {}";
	
	private ComputerRepository computerREP;
	private CompanyRepository companyREP;
	private ValidateComputer validateComputer;
	
	@Autowired
	public ServiceComputer(ComputerRepository computerREP, CompanyRepository companyREP, ValidateComputer validateComputer) {
		this.validateComputer = validateComputer;
		this.computerREP = computerREP;
		this.companyREP = companyREP;
	}
	
	public Long getNumberOf() {
		return computerREP.count();
	}
	
	public ComputerEntity getComputer(Long id) {
		Optional<ComputerEntity> computer;
		computer = computerREP.findById(id);
		return computer.isPresent() ? computer.get() : null;
	}
	
	public Page<ComputerEntity> getList(int page, int size) {
		Page<ComputerEntity> computersPage;
		String column = "id";
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, column));				
		PageRequest pageable = PageRequest.of(page, size, sort);
		computersPage = computerREP.findAll(pageable);				
		return computersPage;
	}
	
	public Page<ComputerEntity> getAllList() {
		Page<ComputerEntity> computersPage;
		String column = "id";
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, column));				
		PageRequest pageable = PageRequest.of(0, (int)computerREP.count(), sort);
		computersPage = computerREP.findAll(pageable);				
		return computersPage;
	}
		
	public void createComputer(ComputerDTO cmp) throws ServiceManagerException {
		try {
			long id = cmp.getCompany().getId();
			Optional<CompanyEntity> opt = companyREP.findById(id);
			
			CompanyEntity company = opt.isPresent() ? opt.get() : null;
			LocalDate introduced = !cmp.getIntroduced().equals("") ? LocalDate.parse(cmp.getIntroduced()) : null;
			LocalDate discontinued = !cmp.getDiscontinued().equals("") ? LocalDate.parse(cmp.getDiscontinued()): null;
			ComputerEntity computer = new ComputerEntity(cmp.getName(), introduced, discontinued, company);
			validateComputer.validate(computer);
			
			computerREP.save(computer);
		} catch (ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public void updateComputer(ComputerDTO cmp) throws ServiceManagerException {
		try {
			long id = cmp.getCompany().getId();
			Optional<CompanyEntity> opt = companyREP.findById(id);
			
			CompanyEntity company = opt.isPresent() ? opt.get() : null;
			LocalDate introduced = !cmp.getIntroduced().equals("") ? LocalDate.parse(cmp.getIntroduced()) : null;
			LocalDate discontinued = !cmp.getDiscontinued().equals("") ? LocalDate.parse(cmp.getDiscontinued()): null;
			ComputerEntity computer = new ComputerEntity(cmp.getName(), introduced, discontinued, company);
			validateComputer.validate(computer);
			computerREP.save(computer);
		} catch (ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public void deleteComputer(Long id) {
		if(computerREP.existsById(id)) {
			computerREP.deleteById(id); 				
		}
	}
	
	
	public void deleteComputer(ComputerDTO computer) {
		if(computerREP.existsById(computer.getId())) {
			computerREP.deleteById(computer.getId()); 				
		}
	}
	
	@Transactional
	public void deleteComputerFromIDList(List<Long> idList) {
		Iterable<ComputerEntity> entityList = computerREP.findAllById(idList);
		computerREP.deleteAll(entityList);
	}
	
}
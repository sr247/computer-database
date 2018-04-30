package com.excilys.formation.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.core.ComputerDTO;
import com.excilys.formation.cdb.core.entity.CompanyEntity;
import com.excilys.formation.cdb.core.entity.ComputerEntity;
import com.excilys.formation.cdb.persistence.repositories.CompanyRepository;
import com.excilys.formation.cdb.persistence.repositories.ComputerRepository;
import com.excilys.formation.cdb.service.validator.ValidateComputer;
import com.excilys.formation.cdb.service.validator.ValidatorException;

@Service
public class ServiceComputer {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceComputer.class);
	private static final String SERVICE_COMPUTER_EXCEPTION = "ServiceComputer: %s";
	private static final String SERVICE_COMPUTER_LOGGER = "ServiceComputer: {}";
	
	private ComputerRepository computerREP;
	private CompanyRepository companyREP;
	private ValidateComputer validateComputer;
	
	@Autowired
	public ServiceComputer(ComputerRepository computerREP, ValidateComputer validateComputer) {
		this.computerREP = computerREP;
		this.validateComputer = validateComputer;
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
//		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
		PageRequest pageable = PageRequest.of(page, size);
		computersPage = computerREP.findAll(pageable);				
		return computersPage;
	}
	
	public Page<ComputerEntity> getAllList() {
		Page<ComputerEntity> computersPage;
//		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
		PageRequest pageable = PageRequest.of(0, (int)computerREP.count());
		computersPage = computerREP.findAll(pageable);				
		return computersPage;
	}
		
	public void createComputer(ComputerDTO cmp) throws ServiceManagerException {
		try {
			CompanyEntity company = companyREP.findByName(cmp.getCompanyName());
			ComputerEntity computer = new ComputerEntity(cmp.getName(), cmp.getIntroduced(), cmp.getDiscontinued(), company);
			validateComputer.validate(computer);
			computerREP.save(computer);
		} catch (ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public void updateComputer(ComputerEntity cmp) throws ServiceManagerException {
		try {
			validateComputer.validate(cmp);	
			computerREP.save(cmp);
		} catch (ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
		computerREP.save(cmp);
	}
	
	public void deleteComputer(Long id) throws ServiceManagerException {
		if(computerREP.existsById(id)) {
			computerREP.deleteById(id); 				
		}
	}
	
	public void deleteComputerFromIDList(List<Long> idList) throws ServiceManagerException{
		Iterable<ComputerEntity> entityList = computerREP.findAllById(idList);
		computerREP.deleteAll(entityList);
	}
	
}
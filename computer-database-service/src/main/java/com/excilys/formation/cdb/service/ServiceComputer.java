package com.excilys.formation.cdb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.core.entity.ComputerEntity;
import com.excilys.formation.cdb.persistence.repositories.ComputerRepository;
import com.excilys.formation.cdb.service.validator.ValidateComputer;

@Service
public class ServiceComputer {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceComputer.class);
	private static final String SERVICE_COMPUTER_EXCEPTION = "ServiceComputer: %s";
	private static final String SERVICE_COMPUTER_LOGGER = "ServiceComputer: {}";
	
	private ComputerRepository computerREP;
	private ValidateComputer validateComputer;
	
	@Autowired
	public ServiceComputer(ComputerRepository computerREP, ValidateComputer validateComputer) {
		this.computerREP = computerREP;
		this.validateComputer = validateComputer;
	}
	
	public Long getNumberOf() {
		return computerREP.count();
	}
	
	public ComputerEntity getComputer(Long id) throws ServiceManagerException {
		Optional<ComputerEntity> computer;
		computer = computerREP.findById(id);
		return computer.isPresent() ? computer.get() : null;
	}
	
	public Iterable<ComputerEntity> getAllList() throws ServiceManagerException {
		return computerREP.findAll();
	}
	
	public Page<ComputerEntity> getList(int page, int size) throws ServiceManagerException {
		Page<ComputerEntity> computersPage;
		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
		PageRequest pageable = PageRequest.of(page, size, sort);
		computersPage = computerREP.findAllWithStride(pageable);				

		return computersPage;
//		logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
//		throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
	}
		
//	public void createComputer(Computer cmp) throws ServiceManagerException {
//		try {
//			validateComputer.validate(cmp);		
//			computerDB.create(cmp);
//		} catch (DAOException | ValidatorException e) {
//			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
//			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
//		}
//	}
//	
//	public void updateComputer(Computer cmp) throws ServiceManagerException {
//		try {
//			validateComputer.validate(cmp);	
//			computerDB.update(cmp);
//		} catch (DAOException | ValidatorException e) {
//			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
//			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
//		}
//	}
//	
//	public void deleteComputer(int id) throws ServiceManagerException {
//		Optional<Computer> computer;
//		try {
//			computer = computerDB.getComputerByID(id);
//			validateComputer.checkIsNull(computer);
//			// Method orElseThrow de optional plus intéressante
//			computerDB.delete(computer.get()); 
//		} catch (DAOException | ValidatorException e) {
//			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
//			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
//		}
//	}
//	
//	public void deleteComputerFromIDList(List<Integer> idList) throws ServiceManagerException{
//		try {
//			computerDB.deleteFromIDList(idList);
//		} catch (DAOException e) {
//			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
//			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
//		}
//	}

	
}

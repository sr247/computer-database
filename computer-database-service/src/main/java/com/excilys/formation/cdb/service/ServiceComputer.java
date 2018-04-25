package com.excilys.formation.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.persistence.DAOException;
import com.excilys.formation.cdb.service.validator.ValidateComputer;
import com.excilys.formation.cdb.service.validator.ValidatorException;
import com.excilys.formation.cdb.binding.ComputerMapperDTO;
import com.excilys.formation.cdb.core.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;

@Service
public class ServiceComputer {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceComputer.class);
	private static final String SERVICE_COMPUTER_EXCEPTION = "ServiceComputer: %s";
	private static final String SERVICE_COMPUTER_LOGGER = "ServiceComputer: {}";
	
	private ComputerDB computerDB;
	private ValidateComputer validateComputer;	
	
	@Autowired
	public ServiceComputer(ComputerDB computerDB, ValidateComputer validateComputer) {
		this.computerDB = computerDB;
		this.validateComputer = validateComputer;
	}
	
	public int getNumberOf() throws ServiceManagerException{
		try {
			return computerDB.getNumComputers();
		}catch(DAOException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public Computer getComputer(int id) throws ServiceManagerException {
		Optional<Computer> computer = Optional.empty();
		try {
			computer = computerDB.getComputerByID(id);
			validateComputer.checkIsNull(computer);
		} catch (ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
		return computer.get();
	}
	
	public List<Computer> getAllList() throws ServiceManagerException {
		try {
			return computerDB.getComputerList();
		} catch (DAOException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public List<Computer> getList(int limit, int offset) throws ServiceManagerException {
		try {
			List<Computer> computerList = new ArrayList<>();			
			if(limit == 0 && offset == 0) {
				computerList =  computerDB.getComputerList();
			} else {
				computerList = computerDB.getComputerList(limit, offset);
			}
			return computerList;
			
		} catch (DAOException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
		
	public void createComputer(Computer cmp) throws ServiceManagerException {
		try {
			validateComputer.validate(cmp);		
			computerDB.create(cmp);
		} catch (DAOException | ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public void updateComputer(Computer cmp) throws ServiceManagerException {
		try {
			validateComputer.validate(cmp);	
			computerDB.update(cmp);
		} catch (DAOException | ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public void deleteComputer(int id) throws ServiceManagerException {
		Optional<Computer> computer;
		try {
			computer = computerDB.getComputerByID(id);
			validateComputer.checkIsNull(computer);
			// Method orElseThrow de optional plus intéressante
			computerDB.delete(computer.get()); 
		} catch (DAOException | ValidatorException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public void deleteComputerFromIDList(List<Integer> idList) throws ServiceManagerException{
		try {
			computerDB.deleteFromIDList(idList);
		} catch (DAOException e) {
			logger.error(SERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPUTER_EXCEPTION, e.getMessage()), e);
		}
	}

	
}

package com.excilys.formation.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.exceptions.ValidatorException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;


public enum ServiceComputer {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceComputer.class);
	private static final String WEBSERVICE_COMPUTER_EXCEPTION = "WebServiceComputer: %s, from %s";
	private static final String WEBSERVICE_COMPUTER_LOGGER = "WebServiceComputer: {}";
	
	@Autowired
	private ComputerDB computerDB;
	@Autowired
	private ValidateComputer validateComputer;
	
	private ServiceComputer() {}	
		
	public int getNumberOf() throws ServiceManagerException{
		try {
			return computerDB.getNumComputers();
		}catch(DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public Computer getComputer(int id) throws ServiceManagerException {
		try {
			return computerDB.getComputerByID(id);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public List<Computer> getAllList() throws ServiceManagerException {
		try {
			return computerDB.getComputerList();
		} catch (DAOException e) {

			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public List<Computer> getList(int limit, int offset) throws ServiceManagerException {
		try {
			if(limit == 0 && offset == 0) {
				return computerDB.getComputerList();
			}
			return computerDB.getComputerList(limit, offset);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	
	public void createComputer(Computer cmp) throws ServiceManagerException {
		try {
			validateComputer.validate(cmp);		
			computerDB.create(cmp);
		} catch (DAOException | ValidatorException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public void updateComputer(Computer cmp) throws ServiceManagerException {
		try {
			validateComputer.validate(cmp);	
			computerDB.update(cmp);
		} catch (DAOException | ValidatorException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public void deleteComputer(int id) throws ServiceManagerException {
		Computer cmp;
		try {
			cmp = computerDB.getComputerByID(id);
			computerDB.delete(cmp);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public void deleteComputerFromIDList(List<Integer> idList) throws ServiceManagerException{
		try {
			computerDB.deleteFromIDList(idList);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}

	
}

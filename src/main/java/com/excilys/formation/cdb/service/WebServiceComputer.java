package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.exceptions.ValidatorException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;


public enum WebServiceComputer {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WebServiceComputer.class);
	private static final String WEBSERVICE_COMPUTER_EXCEPTION = "WebServiceComputer: %s, from %s";
	private static final String WEBSERVICE_COMPUTER_LOGGER = "WebServiceComputer: {}";
	
	private WebServiceComputer() {
	
	}	
		
	public int getNumberOf() throws ServiceManagerException{
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		try {
			return cmpDB.getNumComputers();
		}catch(DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public Computer getComputer(int id) throws ServiceManagerException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		try {
			return cmpDB.getComputerByID(id);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException("WebServiceComputer: " + e.getMessage(), e);
		}
	}
	
	public List<Computer> getAllList() throws ServiceManagerException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		try {
			return cmpDB.getComputerList();
		} catch (DAOException e) {

			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException("WebServiceComputer: " + e.getMessage(), e);
		}
	}
	
	public List<Computer> getList(int limit, int offset) throws ServiceManagerException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		try {
			if(limit == 0 && offset == 0) {
				return cmpDB.getComputerList();
			}
			return cmpDB.getComputerList(limit, offset);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException("WebServiceComputer: " + e.getMessage(), e);
		}
	}
	
	
	public void createComputer(Computer cmp) throws ServiceManagerException {
		
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;		
		try {
			vcmp.validate(cmp);		
			cmpDB.create(cmp);
		} catch (DAOException | ValidatorException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public void updateComputer(Computer cmp) throws ServiceManagerException {
		
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		try {
			vcmp.validate(cmp);	
			cmpDB.update(cmp);
		} catch (DAOException | ValidatorException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public void deleteComputer(int id) throws ServiceManagerException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		Computer cmp;
		try {
			cmp = cmpDB.getComputerByID(id);
			cmpDB.delete(cmp);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}
	
	public void deleteComputerFromIDList(List<Integer> idList) throws ServiceManagerException{
		ComputerDB cmpDB = ComputerDB.INSTANCE;		
		try {
			cmpDB.deleteFromIDList(idList);
		} catch (DAOException e) {
			logger.error(WEBSERVICE_COMPUTER_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
			throw new ServiceManagerException(String.format(WEBSERVICE_COMPUTER_EXCEPTION, e.getMessage(), e.getClass().getSimpleName()), e);
		}
	}

	
}

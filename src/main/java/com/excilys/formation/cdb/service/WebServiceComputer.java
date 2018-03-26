package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;


public enum WebServiceComputer {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WebServiceComputer.class);

	private WebServiceComputer() {
	
	}	
	
	
	public int getNumberOf() throws ServiceManagerException{
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		try {
			return cmpDB.getNumComputers();
		}catch(DAOException e) {
			logger.error("WebServiceError: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceError: " + e.getMessage(), e);
		}
	}
	
	public Computer getComputer(int id) throws ServiceManagerException, InstanceNotInDatabaseException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getComputerByID(id);
	}
	
	public List<Computer> getAllList() throws InstanceNotInDatabaseException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getComputerList();
	}
	
	public List<Computer> getList(int from, int to) throws InstanceNotInDatabaseException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getComputerList(from, to);
	}
	
	
	public void createComputer(Computer cmp) throws IncorrectFieldException, InstanceNotInDatabaseException {
		
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;		
		vcmp.validate(cmp);		
		cmpDB.create(cmp);
	}
	
	public void updateComputer(Computer cmp) throws IncorrectFieldException, InstanceNotInDatabaseException {
		
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		vcmp.validate(cmp);		
		cmpDB.update(cmp);
	}
	
	public void deleteComputer(int id) throws InstanceNotInDatabaseException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		Computer cmp = getComputer(id);
		cmpDB.delete(cmp);
	}

	
}

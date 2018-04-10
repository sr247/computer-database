package com.excilys.formation.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

public enum WebServiceCompany {
	
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(WebServiceCompany.class);

	private WebServiceCompany() {
		
	}

	public int getNumberOf() throws ServiceManagerException {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		try {
			return cpyDB.getNumCompanies();			
		}catch(DAOException e) {
			logger.error("WebServiceError: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceError: " + e.getMessage(), e);
		}
	}

	public List<Company> getAllList() throws ServiceManagerException {
		CompanyDB cmpDB = CompanyDB.INSTANCE;
		try {
			return cmpDB.getCompanyList();			
		}catch(DAOException e) {
			logger.error("WebServiceError: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceError: " + e.getMessage(), e);
		}
	}
	
	public List<Company> getList(int limit, int offset) throws ServiceManagerException{
		CompanyDB cpnDB = CompanyDB.INSTANCE;
		try {
			if(limit == 0 && offset == 0) {
				return cpnDB.getCompanyList();
			}
			return cpnDB.getCompanyList(limit, offset);			
		}catch (DAOException e) {
			logger.error("WebServiceError: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceError: " + e.getMessage(), e);
		}
	}
	
	public Company getCompany(String id) throws ServiceManagerException {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		Optional<Company> optCompany = Optional.empty();
		try {
			optCompany = cpyDB.getCompanyByID(Integer.valueOf(id));
		}catch(NumberFormatException|DAOException e) {
			logger.error("WebServiceError: {}", e.getMessage(), e);
			throw new ServiceManagerException("WebServiceError: " + e.getMessage(), e);
		}
		return optCompany.get();
	}

	
	
}

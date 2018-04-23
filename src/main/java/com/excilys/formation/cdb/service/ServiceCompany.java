package com.excilys.formation.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.exceptions.DAOException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

@Service
@EnableTransactionManagement
public class ServiceCompany {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceCompany.class);
	private static final String SERVICE_COMPANY_EXCEPTION = "ServiceCompany: %s";
	private static final String SERVICE_COMPANY_LOGGER = "ServiceCompany: {}";
	
	@Autowired
	private CompanyDB companyDB;
	
	private ServiceCompany() {}

	public int getNumberOf() throws ServiceManagerException {
		try {
			return companyDB.getNumCompanies();			
		}catch(DAOException e) {
			logger.error(SERVICE_COMPANY_LOGGER, e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPANY_EXCEPTION, e.getMessage()), e);
		}
	}

	public List<Company> getAllList() throws ServiceManagerException {
		try {
			return companyDB.getCompanyList();			
		}catch(DAOException e) {
			logger.error(SERVICE_COMPANY_LOGGER, e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPANY_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public List<Company> getList(int limit, int offset) throws ServiceManagerException{
		try {
			if(limit == 0 && offset == 0) {
				return companyDB.getCompanyList();
			}
			return companyDB.getCompanyList(limit, offset);			
		}catch (DAOException e) {
			logger.error(SERVICE_COMPANY_LOGGER, e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPANY_EXCEPTION, e.getMessage()), e);
		}
	}
	
	public Company getCompany(String id) throws ServiceManagerException {
		Optional<Company> optCompany = Optional.empty();
		try {
			optCompany = companyDB.getCompanyByID(Integer.valueOf(id));
		}catch(NumberFormatException e) {
			logger.error(SERVICE_COMPANY_LOGGER, e.getMessage(), e);
			throw new ServiceManagerException(String.format(SERVICE_COMPANY_EXCEPTION, e.getMessage()), e);
		}
		return optCompany.isPresent() ? optCompany.get() : null;
	}	
	
//	public void deleteCompany(String id) throws ServiceManagerException {
//		Company company = null;
//		try {
//			company  = getCompany(id);
//			companyDB.delete(company);
//		} catch (DAOException e) {
//			logger.error(SERVICE_COMPANY_LOGGER, e.getClass().getSimpleName(), e.getMessage(), e);
//			throw new ServiceManagerException(String.format(SERVICE_COMPANY_EXCEPTION, e.getMessage()), e);
//		}
//	}
}

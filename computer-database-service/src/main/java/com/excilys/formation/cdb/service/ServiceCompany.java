package com.excilys.formation.cdb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.excilys.formation.cdb.persistence.repositories.CompanyRepository;
import com.excilys.formation.cdb.core.entity.CompanyEntity;

@Service
@EnableTransactionManagement
public class ServiceCompany {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ServiceCompany.class);
	private static final String SERVICE_COMPANY_EXCEPTION = "ServiceCompany: %s";
	private static final String SERVICE_COMPANY_LOGGER = "ServiceCompany: {}";
	
	private CompanyRepository companyREP;
	
	@Autowired
	public ServiceCompany(CompanyRepository companyREP) {
		this.companyREP = companyREP;
	}
	

	public Long getNumberOf() throws ServiceManagerException {
		return companyREP.count();			
	}

	public Page<CompanyEntity> getList(int page, int size) {
		Page<CompanyEntity> computersPage;
//		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
		PageRequest pageable = PageRequest.of(page, size);
		computersPage = companyREP.findAll(pageable);				
		return computersPage;
	}
	
	public Page<CompanyEntity> getAllList() {
		Page<CompanyEntity> computersPage;
//		Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "id"));
		PageRequest pageable = PageRequest.of(0, (int)companyREP.count());
		computersPage = companyREP.findAll(pageable);				
		return computersPage;
	}
	
	
	public CompanyEntity getCompany(Long id) {
		Optional<CompanyEntity> company;
		company = companyREP.findById(id);
		return company.isPresent() ? company.get() : null;
	}
	
	public void deleteCompany(Long id) throws ServiceManagerException {
		if(companyREP.existsById(id)) {
			companyREP.deleteById(id); 				
		}
	}
}

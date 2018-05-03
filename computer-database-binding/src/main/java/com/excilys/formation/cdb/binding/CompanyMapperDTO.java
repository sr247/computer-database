package com.excilys.formation.cdb.binding;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.core.Company;
import com.excilys.formation.cdb.core.CompanyDTO;
import com.excilys.formation.cdb.core.entity.CompanyEntity;

@Component
public class CompanyMapperDTO {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompanyMapperDTO.class);
	
    public CompanyDTO map(Company company) {
		return new CompanyDTO(company.getId(), company.getName());
	}    
    
    public List<CompanyDTO> map(List<Company> companyList) {
    	List<CompanyDTO> cmpDTOList = new ArrayList<>();
		for(Company company : companyList) {
			CompanyDTO cpyDTO = map(company);
			cmpDTOList.add(cpyDTO);
		}
		return cmpDTOList;
	}    
    
    public CompanyDTO map(CompanyEntity company) {
    	Optional<CompanyEntity> opt = Optional.ofNullable(company);
    	String companyName = "";
    	long id = 0L;
    	if (opt.isPresent()){
    		companyName = opt.get().getName();
    		id = opt.get().getId();
    	}
		return new CompanyDTO(id, companyName);		
	}   

}

package com.excilys.formation.cdb.binding;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.core.Company;
import com.excilys.formation.cdb.core.CompanyDTO;

@Component
public class CompanyMapperDTO {
	
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

}

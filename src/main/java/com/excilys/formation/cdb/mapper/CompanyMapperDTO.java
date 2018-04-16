package com.excilys.formation.cdb.mapper;

import java.util.ArrayList;
import java.util.List;


import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.CompanyDTO;

/**
 * Classe de Mapping des entreprises formattée pour une page web
 * @author sr247
 */
public class CompanyMapperDTO {
    
	CompanyMapperDTO() {}

	/** 
	 * @param company Un ResultSet contenant les résultats des requêtes DAOs.
	 * @return Un object Optional<Company> qui est vide si res est vide. 
	 */
    public CompanyDTO map(Company company) {
		CompanyDTO compDTO = new CompanyDTO(company.getId(), company.getName());		
		return compDTO;
	}    
    
    public List<CompanyDTO> map(List<Company> companyList) {
    	List<CompanyDTO> cmpDTOList = new ArrayList<CompanyDTO>();
		for(Company company : companyList) {
			CompanyDTO cpyDTO = map(company);
			cmpDTOList.add(cpyDTO);
		}
		return cmpDTOList;
	}

}

package com.excilys.formation.cdb.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.CompanyDTO;

public enum CompanyMapperDTO {

    INSTANCE;
    
    /** Construit un Mapper pour les entreprises.
	 * @
	 */
	CompanyMapperDTO() {
	}

	/** 
	 * @param res Un ResultSet contenant les résultats des requêtes DAOs.
	 * @return Un object Optional<Company> qui est vide si res est vide. 
	 */
    public static CompanyDTO map(Company company) {
		CompanyDTO compDTO = new CompanyDTO(company.getId(), company.getName());		
		return compDTO;
	}
    
    
    
    public static List<CompanyDTO> map(List<Company> companyList) {
    	List<CompanyDTO> cmpDTOList = new ArrayList<CompanyDTO>();
		for(Company c : companyList) {
			int id = c.getId();
			String name = c.getName();
			CompanyDTO cpyDTO = new CompanyDTO(id, name);
			cmpDTOList.add(cpyDTO);
		}
		return cmpDTOList;
	}

}

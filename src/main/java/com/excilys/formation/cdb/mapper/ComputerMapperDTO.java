package com.excilys.formation.cdb.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.CompanyDTO;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.ComputerDTO;

public enum ComputerMapperDTO {

		
	INSTANCE;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ComputerMapper.class);
	
	private ComputerMapperDTO() {
		
	}
	
	public static List<ComputerDTO> map(List<Computer> computerList) {
		List<ComputerDTO> cmpDTOList = new ArrayList<ComputerDTO>();
		for(Computer c : computerList) {
			int id = c.getId();
			String name = c.getName() == null ? "" : c.getName();
			String intro = c.getIntroduced() == null ? "" : c.getIntroduced().toString();
			String discon = c.getDiscontinued() == null ? "" : c.getDiscontinued().toString();
			
			CompanyDTO companyTmp = CompanyMapperDTO.map(c.getCompany());
			String company = companyTmp == null ? "" : companyTmp.getName();
			
			ComputerDTO cmp = new ComputerDTO(id, name, intro, discon, company);
			cmpDTOList.add(cmp);
		}
		return cmpDTOList;
	}
	


}

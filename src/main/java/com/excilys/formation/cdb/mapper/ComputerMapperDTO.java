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
		for(Computer computer : computerList) {
			ComputerDTO cmp = map(computer);
			cmpDTOList.add(cmp);
		}
		return cmpDTOList;
	}
	
	public static ComputerDTO map(Computer computer) {
		int id = computer.getId();
		String name = computer.getName() == null ? "" : computer.getName();
		String intro = computer.getIntroduced() == null ? "" : computer.getIntroduced().toString();
		String discon = computer.getDiscontinued() == null ? "" : computer.getDiscontinued().toString();
		
		CompanyDTO companyTmp = CompanyMapperDTO.map(computer.getCompany());
		String company = companyTmp == null ? "" : companyTmp.getName();
		
		return new ComputerDTO(id, name, intro, discon, company);
	}


}

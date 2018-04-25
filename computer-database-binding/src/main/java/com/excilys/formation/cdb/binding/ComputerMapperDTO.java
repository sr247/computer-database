package com.excilys.formation.cdb.binding;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.core.CompanyDTO;
import com.excilys.formation.cdb.core.Computer;
import com.excilys.formation.cdb.core.ComputerDTO;

@Component
public class ComputerMapperDTO {
	
	@Autowired
	private CompanyMapperDTO companyMDTO;
	
	public List<ComputerDTO> map(List<Computer> computerList) {
		List<ComputerDTO> cmpDTOList = new ArrayList<>();
		for(Computer computer : computerList) {
			ComputerDTO cmp = map(computer);
			cmpDTOList.add(cmp);
		}
		return cmpDTOList;
	}
	
	public ComputerDTO map(Computer computer) {
		int id = computer.getId();
		String name = computer.getName() == null ? "" : computer.getName();
		String intro = computer.getIntroduced() == null ? "" : computer.getIntroduced().toString();
		String discon = computer.getDiscontinued() == null ? "" : computer.getDiscontinued().toString();
		
		CompanyDTO companyTmp = companyMDTO.map(computer.getCompany());
		String company = companyTmp == null ? "" : companyTmp.getName();
		
		return new ComputerDTO(id, name, intro, discon, company);
	}


}

package com.excilys.formation.cdb.pages;

import java.util.List;

import com.excilys.formation.cdb.exceptions.InstanceNotFoundException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.WebServiceComputer;

public class PagesComputer extends Pages<Computer> {

	public PagesComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PagesComputer(List<Computer> page) {
		super(page);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void next() throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		int max = webcmp.getNumberOf();
		pageIndex += PAGE_STRIDE;
		if( (max - pageIndex) < PAGE_STRIDE) {
			pageIndex = max - PAGE_STRIDE;
		} else {
			num++;
		}
		this.page = webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}

	@Override
	public void preview() throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		pageIndex -= PAGE_STRIDE;
		if(pageIndex < 0) {
			pageIndex = 0;
		}else {
			num--;
		}
		this.page = webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}
	
	
}

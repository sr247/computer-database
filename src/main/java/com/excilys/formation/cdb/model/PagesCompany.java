package com.excilys.formation.cdb.model;

import java.util.List;

import com.excilys.formation.cdb.exceptions.InstanceNotFoundException;
import com.excilys.formation.cdb.service.WebServiceCompany;

public class PagesCompany extends Pages<Company> {

	
	public PagesCompany() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PagesCompany(List<Company> page) {
		super(page);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void next() throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		WebServiceCompany webcpy = WebServiceCompany.getInstance();
		int max = webcpy.getNumberOf();
		pageIndex += PAGE_STRIDE;
		if( (max - pageIndex) < PAGE_STRIDE) {
			pageIndex = max - PAGE_STRIDE;
		} else {
			num++;
		}
		
		this.page = webcpy.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}

	@Override
	public void preview() throws InstanceNotFoundException {
		// TODO Auto-generated method stub
		WebServiceCompany webcpy = WebServiceCompany.getInstance();
		pageIndex -= PAGE_STRIDE;
		if(pageIndex < 0) {
			pageIndex = 0;
		}else {
			num--;
		}
		
		this.page = webcpy.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}
	

}

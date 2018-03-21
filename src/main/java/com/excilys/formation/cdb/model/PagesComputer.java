package com.excilys.formation.cdb.model;

import java.util.List;

import com.excilys.formation.cdb.service.WebServiceComputer;

public class PagesComputer extends Pages<Computer> {

	@Override
	public void next() {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.getInstance();
		int max = webcmp.getNumberOf();
		pageIndex += PAGE_STRIDE;
		if(pageIndex > max) {
			pageIndex = max - PAGE_STRIDE;
		}
		this.page = webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}

	@Override
	public void preview() {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.getInstance();
		pageIndex -= PAGE_STRIDE;
		if(pageIndex < 0) {
			pageIndex = 0;
		}
		this.page = webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}
	
	
}

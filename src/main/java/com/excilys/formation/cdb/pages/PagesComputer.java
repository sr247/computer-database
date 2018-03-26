package com.excilys.formation.cdb.pages;

import java.util.List;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.service.WebServiceComputer;

public class PagesComputer<T> extends Pages<T> {

	public PagesComputer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PagesComputer(List<T> page) {
		super(page);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void next() throws InstanceNotInDatabaseException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		int max = webcmp.getNumberOf();
		pageIndex += PAGE_STRIDE;
		if( (max - pageIndex) < PAGE_STRIDE) {
			pageIndex = max - PAGE_STRIDE;
		} else {
			num++;
		}
		this.page = (List<T>) webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}

	@Override
	public void preview() throws InstanceNotInDatabaseException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		pageIndex -= PAGE_STRIDE;
		if(pageIndex < 0) {
			pageIndex = 0;
		}else {
			num--;
		}
		this.page = (List<T>) webcmp.getList(pageIndex, pageIndex+PAGE_STRIDE);
	}
	
	
}

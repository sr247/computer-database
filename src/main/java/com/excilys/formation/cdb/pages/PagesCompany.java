package com.excilys.formation.cdb.pages;

import java.util.List;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.service.WebServiceCompany;

public class PagesCompany<T> extends Pages<T> {

	
	public PagesCompany() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PagesCompany(List<T> page) {
		super(page);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public void next() throws ServiceManagerException  {
		// TODO Auto-generated method stub
		WebServiceCompany webcpy = WebServiceCompany.INSTANCE;
		int max = webcpy.getNumberOf();
		pageRange.setFst(pageRange.getFst() + PAGE_STRIDE);
		pageRange.setSnd(pageRange.getSnd() + PAGE_STRIDE);
		
		if((max - pageRange.getFst()) < PAGE_STRIDE) {
			pageRange.setFst(max - PAGE_STRIDE);
			pageRange.setSnd(max);
		} else {
			numero++;
		}
		
		this.content = (List<T>) webcpy.getList(pageRange.getFst(), pageRange.getSnd());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void preview() throws ServiceManagerException  {
		// TODO Auto-generated method stub
		WebServiceCompany webcpy = WebServiceCompany.INSTANCE;
		
		pageRange.setFst(pageRange.getFst() - PAGE_STRIDE);
		pageRange.setSnd(pageRange.getSnd() - PAGE_STRIDE);
		
		if(pageRange.getFst() < 0) {
			pageRange.setFst(0);
			pageRange.setSnd(PAGE_STRIDE);
		}else {
			numero--;
		}
		
		this.content = (List<T>) webcpy.getList(numero, numero+PAGE_STRIDE);
	}

	public static void update() throws ServiceManagerException {
		// TODO Auto-generated method stub
		WebServiceCompany webcpy = WebServiceCompany.INSTANCE;
		int max = webcpy.getNumberOf();
		pageRange.setSnd(pageRange.getFst() + PAGE_STRIDE);
		if(pageRange.getSnd() > max) {
			pageRange.setFst(max - PAGE_STRIDE);
			pageRange.setSnd(max);
		}		
	}

	

}

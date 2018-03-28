package com.excilys.formation.cdb.pages;

import java.util.List;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
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

	@SuppressWarnings("unchecked")
	@Override
	public void next() throws ServiceManagerException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		int max = webcmp.getNumberOf();
		int maxPage = (int) Math.ceil((double)max / (double) PAGE_STRIDE);
		
		pageRange.setFst(pageRange.getFst() + PAGE_STRIDE);
		pageRange.setSnd(pageRange.getSnd() + PAGE_STRIDE);
		
		int len = numberOfPages.length;
		if((max - pageRange.getFst()) < PAGE_STRIDE) {
			pageRange.setFst(max - PAGE_STRIDE);
			pageRange.setSnd(max);
		} else {
			numero = numberOfPages[2];
			for(int i = 0; i < numberOfPages.length; i++) {
				numberOfPages[i]++;			
			}
		}
				
		this.content = (List<T>) webcmp.getList(numero, numero+PAGE_STRIDE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void preview() throws ServiceManagerException  {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		pageRange.setFst(pageRange.getFst() - PAGE_STRIDE);
		pageRange.setSnd(pageRange.getSnd() - PAGE_STRIDE);
		
		if(pageRange.getFst() < 0) {
			pageRange.setFst(0);
			pageRange.setSnd(PAGE_STRIDE);
		}else {
			numero--;
			for(int i = 0; i < numberOfPages.length; i++) {
				numberOfPages[i]--;			
			}
		}
		
		this.content = (List<T>) webcmp.getList(numero, numero+PAGE_STRIDE);
	}

	public static void update() throws ServiceManagerException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		int max = webcmp.getNumberOf();
		pageRange.setSnd(pageRange.getFst() + PAGE_STRIDE);
		if(pageRange.getSnd() > max) {
			pageRange.setFst(max - PAGE_STRIDE);
			pageRange.setSnd(max);
		}		
	}
	
	
}

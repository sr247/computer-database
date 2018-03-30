package com.excilys.formation.cdb.pages;

import java.util.List;
import java.util.Optional;

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

	@Override
	public void goTo(int index) throws ServiceManagerException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		numberOfElements = webcmp.getNumberOf();
		numberOfPages = (int) Math.ceil((double) numberOfElements / (double) PAGE_LIMIT);
		if(index < 2) {
			CURRENT_PAGE = Optional.of(1);
		}else if(index > 1 && index < numberOfPages-1) {
			CURRENT_PAGE = Optional.of(index);
		} else if(index > numberOfPages-1) {
			CURRENT_PAGE = Optional.of(numberOfPages);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get()-1) * PAGE_LIMIT;
		this.content = (List<T>) webcmp.getList(PAGE_OFFSET, PAGE_LIMIT);
	}
	
	
	@Override
	public void next() throws ServiceManagerException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		numberOfElements = webcmp.getNumberOf();
		numberOfPages = (int) Math.ceil((double) numberOfElements / (double) PAGE_LIMIT);
		
		if(CURRENT_PAGE.isPresent()) {
			CURRENT_PAGE = Optional.of(CURRENT_PAGE.get()+1);
			if(CURRENT_PAGE.get() > numberOfPages) 
				CURRENT_PAGE = Optional.of(numberOfPages);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get()-1) * PAGE_LIMIT;
		this.content = (List<T>) webcmp.getList(PAGE_OFFSET, PAGE_LIMIT);
	}

	
	@Override
	public void preview() throws ServiceManagerException  {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		numberOfElements = webcmp.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
		
		if(CURRENT_PAGE.isPresent()) {
			CURRENT_PAGE = Optional.of(CURRENT_PAGE.get()-1);
			if(CURRENT_PAGE.get() < 2 ) 
				CURRENT_PAGE = Optional.of(1);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get()-1) * PAGE_LIMIT;
		this.content = (List<T>) webcmp.getList(PAGE_OFFSET, PAGE_LIMIT);
	}

	public void update() throws ServiceManagerException {
		// TODO Auto-generated method stub
		WebServiceComputer webcmp = WebServiceComputer.INSTANCE;
		numberOfElements = webcmp.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
	}
	
	
}

package com.excilys.formation.cdb.pages;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.service.ServiceCompany;

@Service
public class PagesCompany<T> extends Pages<T> {

	@Autowired
	private ServiceCompany serviceCompany;
	
	public PagesCompany() {
		super();
	}

	public PagesCompany(List<T> page) {
		super(page);
	}

	
	@Override
	public void goTo(int index) throws ServiceManagerException {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
		if(index < 2) {
			CURRENT_PAGE = Optional.of(1);
		}else if(index > 1 && index < numberOfPages) {
			CURRENT_PAGE = Optional.of(index);
		} else if(index >= numberOfPages) {
			CURRENT_PAGE = Optional.of(numberOfPages);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get()-1) * PAGE_LIMIT;
		this.content = (List<T>) serviceCompany.getList(PAGE_OFFSET, PAGE_LIMIT);
	}
	
	
	@Override
	public void next() throws ServiceManagerException  {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
				
		if(CURRENT_PAGE.isPresent()) {
			CURRENT_PAGE = Optional.of(CURRENT_PAGE.get()+1);
			if(CURRENT_PAGE.get() > numberOfPages) 
				CURRENT_PAGE = Optional.of(numberOfPages);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get()-1) * PAGE_LIMIT;
		this.content = (List<T>) serviceCompany.getList(PAGE_OFFSET, PAGE_LIMIT);
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public void preview() throws ServiceManagerException  {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
		
		if(CURRENT_PAGE.isPresent()) {
			CURRENT_PAGE = Optional.of(CURRENT_PAGE.get()-1);
			if(CURRENT_PAGE.get() < 2 ) 
				CURRENT_PAGE = Optional.of(1);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get()-1) * PAGE_LIMIT;
		this.content = (List<T>) serviceCompany.getList(PAGE_OFFSET, PAGE_LIMIT);
	}

	public void update() throws ServiceManagerException {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);

	}

	@Override
	public int getNumberOfPages() throws ServiceManagerException {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
		return numberOfPages;
	}

	

}

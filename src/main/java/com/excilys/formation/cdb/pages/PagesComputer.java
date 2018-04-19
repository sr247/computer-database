package com.excilys.formation.cdb.pages;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.ModelBase;
import com.excilys.formation.cdb.service.ServiceComputer;

@Component
public class PagesComputer<T extends ModelBase> extends Pages<T> {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PagesComputer.class);
	
	@Autowired
	private ServiceComputer serviceComputer;
	
	public PagesComputer() {
		super();
	}

	public PagesComputer(List<T> page) {
		super(page);
	}

	@Override
	public void goTo(int index) throws ServiceManagerException {
		numberOfElements = serviceComputer.getNumberOf();
		numberOfPages = (int) Math.ceil((double) numberOfElements / (double) PAGE_LIMIT);
		if(index < 2) {
			CURRENT_PAGE = Optional.of(1);
		}else if(index > 1 && index < numberOfPages) {
			CURRENT_PAGE = Optional.of(index);
		} else if(index >= numberOfPages) {
			CURRENT_PAGE = Optional.of(numberOfPages);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get() - 1) * PAGE_LIMIT;
		this.content = (List<T>) serviceComputer.getList(PAGE_OFFSET, PAGE_LIMIT);
	}
	
	
	@Override
	public void next() throws ServiceManagerException {
		numberOfElements = serviceComputer.getNumberOf();
		numberOfPages = (int) Math.ceil((double) numberOfElements / (double) PAGE_LIMIT);
		
		if(CURRENT_PAGE.isPresent()) {
			CURRENT_PAGE = Optional.of(CURRENT_PAGE.get()+1);
			if(CURRENT_PAGE.get() > numberOfPages) 
				CURRENT_PAGE = Optional.of(numberOfPages);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get() - 1) * PAGE_LIMIT;
		this.content = (List<T>) serviceComputer.getList(PAGE_OFFSET, PAGE_LIMIT);
	}

	
	@Override
	public void preview() throws ServiceManagerException  {
		numberOfElements = serviceComputer.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
		
		if(CURRENT_PAGE.isPresent()) {
			CURRENT_PAGE = Optional.of(CURRENT_PAGE.get()-1);
			if(CURRENT_PAGE.get() < 2 ) 
				CURRENT_PAGE = Optional.of(1);
		}
		PAGE_OFFSET = (CURRENT_PAGE.get() - 1) * PAGE_LIMIT;
		this.content = (List<T>) serviceComputer.getList(PAGE_OFFSET, PAGE_LIMIT);
	}

	public void update() throws ServiceManagerException {
		numberOfElements = serviceComputer.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
	}

	@Override
	public int getNumberOfPages() throws ServiceManagerException {
		numberOfElements = serviceComputer.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) PAGE_LIMIT);
		return numberOfPages;
	}
	
	
}

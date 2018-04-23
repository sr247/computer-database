package com.excilys.formation.cdb.pages;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.ModelBase;
import com.excilys.formation.cdb.service.ServiceCompany;

@Component
public class PagesCompany<T extends ModelBase> extends Pages<T> {

	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(PagesCompany.class);
	
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
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) stride);
		if(index <= 1) {
			currentPage = Optional.of(1);
		}else if(index > 1 && index < numberOfPages) {
			currentPage = Optional.of(index);
		} else if(index >= numberOfPages) {
			currentPage = Optional.of(numberOfPages);
		}
		offset = (currentPage.get()-1) * stride;
		this.content = (List<T>) serviceCompany.getList(offset, stride);
	}
	
	
	@Override
	public void next() throws ServiceManagerException  {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) stride);
				
		if(currentPage.isPresent()) {
			currentPage = Optional.of(currentPage.get() + 1);
			if(currentPage.get() > numberOfPages) 
				currentPage = Optional.of(numberOfPages);
		}
		offset = (currentPage.get()-1) * stride;
		this.content = (List<T>) serviceCompany.getList(offset, stride);
	}
	

	@Override
	public void preview() throws ServiceManagerException  {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) stride);
		
		if(currentPage.isPresent()) {
			currentPage = Optional.of(currentPage.get()-1);
			if(currentPage.get() < 2) 
				currentPage = Optional.of(1);
		}
		offset = (currentPage.get()-1) * stride;
		this.content = (List<T>) serviceCompany.getList(offset, stride);
	}

//	public void update() throws ServiceManagerException {
//		numberOfElements = serviceCompany.getNumberOf();
//		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) stride);
//	}

	@Override
	public int getNumberOfPages() throws ServiceManagerException {
		numberOfElements = serviceCompany.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) stride);
		return numberOfPages;
	}

	@Override
	public int getNumberOfElements() throws ServiceManagerException {
		numberOfElements = serviceCompany.getNumberOf();
		return numberOfElements;
	}

	

}

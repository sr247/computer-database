package com.excilys.formation.cdb.service.pages;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.service.ServiceManagerException;
import com.excilys.formation.cdb.core.ModelBase;
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
		numberOfPages = (int) Math.ceil((double) numberOfElements / (double) stride);
		if(index <= 0) {
			currentPage = Optional.of(0);
		}else if(index > 0 && index < numberOfPages) {
			currentPage = Optional.of(index);
		} else {
			currentPage = Optional.of(numberOfPages);
		}
		setOffset((currentPage.get() - 1) * stride);
	}
	
	public void update() {
		numberOfElements = serviceComputer.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) stride);
	}

	@Override
	public int getNumberOfPages() throws ServiceManagerException {
		numberOfElements = serviceComputer.getNumberOf();
		numberOfPages = (int) Math.ceil((double)numberOfElements / (double) stride);
		logger.info("Number of pages in PagesComputer : {}\nNumber of Elements: {}", numberOfPages, numberOfElements);
		return numberOfPages;
	}

	@Override
	public Long getNumberOfElements() throws ServiceManagerException {
		numberOfElements = serviceComputer.getNumberOf();
		return numberOfElements;
	}
}

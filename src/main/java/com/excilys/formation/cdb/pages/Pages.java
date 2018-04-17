package com.excilys.formation.cdb.pages;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.ModelBase;

@Service
public abstract class Pages<T extends ModelBase> {
	
	// Déterminer quel variables dointt etre static ou pas.
	protected static int PAGE_LIMIT = 10;
	protected static int PAGE_OFFSET = 0;
	protected static Optional<Integer> CURRENT_PAGE = Optional.of(1);
	
	protected int numberOfElements;
	protected int numberOfPages;
	protected List<T>content;	
	
	public Pages(List<T> page) {
		this.content = page;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public List<T> getContent() {
		return content;
	}

	public static int getPAGE_OFFSET() {
		return PAGE_OFFSET;
	}
	
	public static int getPAGE_LIMIT() {
		return PAGE_LIMIT;
	}
	
	public static void setPAGE_LIMIT(int pAGE_LIMIT) {
		PAGE_LIMIT = pAGE_LIMIT;
	}

	public static void setPAGE_OFFSET(int pAGE_OFFSET) {
		PAGE_OFFSET = pAGE_OFFSET;
	}

	public void reset() {
		PAGE_LIMIT = 10;
		PAGE_OFFSET = 0;
		CURRENT_PAGE = Optional.of(1);
		
		numberOfElements = 0;
		numberOfPages = 0;
		content = null;
	}	
	
	public static Optional<Integer> getCURRENT_PAGE() {
		return CURRENT_PAGE;
	}

	public static void setStride(int pAGE_LIMIT) throws ServiceManagerException {
		PAGE_LIMIT = pAGE_LIMIT;
	}

	public abstract int getNumberOfPages() throws ServiceManagerException;
	
	public abstract void goTo(int index) throws ServiceManagerException;
	
	public abstract void next() throws ServiceManagerException;
	
	public abstract void preview() throws ServiceManagerException;
	
}

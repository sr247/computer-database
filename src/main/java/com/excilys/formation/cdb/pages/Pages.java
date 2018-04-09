package com.excilys.formation.cdb.pages;

import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;

public abstract class Pages<T> {
	
	// Déterminer quel variables dointt etre static ou pas.
	protected static int PAGE_LIMIT = 10;
	protected static int PAGE_OFFSET = 0;
	protected static Optional<Integer> CURRENT_PAGE = Optional.of(1);
	
	protected int numberOfElements;
	protected int numberOfPages;
	protected List<T> content;	
	
	public Pages() {
	}
	
	public Pages(List<T> page) {
		this.content = page;
	}


	/**
	 * @return the numberOfElements
	 */
	public int getNumberOfElements() {
		return numberOfElements;
	}

	/**
	 * @return the content
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * @return the pAGE_OFFSET
	 */
	public static int getPAGE_OFFSET() {
		return PAGE_OFFSET;
	}
	
	/**
	 * @return the pAGE_STRIDE
	 */
	public static int getPAGE_LIMIT() {
		return PAGE_LIMIT;
	}
	
	/**
	 * @param pAGE_STRIDE the pAGE_STRIDE to set
	 */
	public static void setPAGE_LIMIT(int pAGE_LIMIT) {
		PAGE_LIMIT = pAGE_LIMIT;
	}

	/**
	 * @param pAGE_OFFSET the pAGE_OFFSET to set
	 */
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
	
	/**
	 * @return the cURRENT_PAGE
	 */
	public static Optional<Integer> getCURRENT_PAGE() {
		return CURRENT_PAGE;
	}

	public static void setStride(int pAGE_LIMIT) throws ServiceManagerException {
		PAGE_LIMIT = pAGE_LIMIT;
	};
	
	/**
	 * @return the numberOfPage
	 */
	abstract public int getNumberOfPages() throws ServiceManagerException;
	
	public abstract void goTo(int index) throws ServiceManagerException;
	
	public abstract void next() throws ServiceManagerException;
	
	public abstract void preview() throws ServiceManagerException;
	
}

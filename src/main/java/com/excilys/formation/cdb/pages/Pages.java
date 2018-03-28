package com.excilys.formation.cdb.pages;

import java.util.List;

import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.utils.Pair;

public abstract class Pages<T> {
	
	protected List<T> content;
	protected int numero = 1;
	
	// Envisager une liste
	protected static int[] numberOfPages = {1,2,3,4,5};
	
	protected static int PAGE_STRIDE = 10;
	protected static Pair<Integer, Integer> pageRange = new Pair<Integer, Integer>(0, PAGE_STRIDE);
	
	public Pages() {
	}
	
	public Pages(List<T> page) {
		this.content = page;
	}
	
	/**
	 * @return the content
	 */
	public List<T> getContent() {
		return content;
	}

	/**
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @return the numberOfPages
	 */
	public static int[] getNumberOfPages() {
		return numberOfPages;
	}

	/**
	 * @return the pageRange
	 */
	public static Pair<Integer, Integer> getPageRange() {
		return pageRange;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(List<T> content) {
		this.content = content;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * @param numberOfPages the numberOfPages to set
	 */
	public static void setNumberOfPages(int[] numberOfPages) {
		Pages.numberOfPages = numberOfPages;
	}

	/**
	 * @param pageRange the pageRange to set
	 */
	public static void setPageRange(Pair<Integer, Integer> pageRange) {
		Pages.pageRange = pageRange;
	}

	public void reset() {
		this.content = null;
		this.numero = 1;
		numberOfPages = new int[]{1,2,3,4,5};
		Pages.pageRange = new Pair<Integer, Integer>(0, PAGE_STRIDE);
	}
	
	
	public static void setStride(int pAGE_STRIDE) throws ServiceManagerException {
		PAGE_STRIDE = pAGE_STRIDE;
	};
	
	public abstract void next() throws ServiceManagerException;
	
	public abstract void preview() throws ServiceManagerException;
}

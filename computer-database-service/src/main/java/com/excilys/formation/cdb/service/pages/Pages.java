package com.excilys.formation.cdb.service.pages;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.service.ServiceManagerException;
import com.excilys.formation.cdb.core.ModelBase;

@Component
public abstract class Pages<T extends ModelBase> {
	
	// Déterminer quelles variables doivent être static ou pas.
	protected static int stride = 10;
	protected static int offset = 0;
	
	protected Optional<Integer> currentPage = Optional.of(1);
	protected int numberOfElements;
	protected int numberOfPages;
	protected int focus;
	protected List<T>content;	

	public Pages() {}
	
	public Pages(List<T> page) {
		this.content = page;
	}
	
	public List<T> getContent() {
		return content;
	}
	
	public void setContent(List<T> content) {
		this.content = content;
	}

	public int getCurrentPage() {
		return currentPage.isPresent() ? currentPage.get() : 1;
	}
	
	public void setCurrentPage(int currentPage) {
		if(currentPage >= 1 && currentPage <= numberOfPages)
			this.currentPage = Optional.of(currentPage);
	}
	
	public void reset() {
		Pages.setStride(10);
		Pages.setOffset(0);
		
		currentPage = Optional.of(1);		
		numberOfElements = 0;
		numberOfPages = 0;
		content = null;
	}
	
	public static int getOffset() {
		return offset;
	}
	
	public static void setOffset(int pageOffset) {
		offset = pageOffset;
	}
	


	public static int getStride() {
		return stride;
	}

	public static void setStride(int stride) {
		Pages.stride = stride;
	}
	

	public abstract int getNumberOfPages() throws ServiceManagerException;
	public abstract int getNumberOfElements() throws ServiceManagerException;

	public abstract void goTo(int index) throws ServiceManagerException;
	
	public abstract void next() throws ServiceManagerException;
	
	public abstract void preview() throws ServiceManagerException;
	
}

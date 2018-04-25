package com.excilys.formation.cdb.service.pages;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.service.ServiceManagerException;
import com.excilys.formation.cdb.core.ModelBase;

@Component
public abstract class Pages<T extends ModelBase> {
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Pages.class);
	
	protected static int stride = 10;
	protected static int offset = 0;
	
	protected Optional<Integer> currentPage = Optional.of(1);
	protected int numberOfElements;
	protected int numberOfPages;
	protected int focus;
	protected List<T> content;	

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
		else
			this.currentPage = Optional.of(1);
	}
	
	public void reset() {
		setStride(10);
		setOffset(0);
		
		currentPage = Optional.of(1);		
		numberOfElements = 0;
		numberOfPages = 0;
		content = null;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int pageOffset) {
		setPageOffset(pageOffset);
	}
	 
	public int getStride() {
		return stride;
	}

	public void setStride(int stride) {
		if(stride != 10 && stride != 50 && stride != 100) {
			setPageStride(10);
		} else {
			setPageStride(stride);			
		}
	}
	
	private static synchronized void setPageStride(int stride) {
		Pages.stride = stride;
	}
	
	private static synchronized void setPageOffset(int offset) {
		Pages.offset = offset;
	}
	
	public abstract int getNumberOfPages() throws ServiceManagerException;
	
	public abstract int getNumberOfElements() throws ServiceManagerException;

	public abstract void goTo(int index) throws ServiceManagerException;
	
	public abstract void next() throws ServiceManagerException;
	
	public abstract void preview() throws ServiceManagerException;
	
}

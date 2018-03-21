package com.excilys.formation.cdb.model;

import java.util.List;

public abstract class Pages<T> {
	protected List<T> page;
	protected int num = 0;
	protected int pageIndex;
	protected static final int PAGE_STRIDE = 20;
	
	public Pages() {
		
	}
	
	public Pages(List<T> page) {
		this.page = page;
		
	}
	
	public List<T> getPage() {
		return page;
	}
	
	public void setPage(List<T> objects) {
		this.page = objects;
	}
	
	public int getNum() {
		return num;
	}
	
	public abstract void next();
	
	public abstract void preview();
}

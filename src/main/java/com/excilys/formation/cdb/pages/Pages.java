package com.excilys.formation.cdb.pages;

import java.util.List;

import com.excilys.formation.cdb.exceptions.InstanceNotInDatabaseException;
import com.excilys.formation.cdb.utils.Pair;

public abstract class Pages<T> {
	protected List<T> page;
	protected int num;
	protected static int pageIndex;
	protected static Pair<Integer, Integer> pageRange;
	protected static int PAGE_STRIDE = 5;
	
	public Pages() {
		
	}
	
	public Pages(List<T> page) {
		this.page = page;
		
	}
	
	public List<T> getContent() {
		return page;
	}
	
	public void setPage(List<T> objects) {
		this.page = objects;
	}
	
	public int getNum() {
		return num;
	}
	
	public static int getFrom() {
		return pageIndex;
	}
	
	public static int getTo() {
		return pageIndex+PAGE_STRIDE;
	}
	
	public static Pair<Integer, Integer> getRange(){
		return pageRange;
	}
	
	public void reset() {
		this.page = null;
		this.num = 0;
		Pages.pageIndex = 0;
	}
	
	public static void setStride(int stride) {
		PAGE_STRIDE = stride;
	}
	
	public abstract void next() throws InstanceNotInDatabaseException;
	
	public abstract void preview() throws InstanceNotInDatabaseException;
}

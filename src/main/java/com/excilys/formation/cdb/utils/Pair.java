package com.excilys.formation.cdb.utils;

public class Pair<T, U> {
	private T fst;
	private U snd;
		
	public Pair(T fst, U snd){		
		this.setFst(fst);
		this.setSnd(snd);
	}


	public U getSnd() {
		return snd;
	}


	public void setSnd(U snd) {
		this.snd = snd;
	}


	public T getFst() {
		return fst;
	}


	public void setFst(T fst) {
		this.fst = fst;
	}
}

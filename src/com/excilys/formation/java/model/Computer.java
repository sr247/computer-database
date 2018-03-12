package com.excilys.formation.java.model;

import java.sql.Date;

public class Computer {
	
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private int company_id;
	
	/*
	 * Fonctionnalité  1 à tester immédiatement 
	 * vérifier le lien avec JDBC (persitance)
	 */
	public Computer(int id, String name, Date introduced, Date discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the introduced
	 */
	public Date getIntroduced() {
		return introduced;
	}



	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}



	/**
	 * @return the discontinued
	 */
	public Date getDiscontinued() {
		return discontinued;
	}



	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}



	/**
	 * @return the company_id
	 */
	public int getCompany_id() {
		return company_id;
	}



	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	
}
	
	


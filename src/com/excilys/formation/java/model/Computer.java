package com.excilys.formation.java.model;

import java.sql.Date;

public class Computer {
	
	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;

	// Ce champ deviendra un objet company à part entière.
	private int companyId;
	/*
	 * Fonctionnalité  1 à tester immédiatement 
	 * vérifier le lien avec JDBC (persitance)
	 */
	public Computer(int id, String name, Date introduced, Date discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = company_id;
	}
	
	public Computer(String name, Date introduced, Date discontinued, int company_id) {
		this.id = 0;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = company_id;
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
	public int getCompanyId() {
		return companyId;
	}



	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(int company_id) {
		this.companyId = company_id;
	}
	
	@Override 
	public String toString() {
		return "Computer:("
				+ "id=" + id + ", "
				+ "name=" + name + ", "
				+ "introduced=" + introduced + ", "
				+ "discontinued=" + discontinued + ", "
				+ "company_id=" + companyId + ")";
		
	}
	
	@Override
	public boolean equals(Object  o) {
		Computer cmp = (Computer) o;
		return this.id == cmp.id
				&& this.name.equals(cmp.name)
				&& this.introduced == cmp.introduced
				&& this.discontinued == cmp.discontinued
				&& this.companyId == cmp.companyId;
		
	}
}

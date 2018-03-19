package com.excilys.formation.cdb.model;

import java.time.LocalDate;

public class Computer {
	
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;

	// Ce champ deviendra un objet company à part entière.
	private Company company;
	/*
	 * Fonctionnalité  1 à tester immédiatement 
	 * vérifier le lien avec JDBC (persitance)
	 */
<<<<<<< HEAD:src/com/excilys/formation/java/model/Computer.java
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, int company_id) {
=======
	public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
>>>>>>> 07abdf529dd860bb03aa2f09bf312abe3f167066:src/main/java/com/excilys/formation/cdb/model/Computer.java
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
<<<<<<< HEAD:src/com/excilys/formation/java/model/Computer.java
	public Computer(String name, LocalDate introduced, LocalDate discontinued, int company_id) {
=======
	public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
>>>>>>> 07abdf529dd860bb03aa2f09bf312abe3f167066:src/main/java/com/excilys/formation/cdb/model/Computer.java
		this.id = 0;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
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
	public LocalDate getIntroduced() {
		return introduced;
	}



	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}



	/**
	 * @return the discontinued
	 */
	public LocalDate getDiscontinued() {
		return discontinued;
	}



	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}



	/**
	 * @return the company_id
	 */
	public Company getCompany() {
		return company;
	}



	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override 
	public String toString() {
		return "Computer:("
				+ "id=" + id + ", "
				+ "name=" + name + ", "
				+ "introduced=" + introduced + ", "
				+ "discontinued=" + discontinued + ", "
				+ "company=" + company + ")";
		
	}
	
	@Override
	public boolean equals(Object  o) {
		Computer cmp = (Computer) o;
		return this.id == cmp.id
				&& this.name.equals(cmp.name)
				&& this.introduced == cmp.introduced
				&& this.discontinued == cmp.discontinued
				&& this.company == cmp.company;
		
	}
}

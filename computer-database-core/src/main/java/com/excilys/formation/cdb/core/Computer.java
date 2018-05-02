package com.excilys.formation.cdb.core;

import java.time.LocalDate;

public class Computer extends ModelBase {
	
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;

	// Ce champ deviendra un objet company à part entière.
	private Company company;

	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = 0L;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}


	public LocalDate getDiscontinued() {
		return discontinued;
	}


	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}


	public Company getCompany() {
		return company;
	}


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

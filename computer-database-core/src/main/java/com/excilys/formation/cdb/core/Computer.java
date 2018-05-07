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
	public int hashCode() {
		final int prime = 31;
		int result = 7;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override 
	public String toString() {
		return new StringBuilder()
				.append("Computer:(")
				.append("id=")
				.append(id)
				.append(", ")
				.append("name=")
				.append(name)
				.append(", ")
				.append("introduced=")
				.append(introduced)
				.append(", ")
				.append("discontinued=")
				.append(discontinued)
				.append(", ")
				.append("company=")
				.append(company)
				.append(")")
				.toString();
	}
}


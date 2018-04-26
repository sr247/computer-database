package com.excilys.formation.cdb.core.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
		
	@Column(name ="name")
	private String name;
	
	@Column(name = "introduced")
	private LocalDate introduced;
	
	@Column(name = "discontinued")
	private LocalDate discontinued;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private CompanyEntity company;
		
	
	public ComputerEntity(LocalDate introduced, LocalDate discontinued, CompanyEntity company) {
		super();
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}

	public ComputerEntity(Long id, LocalDate introduced, LocalDate discontinued, CompanyEntity company) {
		super();
		this.id = id;
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

	public CompanyEntity getCompany() {
		return company;
	}

	public void setCompany(CompanyEntity companyId) {
		this.company = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 5;
		result = result << prime * result + ((company == null) ? 0 : company.hashCode());
		result = result << prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = result << prime * result + ((id == null) ? 0 : id.hashCode());
		result = result << prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		ComputerEntity other = (ComputerEntity) obj;
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
		return true;
	}

	
}

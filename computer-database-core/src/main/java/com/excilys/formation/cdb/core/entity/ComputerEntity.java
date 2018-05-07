package com.excilys.formation.cdb.core.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.formation.cdb.core.ModelBase;

@Entity
@Table(name = "computer")
public class ComputerEntity extends ModelBase{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
		
	@Column(name ="name")
	private String name;
	
	@Column(name = "introduced")
	private LocalDate introduced;
	
	@Column(name = "discontinued")
	private LocalDate discontinued;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	private CompanyEntity company;
	
	public ComputerEntity() {
		super();
	}
	
	public ComputerEntity(String name, LocalDate introduced, LocalDate discontinued, CompanyEntity company) {
		super();
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	public String toString() {
		return new StringBuilder().append("ComputerEntity:(")
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
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 5;
		result = result << prime * result + ((company == null) ? 0 : company.hashCode());
		result = result << prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = result << prime * result + (int) id;
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
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		return true;
	}
	
}

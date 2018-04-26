package com.excilys.formation.cdb.core.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ComputerEntity {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	
	@Column(name = "introduced")
	private LocalDate introduced;

	@Column(name = "discontinued")
	private LocalDate discontinued;
	
	@Column(name = "company_id")
	private Long companyId;
		

	public ComputerEntity(LocalDate introduced, LocalDate discontinued, Long companyId) {
		super();
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public ComputerEntity(Long id, LocalDate introduced, LocalDate discontinued, Long companyId) {
		super();
		this.id = id;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = result << prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
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

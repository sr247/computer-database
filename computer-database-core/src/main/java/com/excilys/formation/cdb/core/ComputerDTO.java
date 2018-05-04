package com.excilys.formation.cdb.core;

public class ComputerDTO extends ModelBase {
	private Long id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;
	
	public ComputerDTO() {
		super();
	}
	
	public ComputerDTO(Long id, String name, String introduced, String discontinued, CompanyDTO company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	

	public ComputerDTO(String name, String introduced, String discontinued, CompanyDTO company) {
		this.id = 0L;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	

	public long getId() {
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

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 13;
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
		ComputerDTO other = (ComputerDTO) obj;
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
		StringBuilder s = new StringBuilder();
		return s.append("ComputerDTO:(")
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

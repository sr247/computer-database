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
	
	public ComputerDTO(long id, String name, String introduced, String discontinued, CompanyDTO company) {
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


	public void setId(long id) {
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
	
	@Override
	public boolean equals(Object o) {
		ComputerDTO cmp = (ComputerDTO) o;
		return this.id == cmp.id
				&& this.name.equals(cmp.name)
				&& this.introduced.equals(cmp.introduced)
				&& this.discontinued.equals(cmp.discontinued)
				&& this.company.equals(cmp.company);
	}
}

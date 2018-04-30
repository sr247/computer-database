package com.excilys.formation.cdb.core;

public class ComputerDTO extends ModelBase {
	private long id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyName;
	
	public ComputerDTO() {
		super();
	}
	
	public ComputerDTO(long id, String name, String introduced, String discontinued, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = companyName;
	}
	

	public ComputerDTO(String name, String introduced, String discontinued, String companyName) {
		this.id = 0;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyName = companyName;
	}
	

	public long getId() {
		return id;
	}


	public void setId(int id) {
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompany(String companyName) {
		this.companyName = companyName;
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
				.append(companyName)
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
				&& this.companyName.equals(cmp.companyName);
	}
}

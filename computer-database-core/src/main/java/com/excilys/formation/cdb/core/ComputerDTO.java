package com.excilys.formation.cdb.core;

public class ComputerDTO extends ModelBase {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private String companyName;
	
	public ComputerDTO(int id, String name, String introduced, String discontinued, String companyName) {
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
	public String getIntroduced() {
		return introduced;
	}


	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}



	/**
	 * @return the discontinued
	 */
	public String getDiscontinued() {
		return discontinued;
	}



	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}



	/**
	 * @return the company_id
	 */
	public String getCompanyName() {
		return companyName;
	}


	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany(String companyName) {
		this.companyName = companyName;
	}
	
	@Override 
	public String toString() {
		StringBuilder s = new StringBuilder();
		return s.append("Computer:(")
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

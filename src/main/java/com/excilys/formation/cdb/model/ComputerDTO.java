package com.excilys.formation.cdb.model;

public class ComputerDTO {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private String company;
	
	public ComputerDTO(int id, String name, String introduced, String discontinued, String company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	

	public ComputerDTO(String name, String introduced, String discontinued, String company) {
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
	public String getCompany() {
		return company;
	}



	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany(String company) {
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
		ComputerDTO cmp = (ComputerDTO) o;
		return this.id == cmp.id
				&& this.name.equals(cmp.name)
				&& this.introduced == cmp.introduced
				&& this.discontinued == cmp.discontinued
				&& this.company == cmp.company;
		
	}
}

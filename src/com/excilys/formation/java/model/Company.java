package com.excilys.formation.java.model;

public class Company {
	private int id;
	private String name;
	
	
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Company:("
				+ "id=" + id + ", "
				+ "name=" + name + ")";
	}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	

}

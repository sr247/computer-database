package com.excilys.formation.java.mapper;

import java.sql.Date;
import java.sql.ResultSet;

public class ComputerMP {
	

	private int id;
	private String name;
	private Date introduced;
	private Date discontinued;
	private int company_id;
	/*
	 * Fonctionnalité  1 à tester immédiatement 
	 * vérifier le lien avec JDBC (persitance)
	 */
	public ComputerMP(int id, String name, Date introduced, Date discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
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
	public Date getIntroduced() {
		return introduced;
	}



	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(Date introduced) {
		this.introduced = introduced;
	}



	/**
	 * @return the discontinued
	 */
	public Date getDiscontinued() {
		return discontinued;
	}



	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(Date discontinued) {
		this.discontinued = discontinued;
	}



	/**
	 * @return the company_id
	 */
	public int getCompany_id() {
		return company_id;
	}



	/**
	 * @param company_id the company_id to set
	 */
	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	
	
	public static ComputerMP map(ResultSet res) {
		
		
		int id = -1;
		String name = "";
		Date introduced = new Date(0);
		Date discontinued = new Date(0);
		int company_id  = -1; 
		
		try {
			id = res.getInt("ID");
			name = res.getString("NAME");
			introduced = res.getDate("INTRODUCED");
			discontinued = res.getDate("DISCONTINUED");
			company_id = res.getInt("COMPANY_ID");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}
		
		return new ComputerMP(id, name, introduced, discontinued, company_id);
	}
	
}

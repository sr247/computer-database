package com.excilys.formation.java.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyMP {

	private int id;
	private String name;
	
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

	@Override 
	public String toString() {
		return "Company:("
				+ "id=" + id + ", "
				+ "name=" + name + ")";
		
	}
	
	
	
	public CompanyMP(int id, String name) {
		this.id = id;
		this.name = name;
	}

	
	public static CompanyMP map(ResultSet res) throws SQLException{
		
		int id = -1;
		String name = "";

		/*
		try {
			id = res.getInt("ID");
			name = res.getString("NAME");
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			
		}*/
		
		id = res.getInt("ID");
		name = res.getString("NAME");
		return new CompanyMP(id, name);
	}

}

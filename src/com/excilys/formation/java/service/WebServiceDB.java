package com.excilys.formation.java.service;

import java.sql.Date;

import com.excilys.formation.java.mapper.CompanyMP;
import com.excilys.formation.java.mapper.ComputerMP;
import com.excilys.formation.java.persistence.ConnexionDB;
import com.mysql.jdbc.Connection;

public class WebServiceDB {
	private ConnexionDB cnndb;
	
	
	/**
	 * @return the cnndb
	 */
	public ConnexionDB getCnndb() {
		return cnndb;
	}

	public WebServiceDB() {
		ConnexionDB conndb = new ConnexionDB();
	}
	
	
	public void create(int id, String name, Date introduced, Date discontinued, int company_id) {
		cnndb.getCmpdb().create(new ComputerMP(id, name, introduced, discontinued, company_id), cnndb.getConnection());
	}
	
	public void create(int id, String name) {
		cnndb.getCpndb().create(new CompanyMP(id, name), cnndb.getConnection());
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("WebServiceDB.main()");

	}

}

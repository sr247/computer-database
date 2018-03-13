package com.excilys.formation.java.service;

import com.excilys.formation.java.mapper.CompanyMP;
import com.excilys.formation.java.persistence.CompanyDB;;

public class WebServiceCompany {
	
	private static WebServiceCompany _instance = null;
	private CompanyDB companyDB;

	private WebServiceCompany() {
		companyDB = new CompanyDB();
	}
	
	public static WebServiceCompany getInstance() {
		if(_instance == null) {
			_instance = new WebServiceCompany();
		}
		return _instance;
	}
	
	public CompanyDB getCompanyDB() {
		return companyDB;
	}
		
	public void create(int id, String name) {
		companyDB.create(name);
	}
	
	
	
}

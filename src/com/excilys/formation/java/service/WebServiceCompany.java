package com.excilys.formation.java.service;

import java.util.ArrayList;

import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.persistence.CompanyDB;

public class WebServiceCompany {
	
	private static WebServiceCompany _instance = null;

	private WebServiceCompany() {
		
	}
	
	public static WebServiceCompany getInstance() {
		if(_instance == null) {
			_instance = new WebServiceCompany();
		}
		return _instance;
	}

	public ArrayList<Company> getList(int from, int to){
		CompanyDB cpnDB = CompanyDB.getInterface();
		if(from == 0 && to == 0) {
			return cpnDB.getCompanyList();
		}
		return cpnDB.getCompanyList(from, to);
	}
	
	
}

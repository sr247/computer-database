package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

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

	public List<Company> getList(int from, int to){
		CompanyDB cpnDB = CompanyDB.INSTANTCE;
		if(from == 0 && to == 0) {
			return cpnDB.getCompanyList();
		}
		return cpnDB.getCompanyList(from, to);
	}
	
	
}

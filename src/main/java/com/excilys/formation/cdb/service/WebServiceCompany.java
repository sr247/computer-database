package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ComputerDB;

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
	
	public int getNumberOf() {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		return cpyDB.getNumCompanies();
	}

	public List<Company> getAllList() {
		CompanyDB cmpDB = CompanyDB.INSTANCE;
		return cmpDB.getCompanyList();
	}
	
	public List<Company> getList(int from, int to){
		CompanyDB cpnDB = CompanyDB.INSTANCE;
		if(from == 0 && to == 0) {
			return cpnDB.getCompanyList();
		}
		return cpnDB.getCompanyList(from, to);
	}
	
	public Company getCompany(String id) {
		CompanyDB cpyDB = CompanyDB.INSTANCE;
		return cpyDB.getCompanyByID(Integer.valueOf(id)).get();
	}

	
	
}

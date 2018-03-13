package com.excilys.formation.java.service;

import java.sql.Date;

import com.excilys.formation.java.exceptions.IncorrectFieldException;
import com.excilys.formation.java.persistence.ComputerDB;


public class WebServiceComputer {
	private static WebServiceComputer _instance = null;
	private ComputerDB computerDB;

	private WebServiceComputer() {
		computerDB = new ComputerDB();
	}	
	
	synchronized public static WebServiceComputer getInstance() {
		if(_instance == null) {
			_instance = new WebServiceComputer();
		}
		return _instance;
	}
		
	public ComputerDB getComputerDB() {
		return computerDB;
	}
	
	public void create(String[] fields) throws IncorrectFieldException {
		
		if(ValidateComputer.check(fields)) {
			String name = fields[0];
			Date introduced = Date.valueOf(fields[1]);
			Date discontinued = Date.valueOf(fields[2]);
			int company_id = Integer.valueOf(fields[3]);			
			computerDB.create(name, introduced, discontinued, company_id);	
		} else {
			throw new IncorrectFieldException();
		}
	
		
	}
	
}

package com.excilys.formation.java.service;

import java.sql.Date;
import java.util.ArrayList;

import com.excilys.formation.java.exceptions.IncorrectFieldException;
import com.excilys.formation.java.exceptions.InstanceNotFoundException;
import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.persistence.ComputerDB;


public class WebServiceComputer {
	private static WebServiceComputer _instance = null;
	

	private WebServiceComputer() {
	
	}	
	
	synchronized public static WebServiceComputer getInstance() {
		if(_instance == null) {
			_instance = new WebServiceComputer();
		}
		return _instance;
	}
	
	public Computer getComputer(String id) {
		ComputerDB cmpDB = ComputerDB.getInterface();
		return cmpDB.getComputerByID(Integer.valueOf(id));
	}
	
	public ArrayList<Computer> getList(int from, int to) {
		ComputerDB cmpDB = ComputerDB.getInterface();
		if(from == 0 && to == 0) {
			return cmpDB.getComputerList();
		}
		return cmpDB.getComputerList(from, to);
	}
	

	
	public void createComputer(ArrayList<String> fields) throws IncorrectFieldException, InstanceNotFoundException {
		ValidateComputer vcmp = ValidateComputer.getinterface();
		ComputerDB cmpDB = ComputerDB.getInterface();
		
		String name = vcmp.checkName(fields.get(0));
		Date introduced = vcmp.checkDate(fields.get(1));
		Date discontinued = vcmp.checkDate(fields.get(2));
		int company_id = vcmp.checkForeignKey(fields.get(3));
		Computer cmp = new Computer(name, introduced, discontinued, company_id);
		
		cmpDB.create(cmp);
		
	}
	
	public void updateComputer(ArrayList<String> fields, String id) throws IncorrectFieldException, InstanceNotFoundException {
		
		ValidateComputer vcmp = ValidateComputer.getinterface();
		ComputerDB cmpDB = ComputerDB.getInterface();
		Computer cmp = getComputer(id);
		
		String name = vcmp.checkName(fields.get(0));
		Date introduced = vcmp.checkDate(fields.get(1));
		Date discontinued = vcmp.checkDate(fields.get(2));
		int company_id = vcmp.checkForeignKey(fields.get(3));
		
		cmp.setName(name);
		cmp.setIntroduced(introduced);
		cmp.setDiscontinued(discontinued);
		cmp.setCompany_id(company_id);
		
		cmpDB.update(cmp);
	}
	
	public void deleteComputer(String id) {
		ComputerDB cmpDB = ComputerDB.getInterface();
		Computer cmp = getComputer(id);
		cmpDB.delete(cmp);
		
	}

	
}

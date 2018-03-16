package com.excilys.formation.java.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getComputerByID(Integer.valueOf(id));
	}
	
	public List<Computer> getList(int from, int to) {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		if(from == 0 && to == 0) {
			return cmpDB.getComputerList();
		}
		return cmpDB.getComputerList(from, to);
	}
	

	
	public void createComputer(ArrayList<String> fields) throws IncorrectFieldException, InstanceNotFoundException {
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		
		String name = vcmp.checkName(fields.get(0));
		LocalDate introduced = vcmp.checkDate(fields.get(1));
		LocalDate discontinued = vcmp.checkDate(fields.get(2));
		int company_id = vcmp.checkForeignKey(fields.get(3));
		Computer cmp = new Computer(cmpDB.getNumComputers()+1, name, introduced, discontinued, company_id);
		
		cmpDB.create(cmp);
		
	}
	
	public void updateComputer(ArrayList<String> fields, String id) throws IncorrectFieldException, InstanceNotFoundException {
		
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		Computer cmp = getComputer(id);
		
		String name = vcmp.checkName(fields.get(0));
		LocalDate introduced = vcmp.checkDate(fields.get(1));
		LocalDate discontinued = vcmp.checkDate(fields.get(2));
		int company_id = vcmp.checkForeignKey(fields.get(3));
		
		cmp.setName(name);
		cmp.setIntroduced(introduced);
		cmp.setDiscontinued(discontinued);
		cmp.setCompany_id(company_id);
		
		cmpDB.update(cmp);
	}
	
	public void deleteComputer(String id) {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		Computer cmp = getComputer(id);
		cmpDB.delete(cmp);
		
	}

	
}

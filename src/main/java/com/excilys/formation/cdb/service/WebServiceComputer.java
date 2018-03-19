package com.excilys.formation.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.InstanceNotFoundException;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;


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
	
	
	public void createComputer(Computer cmp /*ArrayList<String> fields*/) throws IncorrectFieldException, InstanceNotFoundException {
		
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;		
		vcmp.validate(cmp);		
		cmpDB.create(cmp);
		
	}
	
	public void updateComputer(Computer cmp) throws IncorrectFieldException, InstanceNotFoundException {
		
		ValidateComputer vcmp = ValidateComputer.INSTANCE;
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		vcmp.validate(cmp);		
		cmpDB.update(cmp);
	}
	
	public void deleteComputer(String id) {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		Computer cmp = getComputer(id);
		cmpDB.delete(cmp);
		
	}

	
}

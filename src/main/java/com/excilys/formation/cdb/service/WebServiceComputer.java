package com.excilys.formation.cdb.service;

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
	
	public int getNumberOf() {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getNumComputers();
	}
	
	public Computer getComputer(int id) throws InstanceNotFoundException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getComputerByID(id);
	}
	
	public List<Computer> getAllList() throws InstanceNotFoundException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getComputerList();
	}
	
	public List<Computer> getList(int from, int to) throws InstanceNotFoundException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		return cmpDB.getComputerList(from, to);
	}
	
	
	public void createComputer(Computer cmp) throws IncorrectFieldException, InstanceNotFoundException {
		
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
	
	public void deleteComputer(int id) throws InstanceNotFoundException {
		ComputerDB cmpDB = ComputerDB.INSTANCE;
		Computer cmp = getComputer(id);
		cmpDB.delete(cmp);
	}

	
}

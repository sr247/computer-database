package com.excilys.formation.java.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.formation.java.mapper.CompanyMP;
import com.excilys.formation.java.mapper.ComputerMP;
import com.excilys.formation.java.persistence.ConnexionDB;

public class CLI_UI {
	// Revoir ce qu'implique un attribut statique ici
	private boolean exit;
	private String command;
	private ConnexionDB cnndb;
	
	public CLI_UI() {
		this.exit = false;
		this.command = "";
		this.cnndb = new ConnexionDB();
	}
	
	public  boolean exit() {
		return exit;
	}
	
	public void showDetailComputer() {
		
	}
	
	public void list(String[] t) {
		boolean listAll =  t[1].equals("all");
		String table = listAll ? t[2] : t[1];
		if("computer".equals(table)) {
			ArrayList<ComputerMP> cmpList = null;
			if(listAll) {
				cmpList = cnndb.getCmpdb().getComputerList(cnndb.getConnection());
			} else {
				cmpList = cnndb.getCmpdb().getComputerList(cnndb.getConnection(), 0, 10);
			}

			for(ComputerMP cmp : cmpList) {
				System.out.println(cmp);
			}
		} else if("company".equals(table)){
			ArrayList<CompanyMP> cpyList = null;
			
			if(listAll) {
				cpyList = cnndb.getCpndb().getCompanyList(cnndb.getConnection());
			} else {
				cpyList = cnndb.getCpndb().getCompanyList(cnndb.getConnection(), 0, 10);
			}
			for(CompanyMP cpn : cpyList) {
				System.out.println(cpn);
			}
		}
	}
	
	public void enterCommand() {
		Scanner sc = new Scanner(System.in);
		command = sc.nextLine();
		String[] target = null;
		
		if("exit".equals(command) || "quit".equals(command)){
			exit = true;
		} else if (command.contains("list")) {
			// Ici target = objet visé par la requete i.e une des tables 
			// i.e la suite de la chaine command, après le mot list	
			target = command.split(" ");
			System.out.println("Command " + target[0] + " " + target[1]);
			list(target);
			
		} else if (command.contains("show")) {
			target = command.split(" ");
			System.out.println("Command " + target[0] + " " + target[1]);
			System.out.println("Command show");
		}
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		CLI_UI cmd = new CLI_UI();
		ConnexionDB conndb = new ConnexionDB();
		
		System.out.println("-- Command Line Interface --");
		while(!cmd.exit()) {
			System.out.print("cli_db > ");
			cmd.enterCommand();
		}
	}

}

package com.excilys.formation.java.ui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.formation.java.mapper.CompanyMP;
import com.excilys.formation.java.mapper.ComputerMP;
import com.excilys.formation.java.persistence.ConnexionDB;
import com.excilys.formation.java.service.WebServiceDB;
import com.mysql.jdbc.Connection;

public class CLI_UI {
	private boolean exit;
	private String command;
	private WebServiceDB wsdb;
	
	
	public CLI_UI() {
		this.exit = false;
		this.command = "";
		this.wsdb = new WebServiceDB();
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
				cmpList = wsdb.getCnndb().getCmpdb().getComputerList(wsdb.getCnndb().getConnection());
			} else {
				// Gerer les cas from to
				cmpList = wsdb.getCnndb().getCmpdb().getComputerList(wsdb.getCnndb().getConnection(), 0, 10);
			}

			for(ComputerMP cmp : cmpList) {
				System.out.println(cmp);
			}
		} else if("company".equals(table)){
			ArrayList<CompanyMP> cpyList = null;
			
			if(listAll) {
				cpyList = wsdb.getCnndb().getCpndb().getCompanyList(wsdb.getCnndb().getConnection());
			} else {
				// Gerer les cas from to
				cpyList = wsdb.getCnndb().getCpndb().getCompanyList(wsdb.getCnndb().getConnection(), 0, 10);
			}
			for(CompanyMP cpn : cpyList) {
				System.out.println(cpn);
			}
		}
	}
	
	public void create(String[] t) {
		Scanner sc = new Scanner(System.in);
		boolean into = t[1].equals("into");
		String table = into ? t[2] : t[1];
		if("computer".equals(table)) {
			// Ici il faudra check la forme et les valeurs de chaque ajout
			// Valeur null, erreur de syntaxe, nombre de champ
			String s = sc.nextLine();
			String[] ss = s.replaceAll("[( | )]", " ").split(",");
			
			// Ce bloc est officiellement gérer par le WebServiceDB
			wsdb.create(Integer.valueOf(ss[0]), ss[1], null, null, Integer.valueOf(ss[4]));
		} else if("company".equals(table)){
			String s = sc.nextLine();
			String[] ss = s.replaceAll("[( | )]", " ").split(",");
			wsdb.create(Integer.valueOf(ss[0]), ss[1]);
		}
		sc.close();
	}
	
	public void enterCommand() {
		Scanner sc = new Scanner(System.in);
		command = sc.nextLine();
		String[] target = null;
		
		if("exit".equals(command) || "quit".equals(command)){
			exit = true;
		} else if (command.contains("list")) {
			// Ici target = objet vis� par la requete i.e une des tables 
			// i.e la suite de la chaine command, apr�s le mot list	
			target = command.split(" ");
			System.out.println("Command " + target[0] + " " + target[1]);
			list(target);
			
		} else if (command.contains("show")) {
			target = command.split(" ");
			System.out.println("Command " + target[0] + " " + target[1]);
			System.out.println("Command show");
		} else if (command.contains("create")) {
			target = command.split(" ");
			System.out.println("Command " + target[0] + " " + target[1]);
			System.out.println("Command show");
			create(target);
		}
		sc.close();
	}
	
	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		CLI_UI cmd = new CLI_UI();
		
		System.out.println("-- Command Line Interface --");
		while(!cmd.exit()) {
			System.out.print("cli_db > ");
			cmd.enterCommand();
		}
	}

}

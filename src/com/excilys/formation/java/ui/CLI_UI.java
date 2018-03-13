package com.excilys.formation.java.ui;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.formation.java.mapper.CompanyMP;
import com.excilys.formation.java.mapper.ComputerMP;
import com.excilys.formation.java.service.WebServiceCompany;
import com.excilys.formation.java.service.WebServiceComputer;

public class CLI_UI {
	private boolean exit;
	private String command;
	private WebServiceComputer wscmp;
	private WebServiceCompany wscpy;
	protected Scanner sc;
	
	
	public CLI_UI() {
		this.exit = false;
		this.command = "";
		this.wscmp = WebServiceComputer.getInstance();
		this.wscpy = WebServiceCompany.getInstance();
		this.sc = new Scanner(System.in);
	}
	
	public  boolean exit() {
		return exit;
	}
	
	public void showDetailComputer(int id) {
		System.out.println(wscmp.getComputerDB().getComputerByID(id));
	}
	
	public void list(String[] t) {
		boolean listAll =  t[1].equals("all");
		String table = listAll ? t[2] : t[1];
		if("computer".equals(table)) {
			ArrayList<ComputerMP> cmpList = null;
			if(listAll) {
				// Programmation fonctionnel ? Peut etre. Envisager...
				cmpList = wscmp.getComputerDB().getComputerList();
			} else {
				// Gerer les cas from to
				cmpList = wscmp.getComputerDB().getComputerList(0, 10);
			}

			for(ComputerMP cmp : cmpList) {
				System.out.println(cmp);
			}
		} else if("company".equals(table)){
			ArrayList<CompanyMP> cpyList = null;
			
			if(listAll) {
				// Programmation fonctionnel ? Peut etre. Envisager...
				cpyList = wscpy.getCompanyDB().getCompanyList();
			} else {
				// Gerer les cas from to
				cpyList = wscpy.getCompanyDB().getCompanyList(0, 10);
			}
			for(CompanyMP cpn : cpyList) {
				System.out.println(cpn);
			}
		}
	}
	
	public void create(String[] t) {
		boolean into = t[1].equals("into");
		String table = into ? t[2] : t[1];
		System.out.println("Enter the tuple corresponding:");
		if("computer".equals(table)) {
			// Ici il faudra check la forme et les valeurs de chaque ajout
			// Valeur null, erreur de syntaxe, nombre de champ
			String s = sc.nextLine();
			String[] ss = s.replaceAll("[( | )]", " ").split(",");		
			wscmp.create(ss);
		} else if("company".equals(table)) {
			String s = sc.nextLine();
			String[] ss = s.replaceAll("[( | )]", " ").split(",");			
			wscpy.create(Integer.valueOf(ss[0]), ss[1]);
		}
	}
	
	public void enterCommand() {
		command = sc.nextLine();
		String[] target = null;
		
		if("exit".equals(command) || "quit".equals(command)){
			exit = true;
		} else if("help".equals(command)) {
			// On peut faire plus propre comme choix d'affichage d'options ( ArgParse )
			System.out.println("Commands:");
			System.out.println("show table_name id:     show details of instance in table_name where ID=id");
			System.out.println("list [all] table_name:  list (all) instances in table_name");
			System.out.println("create into table_name:  create a instance in table_name."
					+ "\nThis require you to provide a tuple containing the values of what defines your object.");
			
		} else if (command.contains("list")) {
			// Ici target = objet visé par la requete i.e une des tables 
			// i.e la suite de la chaine command, après le mot list	
			target = command.split(" ");
			list(target);
			
		} else if (command.contains("show")) {
			target = command.split(" ");
			System.out.println("Command show");
			int id = Integer.valueOf(target[2]);
			showDetailComputer(id);
			
		} else if (command.contains("create")) {
			target = command.split(" ");
			System.out.println("Command create");
			create(target);
		}
	}
	
	public static void main(String args[]) {
		CLI_UI cmd = new CLI_UI();
		System.out.println("-- Command Line Interface --");
		System.out.println("Type help for more informations");
		while(!cmd.exit()) {
			System.out.print("cli_db > ");
			cmd.enterCommand();
		}
		System.out.print("Exit succes");
		cmd.sc.close();		
	}

}

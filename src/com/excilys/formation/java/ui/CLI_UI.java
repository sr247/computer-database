package com.excilys.formation.java.ui;

import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.formation.java.exceptions.IncorrectFieldException;
import com.excilys.formation.java.exceptions.InstanceNotFoundException;
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.model.Computer;
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
	
	public boolean exit() {
		return exit;
	}
	
	public void showDetailComputer(String id) {
		System.out.println(wscmp.getComputer(id));
	}
	
	public void list(String[] t) {
		boolean listAll =  t[1].equals("all");
		String table = listAll ? t[2] : t[1];
		if("computer".equals(table)) {
			ArrayList<Computer> cmpList = null;
			if(listAll) {
				cmpList = wscmp.getList(0, 0);
			} else {
				cmpList = wscmp.getList(0, 10);
			}
			for(Computer cmp : cmpList) {
				System.out.println(cmp);
			}
		} else if("company".equals(table)){
			ArrayList<Company> cpyList = null;
			if(listAll) {
				cpyList = wscpy.getList(0, 0);
			} else {
				cpyList = wscpy.getList(0, 10);
			}
			for(Company cpn : cpyList) {
				System.out.println(cpn);
			}
		}
	}
	
	public void create(String[] t) {
		boolean err = false;
		boolean exit = false;
		String table = t[1];
		
		do{
			if("computer".equals(table)) {
				// Ici il faudra check la forme et les valeurs de chaque ajout
				// Valeur null, erreur de syntaxe, nombre de champs
				ArrayList<String> fields = new ArrayList<String>();
				System.out.print("Entrez le nom: ");
				fields.add(sc.nextLine());
				System.out.print("Entrez la date d'achat: ");
				fields.add(sc.nextLine());
				System.out.print("Entrez la date de fin de production: ");
				fields.add(sc.nextLine());
	
				// Ici une formulation différente 
				System.out.print("Entrez l'id de l'entreprise propritaire: ");			
				fields.add(sc.nextLine());
				err = false;
				try {
					wscmp.createComputer(fields);
				}catch(IncorrectFieldException e) {
					System.err.println("Erreur: Champ invalide");
					err = true;
				}catch (InstanceNotFoundException e) {
					System.err.println("Erreur: Instance inexistante");
					err = true;
				}
			}
		}while(err & !exit);
	}
	
	public void delete(String id) {
		wscmp.deleteComputer(id);
	}
	
	public void update(String[] t) {
		boolean err = false;
		boolean exit = false;
		String table = t[1];
		
		do{
			if("computer".equals(table)) {
				// Ici il faudra check la forme et les valeurs de chaque ajout
				// Valeur null, erreur de syntaxe, nombre de champ
				
				ArrayList<String> fields = new ArrayList<String>();
				System.out.print("Entrez une valeur pour le champ à mettre à jour. Laisser le champ vide pour ne rien modifier: ");
				System.out.print("Entrez le nom: ");
				fields.add(sc.nextLine());
				System.out.print("Entrez la date d'achat: ");
				fields.add(sc.nextLine());
				System.out.print("Entrez la date de fin de production: ");
				fields.add(sc.nextLine());
	
				// ici une formulation différente 
				System.out.print("Entrez l'id de l'entreprise propritaire: ");			
				fields.add(sc.nextLine());
				err = false;
				try {
					wscmp.updateComputer(fields, t[2]);
				}catch(IncorrectFieldException e) {
					System.out.println("Erreur: Champ invalide");
					err = true;
				}catch (InstanceNotFoundException e) {
					System.err.println("Erreur: Instance inexistante");
					err = true;
				}
			}
		}while(err & !exit);
	}
	
	public void enterCommand() {
		command = sc.nextLine();
		String[] target = null;
		
		if("exit".equals(command) || "quit".equals(command)){
			exit = true;
		} else if("help".equals(command)) {
			// On peut faire plus propre comme choix d'affichage d'options ( ArgParse )
			System.out.println("Commandes:");
			System.out.println("show table_name id:          show details of instance in table_name where ID=id");
			System.out.println("list [all] table_name:       list (all) instances in table_name");
			System.out.println("create table_name:      create a instance in table_name."
					+ "\n\t\t\t     This require you to provide a value for each attribute of the object.");
			
			System.out.println("delete [table name] id:      delete the instance in table_name where ID=id");
			System.out.println("update [table name] id:      update the instance in table_name where ID=id"
					+ "\n\t\t\t     This require you to provide a value for each attribute of the object.");
			
		} else if (command.contains("list")) {
			target = command.split(" ");
			if(target.length > 1)
				list(target);
			
		} else if (command.contains("show")) {
			target = command.split(" ");
			System.out.println("Command show");
			if(target.length > 2)
				showDetailComputer(target[2]);
			
		} else if (command.contains("create")) {
			target = command.split(" ");
			System.out.println("Command create");
			if(target.length > 1)
				create(target);
			
		} else if (command.contains("update")) {
			target = command.split(" ");
			System.out.println("Command delete");
			if(target.length > 2)
				update(target);
			
		} else if (command.contains("delete")) {
			target = command.split(" ");
			System.out.println("Command delete");
			if(target.length > 2)
				delete(target[2]);
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

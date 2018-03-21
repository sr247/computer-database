package com.excilys.formation.cdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.InstanceNotFoundException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Pages;
import com.excilys.formation.cdb.model.PagesCompany;
import com.excilys.formation.cdb.model.PagesComputer;
import com.excilys.formation.cdb.service.WebServiceCompany;
import com.excilys.formation.cdb.service.WebServiceComputer;

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
	
	private void printList(boolean all, Pages p) {
		if(all) {
			for(Object c : p.getPage()) {
				System.out.println(c);
			}
		} else {
			boolean still = true;
			while(still) {
				for(Object c : p.getPage()) {
					System.out.println(c);
				}
				System.out.println("Page " + p.getNum());
				System.out.println("Type n for next, p for precedent, q for quit: ");
				String reponse = sc.nextLine();
				if(reponse.equals("n")) {
					p.next();
				}else if(reponse.equals("p")){
					p.preview();
				}else if(reponse.equals("q")){
					still = false;
				}
			}
		}
		}
	
	public void list(String[] t) {
		boolean all =  t[1].equals("all");
		String table = all ? t[2] : t[1];
		if("computer".equals(table)) {
			PagesComputer p = null;
			if(all) {
				p = new PagesComputer(wscmp.getAllList());
			}else {
				p = new PagesComputer(wscmp.getList(Pages.getFrom(), Pages.getTo()));
			}			
			printList(all, p);
			
		} else if("company".equals(table)){
			PagesCompany p = null;
			if(all) {
				p = new PagesCompany(wscpy.getAllList());
			}else {
				p = new PagesCompany(wscpy.getList(Pages.getFrom(), Pages.getTo()));
			}
			printList(all, p);
		}
	}
	
	public void checkSyntaxes(List<String> fields) throws IncorrectFieldException {
		try {
			DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
			LocalDate.parse(fields.get(1), fmt);
			LocalDate.parse(fields.get(2), fmt);
			Integer.parseInt(fields.get(3));
		}catch (DateTimeParseException|NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new IncorrectFieldException();
		}
	}
	
	private List<String> getEntry(){
		List<String> fields = new ArrayList<String>();
		System.out.print("Entrez le nom: ");
		fields.add(sc.nextLine());
		System.out.print("Entrez la date d'achat:(dd/mm/aaaa) ");
		fields.add(sc.nextLine());
		System.out.print("Entrez la date de fin de production:(dd/mm/aaaa) ");
		fields.add(sc.nextLine());
		System.out.print("Entrez l'id de l'entreprise propritaire:");			
		fields.add(sc.nextLine());
		return fields;
	}
	
	private void create(String[] t) {
		boolean err = false;
		boolean exit = false;
		String table = t[1];
		
		do{
			if("computer".equals(table)) {
				List<String> fields = new ArrayList<String>();
				fields = getEntry();
				
				try {
					checkSyntaxes(fields);
					LocalDate d1 = null;
					LocalDate d2 = null;
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
					d1 = LocalDate.parse(fields.get(1), fmt);
					d2 = LocalDate.parse(fields.get(2), fmt);
					Company cmpny = wscpy.getCompany(fields.get(3));					
					Computer cmp = new Computer(fields.get(0), d1, d2, cmpny);
					wscmp.createComputer(cmp);
					err = false;
				}catch(DateTimeParseException|IncorrectFieldException e) {
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
				
				List<String> fields = new ArrayList<String>();
				fields = getEntry();
				
				try {
					checkSyntaxes(fields);
					LocalDate d1 = null;
					LocalDate d2 = null;
					DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/uuuu");
					d1 = LocalDate.parse(fields.get(1), fmt);
					d2 = LocalDate.parse(fields.get(2), fmt);
					Company cmpny = wscpy.getCompany(fields.get(3));					
					Computer cmp = new Computer(fields.get(0), d1, d2, cmpny);
					err = false;
					wscmp.updateComputer(cmp);
					err = false;
				}catch(DateTimeParseException|IncorrectFieldException e) {
					System.err.println("Erreur: Champ invalide");
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
			System.out.println("create table_name:           create a instance in table_name."
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

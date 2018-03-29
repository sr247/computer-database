package com.excilys.formation.cdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.pages.Pages;
import com.excilys.formation.cdb.pages.PagesCompany;
import com.excilys.formation.cdb.pages.PagesComputer;
import com.excilys.formation.cdb.service.WebServiceCompany;
import com.excilys.formation.cdb.service.WebServiceComputer;

public class CLI_UI {
	private boolean exit;
	private String command;
	private WebServiceComputer wscmp;
	private WebServiceCompany wscpy;
	protected Scanner sc;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CLI_UI.class);
	
	public CLI_UI() {
		this.exit = false;
		this.command = "";
		this.wscmp = WebServiceComputer.INSTANCE;
		this.wscpy = WebServiceCompany.INSTANCE;
		this.sc = new Scanner(System.in);
	}
	
	public boolean exit() {
		return exit;
	}
	
	public void showDetailComputer(String id) {
		int i = Integer.valueOf(id);
		try {
			System.out.println(wscmp.getComputer(i));
		} catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage(), e);
		}
	}
	
	private void printList(boolean all, Pages<?> p) {
		if(all) {
			for(Object c : p.getContent()) {
				System.out.println(c);
			}
		} else {
			boolean still = true;
			while(still) {
				for(Object c : p.getContent()) {
					System.out.println(c);
				}
				System.out.println("Page " + Pages.getCURRENT_PAGE().get());
				System.out.println("Type n for next, p for precedent, q for quit: ");
				String reponse = sc.nextLine();
				try {
					if(reponse.equals("n")) {
						p.next();
					}else if(reponse.equals("p")){
						p.preview();
					}else if(reponse.equals("q")){
						still = false;
					}
				}catch (ServiceManagerException e) {
					// TODO Auto-generated catch block
					logger.warn(e.getMessage(), e);
				}
			}
		}
		p.reset();
	}
	
	public void list(String[] t) throws ServiceManagerException {
		boolean all =  t[1].equals("all");
		String table = all ? t[2] : t[1];
		if("computer".equals(table)) {
			PagesComputer<?> p = null;
			if(all) {
				p = new PagesComputer<>(wscmp.getAllList());
			}else {
				int offset = Pages.getPAGE_OFFSET();
				int stride = Pages.getPAGE_LIMIT();
				p = new PagesComputer<>(wscmp.getList(offset, stride));
			}			
			printList(all, p);
			
		} else if("company".equals(table)){
			PagesCompany<?> p = null;
			if(all) {
				p = new PagesCompany<>(wscpy.getAllList());
			}else {
				int offset = Pages.getPAGE_OFFSET();
				int stride = Pages.getPAGE_LIMIT();
				p = new PagesCompany<>(wscpy.getList(offset, stride));
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
			logger.warn(e.getMessage(), e);
			throw new IncorrectFieldException("Champ invalide!");
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
	
	private void create(String[] t) throws ServiceManagerException {
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
					logger.warn(e.getMessage(), e);
					err = true;
				}
			}
		}while(err & !exit);
	}
	
	public void delete(String id) throws ServiceManagerException {
		int i = Integer.valueOf(id);
		wscmp.deleteComputer(i);
	}
	
	public void update(String[] t) throws ServiceManagerException {
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
					err = false;
					wscmp.updateComputer(cmp);
					err = false;
				}catch(DateTimeParseException|IncorrectFieldException e) {
					logger.warn(e.getMessage(), e);
					err = true;
				}
			}
		}while(err & !exit);
	}
	
	public void enterCommand() {
		command = sc.nextLine();
		String[] target = null;
		try {
			if("exit".equals(command) || "quit".equals(command)){
				exit = true;
			} else if("help".equals(command)) {
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
		}catch (ServiceManagerException e) {
			// TODO Auto-generated catch block
			logger.warn(e.getMessage(), e);
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

package com.excilys.formation.cdb.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.exceptions.IncorrectFieldException;
import com.excilys.formation.cdb.exceptions.ServiceManagerException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.pages.Pages;
import com.excilys.formation.cdb.pages.PagesCompany;
import com.excilys.formation.cdb.pages.PagesComputer;
import com.excilys.formation.cdb.service.ServiceCompany;
import com.excilys.formation.cdb.service.ServiceComputer;

public class CliUI {
	private boolean exit;
	private String command;
	
	@Autowired
	private ServiceComputer wscmp;
	@Autowired
	private ServiceCompany wscpy;
	
	private static final String COMPUTER_TABLE = "computer";
	
	protected Scanner sc;
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CliUI.class);
	
	public CliUI() {
		this.exit = false;
		this.command = "";
		this.sc = new Scanner(System.in);
	}
	
	public boolean exit() {
		return exit;
	}
	
	private void displayHelp() {
		System.out.println("Commandes:");
		System.out.println("show table_name id:          show details of instance in table_name where ID=id");
		System.out.println("list [all] table_name:       list (all) instances in table_name");
		System.out.println("create table_name:           create a instance in table_name."
				+ "\n\t\t\t     This require you to provide a value for each attribute of the object.");
		
		System.out.println("delete [table name] id:      delete the instance in table_name where ID=id");
		System.out.println("update [table name] id:      update the instance in table_name where ID=id"
				+ "\n\t\t\t     This require you to provide a value for each attribute of the object.");
	}
	
	public void showDetailComputer (String id) {
		int i = Integer.parseInt(id);
		try {
			System.out.println(wscmp.getComputer(i));
		} catch (ServiceManagerException e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	private void printList (boolean all, Pages<?> p) {
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
				System.out.println("Page " + (Pages.getCURRENT_PAGE().isPresent() ? Pages.getCURRENT_PAGE().get() : "None"));
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
					logger.warn(e.getMessage(), e);
				}
			}
		}
		p.reset();
	}
	
	public void list(String[] t) throws ServiceManagerException {
		boolean all =  t[1].equals("all");
		String table = all ? t[2] : t[1];
		if(COMPUTER_TABLE.equals(table)) {
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
			logger.warn(e.getMessage(), e);
			throw new IncorrectFieldException("Champ invalide!");
		}
	}
	
	private List<String> getEntry(){
		List<String> fields = new ArrayList<>();
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
		exit = false;
		String table = t[1];
		
		do{
			if(COMPUTER_TABLE.equals(table)) {
				List<String> fields = null;
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
		}while(err && !exit);
	}
	
	public void delete(String[] t) throws ServiceManagerException {
		int i = Integer.parseInt(t[2]);
		String table = t[1];
		if(COMPUTER_TABLE.equals(table)) {
			wscmp.deleteComputer(i);
		}else if ("company".equals(table)) {
			wscpy.deleteCompany(t[2]);
		}
	}
	
	public void update(String[] t) throws ServiceManagerException {
		boolean err = false;
		String table = t[1];
		do{
			if(COMPUTER_TABLE.equals(table)) {
				List<String> fields = null;
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
					wscmp.updateComputer(cmp);
					err = false;
				}catch(DateTimeParseException|IncorrectFieldException e) {
					logger.warn(e.getMessage(), e);
					err = true;
				}
			}
		}while(err && !exit);
	}
	
	public void mainLoop () {
		command = sc.nextLine();
		String[] target = null;
		try {
			if("exit".equals(command) || "quit".equals(command)){
				exit = true;
			} else if("help".equals(command)) {
				displayHelp();
				
			} else if (command.contains("list")) {
				target = command.split(" ");
				if(target.length > 1)
					list(target);
				
			} else if (command.contains("show")) {
				target = command.split(" ");
				if(target.length > 2)
					showDetailComputer(target[2]);
				
			} else if (command.contains("create")) {
				target = command.split(" ");
				if(target.length > 1)
					create(target);
				
			} else if (command.contains("update")) {
				target = command.split(" ");
				if(target.length > 2)
					update(target);
				
			} else if (command.contains("delete")) {
				target = command.split(" ");
				if(target.length > 2)
					delete(target);
			}
		}catch (ServiceManagerException e) {
			logger.warn(e.getMessage(), e);
		}
	}
	
	public static void main(String args[]) {
		CliUI cmd = new CliUI();
		System.out.println("-- Command Line Interface --");
		System.out.println("Type \"help\" for more informations");		
		while(!cmd.exit()) {
			System.out.print("cli_db > ");
			cmd.mainLoop();
		}
		System.out.print("Exit succes");
		cmd.sc.close();	
	}

}

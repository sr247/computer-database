package com.excilys.formation.java.service;

import java.sql.Date;

// Cette classe devient un singleton
public class ValidateComputer {
	
	
	public static boolean check(String[] fields) {
		boolean res = true;
		res &= checkName(fields[0]);		
		res &= checkDate(fields[1]);
		res &= checkDate(fields[2]);
		res &= checkForeignKey(fields[3]);
		return res;
	}
	
	private static boolean checkDate(String dt) {
		boolean ok = true;
		
		try {
			Date date = Date.valueOf(dt);						
		} catch(IllegalArgumentException e) {
			System.err.println("Invalid format date.");
			ok = false;
		}
		return ok;
	}
	
	private static boolean checkName(String nm) {
		boolean ok = true;
		// Ici on check une belle regex pour elever les symboles bizarre, ou pas
		return ok;
	}
	
	private static boolean checkForeignKey(String fk) {
		boolean ok = true;
		
		return ok;
	}

}

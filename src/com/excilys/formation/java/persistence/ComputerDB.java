package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.print.attribute.standard.PrinterMessageFromOperator;

import com.excilys.formation.java.mapper.ComputerMP;
import com.mysql.jdbc.PreparedStatement;

public class ComputerDB extends ConnexionDB {
	
	private static int numComputers = -1;
	
	public int getNumComputers() {
		synchronized(conn) {
			if (numComputers == -1)
			{
				Statement s;
				try {
					s = conn.createStatement();
					ResultSet res = s
							.executeQuery("SELECT COUNT(*) AS NUM FROM computer");
					res.next();
					numComputers = res.getInt("NUM");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return numComputers;
	}
	
	public ArrayList<ComputerMP> getComputerList(int from, int to) {
		
		ArrayList<ComputerMP> computers = new ArrayList<ComputerMP>();
		try {
			// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement("SELECT * FROM computer"
							+ " ORDER BY ID"
							+ " LIMIT ? OFFSET ?");
			ps.setInt(1, to-from);
			ps.setInt(2, from);
			ResultSet res = ps.executeQuery();
			
			while (res.next())
				computers.add(ComputerMP.map(res));
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return computers;
	}
	
	public ArrayList<ComputerMP> getComputerList() {
		
		ArrayList<ComputerMP> computers = new ArrayList<ComputerMP>();
		try {
			// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement("SELECT * FROM computer"
							+ " ORDER BY ID");
			ResultSet res = ps.executeQuery();
			
			while (res.next())
				computers.add(ComputerMP.map(res));
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return computers;
	}
	
	public ComputerMP getComputerByID(int id) {
		PreparedStatement ps = null;
		ResultSet res = null;
		try {
			ps = (PreparedStatement) 
					conn.prepareStatement("SELECT * FROM computer"
							+ " WHERE ID=?");
			ps.setInt(1, id);
			res = ps.executeQuery();
			
			res.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ComputerMP.map(res);
	}
	
	
	public void create(String name, Date introduced, Date discontinued, int company_id) {
		PreparedStatement crt;
		int id = getNumComputers()+1;
		try {
			crt = (PreparedStatement) 
					conn.prepareStatement("INSERT INTO computer (NAME, INTRODUCED, DISCONTINUED, COMPANY_ID)"
					+ "VALUES 	(?, ?, ?, ?)");				
			crt.setString(1, name);
			
			//to be functionalized
			if(introduced == null) {
				crt.setNull(2, java.sql.Types.DATE);
			} else {
				crt.setDate(2, introduced);				
			} 
			// to be functionalized
			if(discontinued == null) {
				crt.setNull(3, java.sql.Types.DATE);
			}else {
				crt.setDate(3, discontinued);
			}
			
			// Essayer d'integrer ce test dans la validation plutot
			PreparedStatement checkFk = (PreparedStatement)
					conn.prepareStatement("SELECT * FROM company"
							+ " WHERE ID=?");
			checkFk.setInt(1, company_id);
			ResultSet res = checkFk.executeQuery();
			if(res.next()) {
				crt.setInt(4, company_id);
			}else {
				throw new SQLException("Instance not found in company");
			}
			
			crt.executeUpdate();
			//Can't call commit, when autocommit:true
			//conn.commit();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		ComputerMP cmp = new ComputerMP(id, name, introduced, discontinued, company_id);
		System.out.println("Created:" + cmp);
	}
	
	/*
	 * Ici check : Field doit déja convenir au champs équivalent requis dans la table.
	 * Et n'oubli pas de faire des Test Genre MAINTENANT !
	 */
	public void update(String field, ComputerMP cmp) {
		PreparedStatement upd;
		try {
			upd = (PreparedStatement) conn.prepareStatement("UPDATE computer "
					+ "SET ?=?"
					+ "WHERE ID=?");		
	
			upd.setString(1, field);
			if("NAME".equals(field)) {
				upd.setString(2, cmp.getName());
			}
			else if("INTRODUCED".equals(field)){
				upd.setDate(2, cmp.getIntroduced());
			}
			else if ("DISCONTINUED".equals(field)) {
				upd.setDate(2, cmp.getDiscontinued());
			}
			else if("COMPANY_ID".equals(field)) {
				upd.setInt(2, cmp.getCompanyId());
			}
			upd.setInt(3, cmp.getId());
			upd.executeUpdate();
			//conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Updated:" + cmp);
	}
	
	public void delete(ComputerMP cmp, Connection conn) {
		try {
			PreparedStatement del = (PreparedStatement) conn.prepareStatement("DELETE FROM computer"
					+ " WHERE ID=?");
			del.setInt(1, cmp.getId());
			del.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Deleted:" + cmp);
	}
}

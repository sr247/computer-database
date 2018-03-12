package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.mapper.ComputerMP;
import com.mysql.jdbc.PreparedStatement;

public class ComputerDB {
	private int numComputers;
	
	
	public int getNumComputers(Connection conn) throws SQLException {
		synchronized(conn) {
			if (numComputers == -1)
			{
				Statement s = conn.createStatement();
				ResultSet res = s
						.executeQuery("SELECT COUNT(*) AS NUM FROM company");
				res.next();
				numComputers = res.getInt("NUM");
			}
		}
		return numComputers;
	}
	
	public ArrayList<ComputerMP> getComputerList(Connection conn) throws SQLException {
		
		ArrayList<ComputerMP> computers = new ArrayList<ComputerMP>();
		// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
		Statement s = conn.createStatement();
		ResultSet res = s.executeQuery("SELECT * FROM computer ORDER BY ID");
		
		while (res.next())
			computers.add(ComputerMP.map(res));
	
		return computers;
	}
	
	
	public void create(ComputerMP cp, Connection conn) throws SQLException {
		PreparedStatement crt = (PreparedStatement) conn.prepareStatement("INSERT INTO computer (ID, NAME, INTRODUCED, DISCONTINUED, COMPANY_ID)"
				+ "VALUES 	(?, ?, ?, ?, ?)");
		
		crt.setInt(1, cp.getId());
		crt.setString(2, cp.getName());
		crt.setDate(3, cp.getIntroduced());
		crt.setDate(4, cp.getDiscontinued());
		crt.setInt(5, cp.getCompanyId());
		
		crt.executeUpdate();
		conn.commit();
		
	}
	
	/*
	 * Ici check : Field doit déja convenir au champs équivalent requis dans la table.
	 * Et n'oubli pas de faire des Test Genre MAINTENANT !
	 */
	
	public void update(String field, ComputerMP cp, Connection conn) throws SQLException {
		PreparedStatement upd = (PreparedStatement) conn.prepareStatement("UPDATE computer "
				+ "SET ?=?"
				+ "WHERE ID=?");
		
	
		upd.setString(1, field);
		if("NAME".equals(field)) {
			upd.setString(2, cp.getName());
		}
		else if("INTRODUCED".equals(field)){
			upd.setDate(2, cp.getIntroduced());
		}
		else if ("DISCONTINUED".equals(field)) {
			upd.setDate(2, cp.getDiscontinued());
		}
		else if("COMPANY_ID".equals(field)) {
			upd.setInt(2, cp.getCompanyId());
		}
		upd.setInt(3, cp.getId());
		
		upd.executeUpdate();
		conn.commit();
		
	}
}

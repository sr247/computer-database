package com.excilys.formation.java.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.mapper.ComputerMP;
import com.mysql.jdbc.PreparedStatement;

public class ComputerDB {
	
	private static int numComputers = -1;
	
	public int getNumComputers(Connection conn) throws SQLException {
		synchronized(conn) {
			if (numComputers == -1)
			{
				Statement s = conn.createStatement();
				ResultSet res = s
						.executeQuery("SELECT COUNT(*) AS NUM FROM computer");
				res.next();
				numComputers = res.getInt("NUM");
			}
		}
		return numComputers;
	}
	
	public ArrayList<ComputerMP> getComputerList(Connection conn, int from, int to) {
		
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
	
	public ArrayList<ComputerMP> getComputerList(Connection conn) {
		
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
	
	public ComputerMP getComputer(int id, Connection conn) {
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
	
	public void create(ComputerMP cmp, Connection conn) {
		PreparedStatement crt;
		try {
			crt = (PreparedStatement) conn.prepareStatement("INSERT INTO computer (ID, NAME, INTRODUCED, DISCONTINUED, COMPANY_ID)"
					+ "VALUES 	(?, ?, ?, ?, ?)");				
			crt.setInt(1, cmp.getId());
			crt.setString(2, cmp.getName());
			crt.setDate(3, cmp.getIntroduced());
			crt.setDate(4, cmp.getDiscontinued());
			crt.setInt(5, cmp.getCompanyId());
			crt.executeUpdate();
			//Can't call commit, when autocommit:true
			//conn.commit();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Created:" + cmp);
	}
	
	/*
	 * Ici check : Field doit déja convenir au champs équivalent requis dans la table.
	 * Et n'oubli pas de faire des Test Genre MAINTENANT !
	 */
	public void update(String field, ComputerMP cmp, Connection conn) {
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

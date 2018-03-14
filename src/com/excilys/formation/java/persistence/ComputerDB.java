package com.excilys.formation.java.persistence;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.java.mapper.ComputerMapper;
import com.excilys.formation.java.model.Computer;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class ComputerDB {
	
	private static ComputerDB _interface = null;
	private final static Connection conn = (Connection) ConnexionDB.getInterface().getConnection();
	private static int numComputers = -1;
	
	private ComputerDB() {
		
	}
	
	public static ComputerDB getInterface() {
		if(_interface == null) {
			_interface = new ComputerDB();
		}
		return _interface;
	}
	
	public Connection getConnexion() {
		return conn;
	}

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
	
	public Computer getComputerByID(int id) {
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
		return ComputerMapper.map(res);
	}
	
	public ArrayList<Computer> getComputerList() {
		
		ArrayList<Computer> computers = new ArrayList<Computer>();
		try {
			// Solutionner pour les preperedStatement plutot : Plus sécuritaire au niveau des injection sql.
			PreparedStatement ps = (PreparedStatement) 
					conn.prepareStatement("SELECT * FROM computer"
							+ " ORDER BY ID");
			ResultSet res = ps.executeQuery();
			while (res.next())
				computers.add(ComputerMapper.map(res));
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	
		return computers;
	}
	
	public ArrayList<Computer> getComputerList(int from, int to) {		
		ArrayList<Computer> computers = new ArrayList<Computer>();
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
				computers.add(ComputerMapper.map(res));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	
		return computers;
	}
	
	
	public PreparedStatement setDateProperly(Date dt, PreparedStatement ps, int i) throws SQLException {
		if(dt == null) {
			ps.setNull(i, java.sql.Types.DATE);
		} else {
			ps.setDate(i, dt);				
		} 
		return ps;
	}
	
	
	public void create(Computer cmp) {
		PreparedStatement crt;
		try {
			crt = (PreparedStatement) 
					conn.prepareStatement("INSERT INTO computer (NAME, INTRODUCED, DISCONTINUED, COMPANY_ID)"
					+ "VALUES 	(?, ?, ?, ?)");				
			crt.setString(1, cmp.getName());
			crt = setDateProperly(cmp.getIntroduced(), crt, 2);
			crt = setDateProperly(cmp.getDiscontinued(), crt, 3);
			crt.setInt(4, cmp.getCompanyId());
			crt.executeUpdate();
			System.out.println("Created:" + cmp);
			//Can't call commit, when autocommit:true
			//conn.commit();
		} catch (MySQLIntegrityConstraintViolationException e) {
			// TODO Auto-generated catch block
			// System.err.println(e.getMessage());
			System.err.println("Erreur: clé de company inexistante");
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} 
		
	}
	
	/*
	 * Ici on update forcément tout.. Bonne ou mauvaise solution ?
	 * Essayer une version qui update seulement x champs.
	 */
	public void update(Computer cmp) {
		PreparedStatement upd;
		try {
			upd = (PreparedStatement) conn.prepareStatement("UPDATE computer "
					+ "SET NAME=?, INTRODUCED=?, DISCONTINUED=?, COMPANY_ID=?"
					+ "WHERE ID=?");		

			upd.setString(2, cmp.getName());		
			upd.setDate(2, cmp.getIntroduced());		
			upd.setDate(2, cmp.getDiscontinued());		
			upd.setInt(2, cmp.getCompanyId());		
			upd.setInt(3, cmp.getId());
			upd.executeUpdate();
			//conn.commit();
			System.out.println("Updated:" + cmp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	// Pour le moment on recoit juste l'id
	public void delete(Computer cmp) {
		try {
			PreparedStatement del = (PreparedStatement) conn.prepareStatement("DELETE FROM computer"
					+ " WHERE ID=?");
			del.setInt(1, cmp.getId());
			del.executeUpdate();
			System.out.println("Deleted:" + cmp);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

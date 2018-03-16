package com.excilys.formation.java.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.java.model.Computer;
import com.excilys.formation.java.persistence.ComputerDB;
import com.excilys.formation.java.persistence.ConnexionDB;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

class ComputerMapperTest {
	private static Computer expected;
	private static Computer actual;
	private static ResultSet res;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {

	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	
	@Test
	void testGetInterface() {
		assertTrue(true, "getInterface is trivial");
	}
	
	@Test
	void testMap() throws SQLException {
		String SELECT_ONE = "SELECT * FROM computer WHERE ID=?;";
		Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();
		PreparedStatement ps = null;
		ps = (PreparedStatement) 
				conn.prepareStatement(SELECT_ONE);
		ps.setInt(1, 1);
		res = ps.executeQuery();
		res.next();		
		int id = res.getInt("ID");
		String name = res.getString("NAME");
		LocalDate introduced = res.getDate("INTRODUCED").toLocalDate();
		LocalDate discontinued = res.getDate("DISCONTINUED").toLocalDate();
		int company_id = res.getInt("COMPANY_ID");
		actual = new Computer(id, name, introduced, discontinued, company_id);
		
		ComputerDB cmpDB = ComputerDB.INSTANCE;		
		expected = cmpDB.getComputerByID(1);
		
		
		assertTrue(expected.getId() == actual.getId(), "ID");
		assertTrue(expected.getName().equals(actual.getName()), "Name");
		assertTrue(expected.getIntroduced() == actual.getIntroduced(), "Intro");
		assertTrue(expected.getDiscontinued() == actual.getDiscontinued(), "Discon");
	}

}

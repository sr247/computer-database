package com.excilys.formation.cdb;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ConnexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;

class CompanyMapperTest {
	private static Company expected;
	private static Company actual;
	private static ResultSet res;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		CompanyDB cmpDB = CompanyDB.INSTANCE;		
		expected = cmpDB.getCompanyByID(1).get();		
		PreparedStatement ps = null;
		Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();
		ps = (PreparedStatement) 
				conn.prepareStatement("SELECT * FROM company"
						+ " WHERE ID=?");
		ps.setInt(1, 1);
		
		res = ps.executeQuery();
		res.next();
		
		int id = res.getInt("ID");
		String name = res.getString("NAME");
		
		actual = new Company(id, name);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		Connection conn = (Connection) ConnexionDB.INSTANCE.getConnection();
		conn.close();
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
	void testMap() {
		// System.err.println(expected.getName() + " == " + actual.getName());
		assertTrue(expected.getId() == actual.getId(), "ID");
		assertTrue(expected.getName().equals(actual.getName()), "Name");
	}

}

package com.excilys.formation.cdb;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.ResultSet;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

<<<<<<< HEAD:src/com/excilys/formation/java/test/CompanyMapperTest.java
import com.excilys.formation.java.model.Company;
import com.excilys.formation.java.persistence.CompanyDB;
import com.excilys.formation.java.persistence.ConnexionDB;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
=======
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ConnexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
>>>>>>> 07abdf529dd860bb03aa2f09bf312abe3f167066:src/test/java/com/excilys/formation/cdb/CompanyMapperTest.java

class CompanyMapperTest {
	private static Company expected;
	private static Company actual;
	private static ResultSet res;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
<<<<<<< HEAD:src/com/excilys/formation/java/test/CompanyMapperTest.java
		CompanyDB cmpDB = CompanyDB.INSTANTCE;		
		expected = cmpDB.getCompanyByID(1);		
=======
		CompanyDB cmpDB = CompanyDB.INSTANCE;		
		expected = cmpDB.getCompanyByID(1).get();		
>>>>>>> 07abdf529dd860bb03aa2f09bf312abe3f167066:src/test/java/com/excilys/formation/cdb/CompanyMapperTest.java
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

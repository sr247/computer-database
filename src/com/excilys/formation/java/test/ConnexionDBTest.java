package com.excilys.formation.java.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.java.persistence.ConnexionDB;
import java.sql.Connection;
import java.sql.SQLException;

class ConnexionDBTest {

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
		ConnexionDB connDB = ConnexionDB.INSTANCE;
		assertEquals(connDB.getClass(), ConnexionDB.class);
	}

	@Test
	void testGetConnection() throws SQLException {
		ConnexionDB connDB = ConnexionDB.INSTANCE;
		assertNotEquals(connDB.getConnection(), null);
		Connection conn = connDB.getConnection();
		assertEquals(conn.isValid(0), true);
		
	}

}

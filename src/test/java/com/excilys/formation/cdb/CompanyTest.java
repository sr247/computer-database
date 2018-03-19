package com.excilys.formation.cdb;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;

class CompanyTest {

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
	void testCompany() {
		String name = "Apple";
		int id = 1;		
		assertTrue(name == "Apple");
		assertTrue(id == 1);
		Company cpy = new Company(id, name);
		assertTrue(cpy.getName() == "Apple");		
		assertTrue(cpy.getId() == 1);
	}

	@Test
	void testToString() {
		String name = "Apple";
		int id = 1;
		Company cpy = new Company(id, name);
		String s = "Company:("
				+ "id=" + 1 + ", "
				+ "name=" + "Apple" + ")";
		
		assertEquals(s, cpy.toString());
	}

	@Test
	void testGetId() {
		String name = "Apple";
		int id = 1;
		Company cpy = new Company(id, name);
		assertEquals(cpy.getId(), 1);
	}

	@Test
	void testGetName() {
		String name = "Apple";
		int id = 1;
		Company cpy = new Company(id, name);
		assertEquals(cpy.getName(), "Apple");
	}

	@Test
	void testSetId() {
		String name = "Apple";
		int id = 1;
		Company cpy = new Company(id, name);
		assertEquals(cpy.getName(), "Apple");
		assertEquals(cpy.getId(), 1);
		cpy.setId(2);
		assertEquals(cpy.getName(), "Apple");
		assertEquals(cpy.getId(), 2);
	}

	@Test
	void testSetName() {
		String name = "Apple";
		int id = 1;
		Company cpy = new Company(id, name);
		assertEquals(cpy.getName(), "Apple");
		assertEquals(cpy.getId(), 1);
		cpy.setName("Philips");
		assertEquals(cpy.getName(), "Philips");
		assertEquals(cpy.getId(), 1);
	}

}

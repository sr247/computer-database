package com.excilys.formation.java.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.java.model.Computer;

class ComputerTest {

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
	void testComputerIntStringDateDateInt() {
		fail("Not yet implemented");
	}

	@Test
	void testComputerStringDateDateInt() {
		fail("Not yet implemented");
	}

	@Test
	void testGetId() {
		Computer cmp = new Computer(1, "Lenovo", null, null, 40);
		assertEquals(cmp.getId(), 1);
	}

	@Test
	void testSetId() {
		Computer cmp = new Computer(1, "Lenovo", null, null, 40);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompanyId(), 40);
		cmp.setId(2);
		assertEquals(cmp.getId(), 2);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompanyId(), 40);
	}

	@Test
	void testGetName() {
		Computer cmp = new Computer(1, "Lenovo", null, null, 40);
		assertEquals(cmp.getName(), "Lenovo");
	}

	@Test
	void testSetName() {
		Computer cmp = new Computer(1, "Lenovo", null, null, 40);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompanyId(), 40);
		cmp.setName("Intel");
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Intel");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompanyId(), 40);
	}

	@Test
	void testGetIntroduced() {
		Date d1 = Date.valueOf("1992-10-10");
		Date d2 = Date.valueOf("1993-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), 40);
		assertEquals(cmp.getIntroduced(), Date.valueOf("1992-10-10"));
	}

	@Test
	void testSetIntroduced() {
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Date d3 = Date.valueOf("1993-10-10");
		Computer cmp = new Computer(1, "Lenovo", d2.toLocalDate(), d3.toLocalDate(), 40);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1993-10-10"));
		assertEquals(cmp.getCompanyId(), 40);
		cmp.setIntroduced(d1.toLocalDate());
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1993-10-10"));
		assertEquals(cmp.getCompanyId(), 40);
	}

	@Test
	void testGetDiscontinued() {
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), 40);
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
	}

	@Test
	void testSetDiscontinued() {
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Date d3 = Date.valueOf("1993-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), 40);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getCompanyId(), 40);
		cmp.setDiscontinued(d3.toLocalDate());
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1993-10-10"));
		assertEquals(cmp.getCompanyId(), 40);
	}

	@Test
	void testGetCompanyId() {
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), 40);
		assertEquals(cmp.getCompanyId(), 40);
	}

	@Test
	void testSetCompany_id() {
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), 40);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getCompanyId(), 40);
		cmp.setCompany_id(41);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getCompanyId(), 41);
	}

	@Test
	void testToString() {
		Computer cmp = new Computer(1, "Lenovo", null, null, 40);
		String s = "Computer:("
				+ "id=" + 1 + ", "
				+ "name=" + "Lenovo" + ", "
				+ "introduced=" + null + ", "
				+ "discontinued=" + null + ", "
				+ "company_id=" + 40 + ")";
		
		assertEquals(s, cmp.toString());
	}

}

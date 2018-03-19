package com.excilys.formation.cdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

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
		Company company = new Company(40, "HTC Corporation");
		Computer cmp = new Computer(1, "Lenovo", null, null, company);
		assertEquals(cmp.getId(), 1);
	}

	@Test
	void testSetId() {
		Company company = new Company(40, "HTC Corporation");
		Computer cmp = new Computer(1, "Lenovo", null, null, company);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompany(), company);
		cmp.setId(2);
		assertEquals(cmp.getId(), 2);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompany(), company);
	}

	@Test
	void testGetName() {
		Company company = new Company(40, "HTC Corporation");
		Computer cmp = new Computer(1, "Lenovo", null, null, company);
		assertEquals(cmp.getName(), "Lenovo");
	}

	@Test
	void testSetName() {
		Company company = new Company(40, "HTC Corporation");
		Computer cmp = new Computer(1, "Lenovo", null, null, company);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompany(), company);
		cmp.setName("Intel");
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Intel");
		assertEquals(cmp.getIntroduced(), null);
		assertEquals(cmp.getDiscontinued(), null);
		assertEquals(cmp.getCompany(), company);
	}

	@Test
	void testGetIntroduced() {
		Date d1 = Date.valueOf("1992-10-10");
		Date d2 = Date.valueOf("1993-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), new Company(40, "HTC Corporation"));
		assertEquals(cmp.getIntroduced(), Date.valueOf("1992-10-10"));
	}

	@Test
	void testSetIntroduced() {
		Company company = new Company(40, "HTC Corporation");
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Date d3 = Date.valueOf("1993-10-10");
		Computer cmp = new Computer(1, "Lenovo", d2.toLocalDate(), d3.toLocalDate(), new Company(40, "HTC Corporation"));
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1993-10-10"));
		assertEquals(cmp.getCompany(), company);
		cmp.setIntroduced(d1.toLocalDate());
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1993-10-10"));
		assertEquals(cmp.getCompany(), company);
	}

	@Test
	void testGetDiscontinued() {
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), new Company(40, "HTC Corporation"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
	}

	@Test
	void testSetDiscontinued() {
		Company company = new Company(40, "HTC Corporation");
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Date d3 = Date.valueOf("1993-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), new Company(40, "HTC Corporation"));
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getCompany(), company);
		cmp.setDiscontinued(d3.toLocalDate());
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1993-10-10"));
		assertEquals(cmp.getCompany(), company);
	}

	@Test
	void testGetCompanyId() {
		Company company = new Company(40, "HTC Corporation");
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), new Company(40, "HTC Corporation"));
		assertEquals(cmp.getCompany(), company);
	}

	@Test
	void testSetCompany_id() {
		Date d1 = Date.valueOf("1991-10-10");
		Date d2 = Date.valueOf("1992-10-10");
		Company company = new Company(41, "Research In Motion");
		Computer cmp = new Computer(1, "Lenovo", d1.toLocalDate(), d2.toLocalDate(), new Company(40, "HTC Corporation"));
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getCompany(), new Company(40, "HTC Corporation"));
		cmp.setCompany(company);
		assertEquals(cmp.getId(), 1);
		assertEquals(cmp.getName(), "Lenovo");
		assertEquals(cmp.getIntroduced(), Date.valueOf("1991-10-10"));
		assertEquals(cmp.getDiscontinued(), Date.valueOf("1992-10-10"));
		assertEquals(cmp.getCompany(), company);
	}

	@Test
	void testToString() {
		Computer cmp = new Computer(1, "Lenovo", null, null, new Company(40, "HTC Corporation"));
		String s = "Computer:("
				+ "id=" + 1 + ", "
				+ "name=" + "Lenovo" + ", "
				+ "introduced=" + null + ", "
				+ "discontinued=" + null + ", "
				+ "company=" + new Company(40, "HTC Corporation") + ")";
		
		assertEquals(s, cmp.toString());
	}

}

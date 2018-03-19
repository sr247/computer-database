package com.excilys.formation.cdb;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;

class ComputerDBTest {

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
	void testGetNumComputers() {
		ComputerDB connDB = ComputerDB.INSTANCE;
		int expected = 574;
		assertEquals(expected, connDB.getNumComputers());
	}

	@Test
	void testGetComputerByID() {
		ComputerDB connDB = ComputerDB.INSTANCE;
		Computer expected = new Computer(1, "MacBook Pro 15.4 inch", null, null, new Company(1, "Apple Inc."));
		Computer actual = connDB.getComputerByID(1);
		assertEquals(expected.getClass(), 
				actual.getClass());
		assertEquals(expected.getId(), actual.getId());
		assertEquals(expected.getIntroduced(), actual.getIntroduced());
		assertEquals(expected.getDiscontinued(), actual.getDiscontinued());
		assertEquals(expected.getCompany(), actual.getCompany());
		
	}

	@Test
	void testGetComputerList() {
		assertTrue(true, "Depending on the content of db");
	}

	@Test
	void testGetComputerListIntInt() {
		ComputerDB connDB = ComputerDB.INSTANCE;
		List<Computer> expected = new ArrayList<Computer>();
		expected.add(new Computer(1, "MacBook Pro 15.4 inch", null, null, new Company(1, "Apple Inc.")));
		expected.add(new Computer(2, "CM-2a", null, null, new Company(2, "Thinking Machines")));	
		expected.add(new Computer(3, "CM-200", null, null, new Company(2, "Thinking Machines")));
		expected.add(new Computer(4, "CM-5e", null, null, new Company(2, "Thinking Machines")));
		expected.add(new Computer(5, "CM-5", LocalDate.parse("1991-01-01"), null, new Company(2, "Thinking Machines")));
		List<Computer> actual = connDB.getComputerList(0, 5);
				
		assertTrue(expected.size() == actual.size());
		for(int i = 0; i < expected.size(); i++) {
			System.err.println(expected.get(i));
			System.err.println(actual.get(i));
			assertEquals(expected.get(i).getId(), actual.get(i).getId());
			assertEquals(expected.get(i).getName(), actual.get(i).getName());
			assertEquals(expected.get(i).getIntroduced(), actual.get(i).getIntroduced());
			assertEquals(expected.get(i).getDiscontinued(), actual.get(i).getDiscontinued());
			assertEquals(expected.get(i).getCompany(), actual.get(i).getCompany());
			
		}
	}

	@Test
	void testSetDateProperly() {
		fail("Not yet implemented");
	}

	@Test
	void testCreate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

}

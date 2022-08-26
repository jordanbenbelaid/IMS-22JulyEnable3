package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CustomerTest {
	Customer testCustomer;
	
	@Before
	public void init() {
		testCustomer = new Customer("Name", "Surname");
	}
	
	@Test
	public void testConstructor() {
		// Constructor 1
		assertTrue(testCustomer instanceof Customer);
		
		// Constructor 2
		testCustomer = new Customer(1l, "New Name", "New Surname");
		assertTrue(testCustomer instanceof Customer);
	}
	
	@Test
	public void testSetGetId() {
		testCustomer.setId(1l);
		assertEquals(Long.valueOf(1), testCustomer.getId());
	}
	
	@Test
	public void testSetGetFirstName() {
		testCustomer.setFirstName("New");
		assertEquals("New", testCustomer.getFirstName());
	}
	
	@Test
	public void testSetGetSurname() {
		testCustomer.setSurname("New");
		assertEquals("New", testCustomer.getSurname());
	}
	
	@Test
	public void testToString() {
		testCustomer.setId(1l);
		String expected = "ID: 1 First Name: Name Surname: Surname";
		assertEquals(expected, testCustomer.toString());
	}
	
//	Taken from https://stackoverflow.com/questions/4449728/
	@Test
	public void testHashCodeAndEquals() {
		Customer testCustomer = new Customer("John", "Smith");
		Customer otherCustomer = new Customer("John", "Smith");
		
		assertTrue(testCustomer.equals(otherCustomer) && otherCustomer.equals(testCustomer));
		assertNotSame(testCustomer, otherCustomer);
		assertEquals(testCustomer.hashCode(), otherCustomer.hashCode());
	}

}

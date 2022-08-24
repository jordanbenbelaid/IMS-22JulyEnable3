package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DomainTest {
	
	@Test
	public void testGetDescription() {
		String expected = "CUSTOMER: Information about customers";
		assertEquals(expected, Domain.CUSTOMER.getDescription());
	}
	
}

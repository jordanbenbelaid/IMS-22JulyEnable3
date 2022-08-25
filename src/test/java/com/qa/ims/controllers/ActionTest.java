package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.qa.ims.controller.Action;

public class ActionTest {
	
	@Test
	public void testGetDescription() {
		String expected = "CREATE: To save a new entity into the database";
		assertEquals(expected, Action.CREATE.getDescription());
	}

}

package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.qa.ims.controller.OrderAction;

public class OrderActionTest {
	@Test
	public void testGetDescription() {
		String expected = "ADD: To add an item to the order";
		assertEquals(expected, OrderAction.ADD.getDescription());
	}

}

package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class OrderLineItemTest {
	OrderLineItem testLineItem;
	Item testItem = new Item(1l, "Item name", 50.00d, 10l);
	
	@Before
	public void init() {
		testLineItem = new OrderLineItem(testItem, 1l, 1l);
	}
	
	@Test
	public void testConstructor() {
		// Constructor 1
		assertTrue(testLineItem instanceof OrderLineItem);
		
		// Constructor 2
		testLineItem = new OrderLineItem(1l, testItem, 2l, 2l);
		assertTrue(testLineItem instanceof OrderLineItem);
	}
	
	@Test
	public void testSetGetId() {
		testLineItem.setId(1l);
		assertEquals(Long.valueOf(1), testLineItem.getId());
	}
	
	@Test
	public void testSetGetItem() {
		Item newItem = new Item(2l, "Name", 10.0d, 2l);
		testLineItem.setItem(newItem);
		assertEquals(newItem, testLineItem.getItem());
	}
	
	@Test
	public void testSetGetQuantity() {
		testLineItem.setQuantity(5l);
		assertEquals(Long.valueOf(5), testLineItem.getQuantity());
	}
	
	@Test
	public void testSetGetOrderId() {
		testLineItem.setOrderId(5l);
		assertEquals(Long.valueOf(5), testLineItem.getOrderId());
	}
	

	@Test
	public void testToString() {
		String expected = "name: Item name quantity: 1";
		assertEquals(expected, testLineItem.toString());
	}

}
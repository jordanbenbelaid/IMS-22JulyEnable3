package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class ItemTest {
	Item testItem;
	
	@Before
	public void init() {
		testItem = new Item("Item name", 50.00d, 10l);
	}
	
	@Test
	public void testConstructor() {
		// Constructor 1
		assertTrue(testItem instanceof Item);
		
		// Constructor 2
		testItem = new Item(1l, "Item name", 50.00d, 10l);
		assertTrue(testItem instanceof Item);
	}
	
	@Test
	public void testSetGetId() {
		testItem.setId(5l);
		assertEquals(Long.valueOf(5), testItem.getId());
	}
	
	@Test
	public void testSetGetName() {
		testItem.setName("New name");
		assertEquals("New name", testItem.getName());
	}
	
	@Test
	public void testSetGetPrice() {
		testItem.setPrice(5d);
		assertEquals(Double.valueOf(5), testItem.getPrice());
	}
	
	@Test
	public void testSetGetStock() {
		testItem.setStock(2l);
		assertEquals(Long.valueOf(2), testItem.getStock());
	}
	
	@Test
	public void testUpdateStock() {
		testItem.updateStock(-5l);
		assertEquals(Long.valueOf(5), testItem.getStock());
	}
	
	@Test
	public void testToString() {
		testItem.setId(1l);
		String expected = "id: 1 name: Item name price: 50.0 stock: 10";
		assertEquals(expected, testItem.toString());
	}

}

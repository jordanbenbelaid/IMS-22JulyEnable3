package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class OrderTest {
	Order testOrder;
	Customer testCustomer = new Customer(1l, "Name", "Surname");
	Item testItem = new Item("Item name", 50.00d, 10l);
	OrderLineItem testLineItem = new OrderLineItem(testItem, 2l, 1l);
	
	@Before
	public void init() {
		testOrder = new Order("abc123", testCustomer);
	}
	
	@Test
	public void testConstructor() {
		// Constructor 1
		assertTrue(testOrder instanceof Order);
		
		// Constructor 2
		testOrder = new Order(1l, "newnumber", testCustomer);
		assertTrue(testOrder instanceof Order);
	}
	
	@Test
	public void testSetGetId() {
		testOrder.setId(1l);
		assertEquals(Long.valueOf(1), testOrder.getId());
	}
	
	@Test
	public void testSetGetOrderNumber() {
		testOrder.setOrderNumber("1234");
		assertEquals("1234", testOrder.getOrderNumber());
	}
	
	@Test
	public void testSetGetCustomer() {
		testCustomer = new Customer(2l, "New", "New");
		testOrder.setCustomer(testCustomer);
		assertEquals(testCustomer, testOrder.getCustomer());
	}
	
	@Test
	public void testSetGetOrderTotal() {
		testOrder.setOrderTotal(50.55d);
		assertEquals(Double.valueOf(50.55), testOrder.getOrderTotal());
	}
	
	@Test
	public void testAddOrderLineItem() {
		testOrder.addOrderLineItem(testLineItem);
		assertEquals(1, testOrder.getOrderLineItems().size());
	}
	
	@Test
	public void testRemoveOrderLineItem() {
		testOrder.addOrderLineItem(testLineItem);
		testOrder.removeOrderLineItem(testLineItem);
		assertEquals(0, testOrder.getOrderLineItems().size());
	}
	
	@Test
	public void testCalculateTotal() {
		testOrder.addOrderLineItem(testLineItem);
		assertEquals(Double.valueOf(100), testOrder.getOrderTotal());
	}
	
	@Test
	public void testToString() {
		testOrder.setId(1l);
		String expected = "id: 1 orderNumber: abc123 customer: id: 1 first name: Name surname: Surname items: [] total price: 0.0";
		assertEquals(expected, testOrder.toString());
	}
	
	@Test
	public void testHashCodeAndEquals() {
		Order testOrder = new Order("abc123", testCustomer);
		Order otherOrder = new Order("abc123", testCustomer);
		
		assertTrue(testOrder.equals(otherOrder) && otherOrder.equals(testOrder));
		assertNotSame(testOrder, otherOrder);
		assertEquals(testOrder.hashCode(), otherOrder.hashCode());
	}

}

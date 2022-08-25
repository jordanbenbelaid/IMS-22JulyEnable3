package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderController;
import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderLineItemDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLineItem;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {
	@Mock
	private Utils utils;
	
	@Mock
	private OrderDAO dao;
	
	@Mock
	private CustomerDAO custDao;
	
	@Mock
	private ItemDAO itemDao;
	
	@Mock
	private OrderLineItemDAO lineItemDao;
	
	@InjectMocks
	private OrderController controller;
	
	@Test
	public void testCreate() {
		final String ORDERNUMBER = "abc1234";
		final Long CUSTOMERID = 1l;
		final Customer testCustomer = new Customer(CUSTOMERID, "jordan", "harrison");
		final Order testOrder = new Order(ORDERNUMBER, testCustomer);
		
		Mockito.when(utils.getString()).thenReturn(ORDERNUMBER);
		Mockito.when(utils.getLong()).thenReturn(CUSTOMERID);
		Mockito.when(custDao.read(CUSTOMERID)).thenReturn(testCustomer);
		Mockito.when(dao.create(testOrder)).thenReturn(testOrder);
		
		assertEquals(testOrder, controller.create());
		
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(custDao, Mockito.times(1)).read(CUSTOMERID);
		Mockito.verify(dao, Mockito.times(1)).create(testOrder);	
	}
	
	@Test
	public void testCreateInvalidCustomer() {
		final String ORDERNUMBER = "abc1234";
		final Long CUSTOMERID = 1l;
		final Customer testCustomer = new Customer(CUSTOMERID, "John", "Smith");
		final Order testOrder = new Order(ORDERNUMBER, testCustomer);
		
		Mockito.when(utils.getString()).thenReturn(ORDERNUMBER);
		Mockito.when(utils.getLong()).thenReturn(CUSTOMERID);
		Mockito.when(custDao.read(CUSTOMERID)).thenReturn(null);
		
		assertEquals(null, controller.create());
		
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(custDao, Mockito.times(1)).read(CUSTOMERID);
		Mockito.verify(dao, Mockito.times(0)).create(testOrder);	
	}
	
	@Test
	public void testReadAll() {
		Customer testCustomer = new Customer(1l, "Jane", "Doe");
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(1l, "abcd", testCustomer));
		
		Mockito.when(dao.readAll()).thenReturn(orders);
		
		assertEquals(orders, controller.readAll());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testReadAllNoOrders() {
		List<Order> orders = new ArrayList<>();
		
		Mockito.when(dao.readAll()).thenReturn(orders);
		
		assertEquals(orders, controller.readAll());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testUpdate() {
		Customer testCustomer = new Customer(1l, "Jane", "Doe");
		Order updatedOrder = new Order(1l, "1234", testCustomer);
		
		Mockito.when(utils.getLong()).thenReturn(updatedOrder.getId(), testCustomer.getId());
		Mockito.when(utils.getString()).thenReturn(updatedOrder.getOrderNumber());
		Mockito.when(custDao.read(testCustomer.getId())).thenReturn(testCustomer);
		Mockito.when(dao.update(updatedOrder)).thenReturn(updatedOrder);

		assertEquals(updatedOrder, controller.update());

		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(custDao, Mockito.times(1)).read(testCustomer.getId());
		Mockito.verify(dao, Mockito.times(1)).update(updatedOrder);
	}
	
	@Test
	public void testUpdateOrderDoesntExist() {
		Customer testCustomer = new Customer(1l, "Jane", "Doe");
		Order updatedOrder = new Order(1l, "1234", testCustomer);
		
		Mockito.when(utils.getLong()).thenReturn(updatedOrder.getId(), testCustomer.getId());
		Mockito.when(utils.getString()).thenReturn(updatedOrder.getOrderNumber());
		Mockito.when(custDao.read(testCustomer.getId())).thenReturn(testCustomer);
		Mockito.when(dao.update(updatedOrder)).thenReturn(null);

		assertEquals(null, controller.update());

		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(custDao, Mockito.times(1)).read(testCustomer.getId());
		Mockito.verify(dao, Mockito.times(1)).update(updatedOrder);
	}
	
	@Test
	public void testUpdateInvalidCustomer() {
		Customer testCustomer = new Customer(1l, "Jane", "Doe");
		Order updatedOrder = new Order(1l, "1234", testCustomer);
		
		Mockito.when(utils.getLong()).thenReturn(updatedOrder.getId(), testCustomer.getId());
		Mockito.when(utils.getString()).thenReturn(updatedOrder.getOrderNumber());
		Mockito.when(custDao.read(testCustomer.getId())).thenReturn(null);

		assertEquals(null, controller.update());

		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(custDao, Mockito.times(1)).read(testCustomer.getId());
		Mockito.verify(dao, Mockito.times(0)).update(updatedOrder);
	}
	
	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(utils.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, controller.delete());

		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}
	
	@Test
	public void testCalculateTotal() {
		final long ORDERID = 1l;
		Customer testCustomer = new Customer(1l, "Jane", "Doe");
		Order testOrder = new Order(1l, "1234", testCustomer);
		
		Mockito.when(dao.read(ORDERID)).thenReturn(testOrder);
		Mockito.when(dao.update(testOrder)).thenReturn(testOrder);
		
		assertEquals(testOrder, controller.calculateTotal(ORDERID));
		
		Mockito.verify(dao, Mockito.times(1)).read(ORDERID);
		Mockito.verify(dao, Mockito.times(1)).update(testOrder);
	}
	
	@Test
	public void testAddItem() {
		final long ORDERID = 1l;
		final long ITEMID = 2l;
		final long QUANTITY = 5l;
		
		Item testItem = new Item(ITEMID, "stuff", 5.00, 6l);
		Customer testCustomer = new Customer(1l, "James", "Jones");
		Order testOrder = new Order(ORDERID, "1234", testCustomer);
		OrderLineItem testLineItem = new OrderLineItem(1l, testItem, QUANTITY, ORDERID);
		
		Mockito.when(utils.getLong()).thenReturn(ORDERID, ITEMID, QUANTITY);
		Mockito.when(itemDao.read(ITEMID)).thenReturn(testItem);
		Mockito.when(dao.read(ORDERID)).thenReturn(testOrder);
		Mockito.when(lineItemDao.readByOrderItem(ORDERID, ITEMID)).thenReturn(testLineItem);
		Mockito.when(lineItemDao.update(testLineItem)).thenReturn(testLineItem);
		Mockito.when(itemDao.update(testItem)).thenReturn(testItem);
		Mockito.when(dao.update(testOrder)).thenReturn(testOrder);
		
		assertEquals(testOrder, controller.addItem());
		
		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(itemDao, Mockito.times(1)).read(ITEMID);
		Mockito.verify(itemDao, Mockito.times(1)).update(testItem);
		Mockito.verify(lineItemDao, Mockito.times(1)).readByOrderItem(ORDERID, ITEMID);
		Mockito.verify(lineItemDao, Mockito.times(1)).update(testLineItem);
		Mockito.verify(dao, Mockito.times(2)).read(ORDERID);
	}
	
	@Test
	public void testAddItemNotYetExisting() {
		final long ORDERID = 1l;
		final long ITEMID = 2l;
		final long QUANTITY = 5l;
		
		Item testItem = new Item(ITEMID, "stuff", 5.00, 6l);
		Customer testCustomer = new Customer(1l, "James", "Jones");
		Order testOrder = new Order(ORDERID, "1234", testCustomer);
		OrderLineItem testLineItem = new OrderLineItem(testItem, QUANTITY, ORDERID);
		
		Mockito.when(utils.getLong()).thenReturn(ORDERID, ITEMID, QUANTITY);
		Mockito.when(itemDao.read(ITEMID)).thenReturn(testItem);
		Mockito.when(dao.read(ORDERID)).thenReturn(testOrder);
		Mockito.when(lineItemDao.readByOrderItem(ORDERID, ITEMID)).thenReturn(null);
		Mockito.when(lineItemDao.create(testLineItem)).thenReturn(testLineItem);
		Mockito.when(itemDao.update(testItem)).thenReturn(testItem);
		Mockito.when(dao.update(testOrder)).thenReturn(testOrder);
		
		assertEquals(testOrder, controller.addItem());
		
		Mockito.verify(utils, Mockito.times(3)).getLong();
		Mockito.verify(itemDao, Mockito.times(1)).read(ITEMID);
		Mockito.verify(itemDao, Mockito.times(1)).update(testItem);
		Mockito.verify(lineItemDao, Mockito.times(1)).readByOrderItem(ORDERID, ITEMID);
		Mockito.verify(lineItemDao, Mockito.times(1)).create(testLineItem);
		Mockito.verify(dao, Mockito.times(2)).read(ORDERID);
	}
	
	@Test
	public void testRemoveItem() {
		final long ORDERID = 1l;
		final long ITEMID = 2l;
		
		Item testItem = new Item(ITEMID, "stuff", 5.00, 6l);
		Customer testCustomer = new Customer(1l, "James", "Jones");
		Order testOrder = new Order(ORDERID, "1234", testCustomer);
		OrderLineItem testLineItem = new OrderLineItem(1l, testItem, 1l, ORDERID);
		
		Mockito.when(utils.getLong()).thenReturn(ORDERID, ITEMID);
		Mockito.when(itemDao.read(ITEMID)).thenReturn(testItem);
		Mockito.when(dao.read(ORDERID)).thenReturn(testOrder);
		Mockito.when(lineItemDao.readByOrderItem(ORDERID, ITEMID)).thenReturn(testLineItem);
		Mockito.when(lineItemDao.delete(testLineItem.getId())).thenReturn(1);
		Mockito.when(itemDao.update(testItem)).thenReturn(testItem);
		Mockito.when(dao.update(testOrder)).thenReturn(testOrder);
		
		assertEquals(testOrder, controller.removeItem());
		
		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(itemDao, Mockito.times(1)).read(ITEMID);
		Mockito.verify(dao, Mockito.times(2)).read(ORDERID);
		Mockito.verify(lineItemDao, Mockito.times(1)).readByOrderItem(ORDERID, ITEMID);
		Mockito.verify(lineItemDao, Mockito.times(1)).delete(testLineItem.getId());
		Mockito.verify(itemDao, Mockito.times(1)).update(testItem);

	}

}

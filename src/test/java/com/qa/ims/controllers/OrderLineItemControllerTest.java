package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.OrderLineItemController;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderLineItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.OrderLineItem;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class OrderLineItemControllerTest {
	@Mock
	private Utils utils;
	
	@Mock
	private OrderLineItemDAO dao;
	
	@Mock
	private ItemDAO itemDao;
	
	@Mock
	private OrderLineItemController controller;
	
	@Test
	public void testAddToOrder() {
		final long ITEMID = 1l;
		final long ORDERID = 1l;
		final long QUANTITY = 2l;
		
		Item testItem = new Item(ITEMID, "stuff", 5.00, 6l);
		OrderLineItem testLineItem = new OrderLineItem(1l, testItem, 1l, ORDERID);
		
		Mockito.when(dao.readByOrderItem(ORDERID, ITEMID)).thenReturn(testLineItem);
		Mockito.when(dao.update(testLineItem)).thenReturn(testLineItem);
		Mockito.when(itemDao.update(testItem)).thenReturn(testItem);
		
		assertEquals(testLineItem, controller.addToOrder(testItem, ITEMID, QUANTITY, ORDERID));
		
		Mockito.verify(dao, Mockito.times(1)).readByOrderItem(ORDERID, ITEMID);
		Mockito.verify(dao, Mockito.times(1)).update(testLineItem);
		Mockito.verify(itemDao, Mockito.times(1)).update(testItem);
	}
	
	@Test
	public void testAddToOrderDoesntCurrentlyExist() {
		final long ITEMID = 1l;
		final long ORDERID = 1l;
		final long QUANTITY = 2l;
		
		Item testItem = new Item(ITEMID, "stuff", 5.00, 6l);
		OrderLineItem testLineItem = new OrderLineItem(1l, testItem, 1l, ORDERID);
		
		Mockito.when(dao.readByOrderItem(ORDERID, ITEMID)).thenReturn(null);
		Mockito.when(dao.create(testLineItem)).thenReturn(testLineItem);
		Mockito.when(itemDao.update(testItem)).thenReturn(testItem);
		
		assertEquals(testLineItem, controller.addToOrder(testItem, ITEMID, QUANTITY, ORDERID));
		
		Mockito.verify(dao, Mockito.times(1)).readByOrderItem(ORDERID, ITEMID);
		Mockito.verify(dao, Mockito.times(1)).update(testLineItem);
		Mockito.verify(itemDao, Mockito.times(1)).update(testItem);
	}

}

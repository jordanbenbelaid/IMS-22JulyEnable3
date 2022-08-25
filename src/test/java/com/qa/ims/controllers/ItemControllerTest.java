package com.qa.ims.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.controller.ItemController;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.Utils;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {
	
	@Mock
	private Utils utils;
	
	@Mock
	private ItemDAO dao;
	
	@InjectMocks
	private ItemController controller;
	
	@Test
	public void testCreate() {
		final String NAME = "apple";
		final Double PRICE = 5.0;
		final Long STOCK = 2l;
		final Item testItem = new Item(NAME, PRICE, STOCK);
		
		Mockito.when(utils.getString()).thenReturn(NAME);
		Mockito.when(utils.getDouble()).thenReturn(PRICE);
		Mockito.when(utils.getLong()).thenReturn(STOCK);
		Mockito.when(dao.create(testItem)).thenReturn(testItem);
		
		assertEquals(testItem, controller.create());
		
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getDouble();
		Mockito.verify(utils, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).create(testItem);	
	}
	
	@Test
	public void testReadAll() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(1l, "orange", 2.00, 5l));
		
		Mockito.when(dao.readAll()).thenReturn(items);
		
		assertEquals(items, controller.readAll());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testReadAllNoItems() {
		List<Item> items = new ArrayList<>();
		
		Mockito.when(dao.readAll()).thenReturn(items);
		
		assertEquals(items, controller.readAll());
		
		Mockito.verify(dao, Mockito.times(1)).readAll();
	}
	
	@Test
	public void testUpdate() {
		Item updatedItem = new Item(1l, "banana", 6.00, 2l);
		
		Mockito.when(utils.getLong()).thenReturn(updatedItem.getId(), updatedItem.getStock());
		Mockito.when(utils.getString()).thenReturn(updatedItem.getName());
		Mockito.when(utils.getDouble()).thenReturn(updatedItem.getPrice());
		Mockito.when(dao.update(updatedItem)).thenReturn(updatedItem);

		assertEquals(updatedItem, controller.update());

		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getDouble();
		Mockito.verify(dao, Mockito.times(1)).update(updatedItem);
	}
	
	@Test
	public void testUpdateItemDoesntExist() {
		Item updatedItem = new Item(1l, "banana", 6.00, 2l);
		
		Mockito.when(utils.getLong()).thenReturn(updatedItem.getId(), updatedItem.getStock());
		Mockito.when(utils.getString()).thenReturn(updatedItem.getName());
		Mockito.when(utils.getDouble()).thenReturn(updatedItem.getPrice());
		Mockito.when(dao.update(updatedItem)).thenReturn(null);

		assertEquals(null, controller.update());

		Mockito.verify(utils, Mockito.times(2)).getLong();
		Mockito.verify(utils, Mockito.times(1)).getString();
		Mockito.verify(utils, Mockito.times(1)).getDouble();
		Mockito.verify(dao, Mockito.times(1)).update(updatedItem);
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

}

package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.OrderLineItem;
import com.qa.ims.utils.DBUtils;

public class OrderLineItemDAOTest {
	
	private final OrderLineItemDAO dao = new OrderLineItemDAO();
	private final Item testItem = new Item(1l, "TV", 500.0, 9l);
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final OrderLineItem testLineItem = new OrderLineItem(2l, testItem, 1l, 1l);
		assertEquals(testLineItem, dao.create(testLineItem));
	}
	
	@Test
	public void testReadAll() {
		List<OrderLineItem> testLineItems = new ArrayList<>();
		testLineItems.add(new OrderLineItem(1l, testItem, 1l, 1l));
		assertEquals(testLineItems, dao.readAll());
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new OrderLineItem(1l, testItem, 1l, 1l), dao.readLatest());
	}
	
	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new OrderLineItem(ID, testItem, 1l, 1l), dao.read(ID));
	}
	
	@Test
	public void testReadByOrderId() {
		final long ID = 1L;
		List<OrderLineItem> testLineItems = new ArrayList<>();
		testLineItems.add(new OrderLineItem(1l, testItem, 1l, ID));
		assertEquals(testLineItems, dao.readByOrderId(ID));
	}
	
	@Test
	public void testReadByOrderItem() {
		assertEquals(null, dao.readByOrderItem(2l, 2l));
	}

	@Test
	public void testUpdate() {
		final OrderLineItem updatedLineItem = new OrderLineItem(1L, testItem, 2l, 1l);
		assertEquals(updatedLineItem, dao.update(updatedLineItem));

	}
	
	@Test
	public void testUpdateInvalidLineItem() {
		final OrderLineItem updatedLineItem = new OrderLineItem(2L, testItem, 2l, 1l);
		assertEquals(null, dao.update(updatedLineItem));

	}

	@Test
	public void testDelete() {
		assertEquals(1, dao.delete(1));
	}

}

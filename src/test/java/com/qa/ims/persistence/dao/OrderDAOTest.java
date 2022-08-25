package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLineItem;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {
	
	private final OrderDAO dao = new OrderDAO();
	private final Customer testCustomer = new Customer(1l, "jordan", "harrison");
	private final Item testItem = new Item(1l, "TV", 500.0, 9l);
	private final OrderLineItem testLineItem = new OrderLineItem(1l, testItem, 1l, 1l);
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final Order testOrder = new Order(2l, "abc", testCustomer);
		assertEquals(testOrder, dao.create(testOrder));
	}
	
	@Test
	public void testReadAll() {
		List<Order> testOrders = new ArrayList<>();
		Order testOrder = new Order(1l, "abc1234", testCustomer);
		testOrder.addOrderLineItem(testLineItem);
		testOrders.add(testOrder);
		assertEquals(testOrders, dao.readAll());
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new Order(1L, "abc1234", testCustomer), dao.readLatest());
	}
	
	@Test
	public void testRead() {
		final long ID = 1L;
		Order testOrder = new Order(ID, "abc1234", testCustomer);
		testOrder.addOrderLineItem(testLineItem);
		assertEquals(testOrder, dao.read(ID));
	}

	@Test
	public void testUpdate() {
		final Order updatedOrder = new Order(1L, "newnum", testCustomer);
		updatedOrder.addOrderLineItem(testLineItem);
		assertEquals(updatedOrder, dao.update(updatedOrder));

	}
	
	@Test
	public void testUpdateInvalidOrder() {
		final Order updatedOrder = new Order(4L, "newnum", testCustomer);
		assertEquals(null, dao.update(updatedOrder));

	}

	@Test
	public void testDelete() {
		assertEquals(1, dao.delete(1));
	}
	
}

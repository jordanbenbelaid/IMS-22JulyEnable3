package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

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
		assertEquals(testItem, dao.create(testLineItem));
	}

}

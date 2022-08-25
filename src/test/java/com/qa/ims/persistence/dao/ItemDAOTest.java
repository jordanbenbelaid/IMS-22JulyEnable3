package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DBUtils;

public class ItemDAOTest {
	
	private final ItemDAO dao = new ItemDAO();
	
	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}
	
	@Test
	public void testCreate() {
		final Item testItem = new Item(2l, "Some name", 5.00, 5l);
		assertEquals(testItem, dao.create(testItem));
	}
	
	@Test
	public void testReadAll() {
		List<Item> testItems = new ArrayList<>();
		testItems.add(new Item(1L, "TV", 500.0, 9l));
		assertEquals(testItems, dao.readAll());
	}
	
	@Test
	public void testReadLatest() {
		assertEquals(new Item(1l, "TV", 500.0, 9l), dao.readLatest());
	}
	
	@Test
	public void testRead() {
		final long ID = 1L;
		assertEquals(new Item(ID, "TV", 500.0, 9l), dao.read(ID));
	}

	@Test
	public void testUpdate() {
		final Item updatedItem = new Item(1L, "Something", 50.0, 8l);
		assertEquals(updatedItem, dao.update(updatedItem));

	}
	
	@Test
	public void testUpdateInvalidItem() {
		final Item updatedItem = new Item(2L, "Something", 50.0, 8l);
		assertEquals(null, dao.update(updatedItem));

	}

	@Test
	public void testDelete() {
		assertEquals(1, dao.delete(1));
	}
	
}

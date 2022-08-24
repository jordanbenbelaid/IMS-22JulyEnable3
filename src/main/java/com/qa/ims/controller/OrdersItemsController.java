package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrdersItemsDAO;
import com.qa.ims.persistence.domain.OrdersItems;
import com.qa.ims.utils.Utils;

public class OrdersItemsController implements CrudController<OrdersItems>{

	public static final Logger LOGGER = LogManager.getLogger();
	
	private OrdersItemsDAO ordersItemsDAO;
	private Utils utils;

	
	public OrdersItemsController(OrdersItemsDAO ordersItemsDAO, Utils utils) {
		super();
		this.ordersItemsDAO = ordersItemsDAO;
		this.utils = utils;
	}

	@Override
	public List<OrdersItems> readAll() {
		List<OrdersItems> ordersItems = ordersItemsDAO.readAll();
		for (OrdersItems oi : ordersItems) {
			LOGGER.info(ordersItems);
		}
		return ordersItems;
	}

	@Override
	public OrdersItems create() {
		LOGGER.info("Please enter the order id");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter the item id of the item being ordered");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the desired quanity of this item");
		Long quantity = utils.getLong();
		OrdersItems ordersItems = ordersItemsDAO.create(new OrdersItems(orderId, itemId, quantity));
		LOGGER.info("Order details created");
		return ordersItems;
	}

	@Override
	public OrdersItems update() {
		LOGGER.info("Please enter the order id");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter the item id of the item being ordered");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the desired quanity of this item");
		Long quantity = utils.getLong();
		OrdersItems ordersItems = ordersItemsDAO.update(new OrdersItems(orderId, itemId, quantity));
		LOGGER.info("Order details updated");
		return ordersItems;
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the order id of the order details you would like to delete");
		Long orderId = utils.getLong();
		return ordersItemsDAO.delete(orderId);
	}
	
	

}

package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrdersDAO;
import com.qa.ims.persistence.domain.Orders;
import com.qa.ims.utils.Utils;

public class OrdersController implements CrudController<Orders> {

	public static final Logger LOGGER = LogManager.getLogger();
	
	private OrdersDAO ordersDAO;
	private Utils utils;
	
	public OrdersController(OrdersDAO ordersDAO, Utils utils) {
		super();
		this.ordersDAO = ordersDAO;
		this.utils = utils;
	}

	//reading all orders into the logger 
	@Override
	public List<Orders> readAll() {
		List<Orders> orders = ordersDAO.readAll();
		for (Orders order : orders) {
			LOGGER.info(orders);
		}
		return orders;
	}

	//Creates a order by taking in user input
	@Override
	public Orders create() {
		LOGGER.info("Please enter the id of the customer placing the order");
		Long customerId = utils.getLong();
		Orders orders = ordersDAO.create(new Orders(customerId));
		LOGGER.info("Order created");
		return orders;
	}

	//updating the customer associated with the order
	@Override
	public Orders update() {
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Orders orders = ordersDAO.create(new Orders(id, customerId));
		LOGGER.info("Order updated");
		return orders;
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return ordersDAO.delete(id);
	}

	
	
}

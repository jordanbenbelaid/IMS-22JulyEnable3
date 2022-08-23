package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private OrderDAO orderDAO;
	private Utils utils;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		for (Order order : orders) {
			LOGGER.info(order);
		}
		return orders;
	}

	@Override
	public Order create() {
		CustomerDAO custDAO = new CustomerDAO();
		LOGGER.info("Please enter an order number");
		String orderNumber = utils.getString();
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Customer customer = custDAO.read(customerId);
		Order order = orderDAO.create(new Order(orderNumber, customer));
		LOGGER.info("Order created");
		return order;
	}

	@Override
	public Order update() {
		CustomerDAO custDAO = new CustomerDAO();
		LOGGER.info("Please enter the id of the order you would like to update");
		Long id = utils.getLong();
		LOGGER.info("Please enter an order number");
		String orderNumber = utils.getString();
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Customer customer = custDAO.read(customerId);
		Order order = orderDAO.update(new Order(id, orderNumber, customer));
		LOGGER.info("Order updated");
		return order;
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}

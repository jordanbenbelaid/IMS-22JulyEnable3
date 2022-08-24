package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.dao.OrderLineItemDAO;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.OrderLineItem;
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
	
	public Order addItem() {
		ItemDAO itemDAO = new ItemDAO();
		OrderLineItemDAO lineItemDAO = new OrderLineItemDAO();
		LOGGER.info("Please enter the order id");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter the item id");
		Long itemId = utils.getLong();
		Item item = itemDAO.read(itemId);
		LOGGER.info("Please enter the item quantity");
		Long quantity = utils.getLong();
		OrderLineItem currentItem = lineItemDAO.readByOrderItem(orderId, itemId);
		OrderLineItem lineItem;
		if (currentItem != null) {
			currentItem.setQuantity(currentItem.getQuantity() + quantity);
			lineItem = lineItemDAO.update(currentItem);
		} else {
			lineItem = lineItemDAO.create(new OrderLineItem(item, quantity, orderId));
		}
		Order order = calculateTotal(orderId);
		LOGGER.info(quantity + " of " + lineItem.getItem().getName() + " added");
		return order;
	}
	
	public Order removeItem() {
		OrderLineItemDAO lineItemDAO = new OrderLineItemDAO();
		LOGGER.info("Please enter the order id");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter the id of the item to remove");
		Long itemId = utils.getLong();
		OrderLineItem lineItem = lineItemDAO.readByOrderItem(orderId, itemId);
		lineItemDAO.delete(lineItem.getId());
		Order order = calculateTotal(orderId);
		return order;
	}
	
	public Order calculateTotal(Long orderId) {
		Order selectedOrder = orderDAO.read(orderId);
		Order revisedOrder = orderDAO.update(selectedOrder);
		
		return revisedOrder;
		
	}

	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}

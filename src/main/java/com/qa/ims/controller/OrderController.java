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
		if (customer != null) {
			Order order = orderDAO.update(new Order(id, orderNumber, customer));
			if (order != null) {
				LOGGER.info("Order updated");
			}
			return order;
		}
		
		return null;
		
	}
	
	public Order addItem() {
		ItemDAO itemDAO = new ItemDAO();
		OrderLineItemController lineItemController = new OrderLineItemController();
		Item item;
		Long orderId;
		Long itemId;
		Long quantity;
		while (true) {
				LOGGER.info("Please enter the order id");
				orderId = utils.getLong();
				LOGGER.info("Please enter the item id");
				itemId = utils.getLong();
				LOGGER.info("Please enter the item quantity");
				quantity = utils.getLong();
				item = itemDAO.read(itemId);
				Order order = orderDAO.read(orderId);
				if (item != null && order != null) {
					break;
				} else {
					LOGGER.info("Some information you entered is incorrect, please try again");
					continue;
				}	
		}
		lineItemController.addToOrder(item, orderId, itemId, quantity);
		Order order = calculateTotal(orderId);
		LOGGER.info(quantity + " of " + item.getName() + " added");
		return order;
	}
	
	public Order removeItem() {
		OrderLineItemDAO lineItemDAO = new OrderLineItemDAO();
		ItemDAO itemDAO = new ItemDAO();
		OrderLineItem lineItem;
		Item item;
		Long orderId;
		while (true) {
			LOGGER.info("Please enter the order id");
			orderId = utils.getLong();
			LOGGER.info("Please enter the id of the item to remove");
			Long itemId = utils.getLong();
			item = itemDAO.read(itemId);
			Order order = orderDAO.read(orderId);
			if (item == null || order == null) {
				LOGGER.info("Some information you entered is incorrect, please try again");
				continue;
			} else {
				lineItem = lineItemDAO.readByOrderItem(orderId, itemId);
				if (lineItem != null) {
					break;
				} else {
					LOGGER.info("This order does not contain the selected item.");
					continue;
				}
				
			}
			
		}
		
		lineItemDAO.delete(lineItem.getId());
		item.updateStock(lineItem.getQuantity());
		itemDAO.update(item);
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

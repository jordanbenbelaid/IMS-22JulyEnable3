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
	private CustomerDAO custDAO;
	private ItemDAO itemDAO;
	private OrderLineItemDAO lineItemDAO;
	private Utils utils;

	public OrderController(OrderDAO orderDAO, CustomerDAO custDAO, ItemDAO itemDAO, OrderLineItemDAO lineItemDAO, Utils utils) {
		this.orderDAO = orderDAO;
		this.custDAO = custDAO;
		this.itemDAO = itemDAO;
		this.lineItemDAO = lineItemDAO;
		this.utils = utils;
	}

	/**
	 * Reads all orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> orders = orderDAO.readAll();
		if (orders.size() > 0) {
			for (Order order : orders) {
				LOGGER.info(order);
			}
		} else {
			LOGGER.info("There are currently no orders in the system");
		}

		return orders;
	}

	/**
	 * Creates an order by taking in user input
	 * Only attempts to create the order if a valid
	 * customer id has been input.
	 */
	@Override
	public Order create() {
		LOGGER.info("Please enter an order number");
		String orderNumber = utils.getString();
		LOGGER.info("Please enter a customer id");
		Long customerId = utils.getLong();
		Customer customer = custDAO.read(customerId);
		if (customer != null) {
			Order order = orderDAO.create(new Order(orderNumber, customer));
			LOGGER.info("Order created");
			return order;	
		} else {
			LOGGER.info("Please try again with a valid customer");
			return null;
		}
	}

	
	/**
	 * Updates an existing order by taking in user input
	 * Only attempts to update the order if a valid
	 * customer id has been input.
	 */
	@Override
	public Order update() {
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

	
	/**
	 * Adds an item to an order.
	 * Only attempts to create the line item if valid order and customer
	 * ids have been added. Calls the OrderLineItemController method for
	 * the database updates. 
	 */
	public Order addItem() {
		LOGGER.info("Please enter the order id");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter the item id");
		Long itemId = utils.getLong();
		LOGGER.info("Please enter the item quantity");
		Long quantity = utils.getLong();
		Item item = itemDAO.read(itemId);
		Order order = orderDAO.read(orderId);
		if (item != null && order != null) {
			OrderLineItem currentLineItem = lineItemDAO.readByOrderItem(orderId, itemId);
			OrderLineItem lineItem;
			
			if (currentLineItem != null) {
				currentLineItem.setQuantity(currentLineItem.getQuantity() + quantity);
				lineItem = lineItemDAO.update(currentLineItem);
			} else {
				lineItem = lineItemDAO.create(new OrderLineItem(item, quantity, orderId));
			}
			item.updateStock(-quantity);
			itemDAO.update(item);
			
			Order updatedOrder = calculateTotal(orderId);
			LOGGER.info(quantity + " of " + item.getName() + " added");
			return updatedOrder;
		} else {
			LOGGER.info("Some information you entered is incorrect, please try again");
			return null;
		}

	}

	/**
	 * Removes an item from an order.
	 * Only attempts to delete the line item if valid order and customer
	 * ids have been added. 
	 */
	public Order removeItem() {
		LOGGER.info("Please enter the order id");
		Long orderId = utils.getLong();
		LOGGER.info("Please enter the id of the item to remove");
		Long itemId = utils.getLong();
		Item item = itemDAO.read(itemId);
		Order order = orderDAO.read(orderId);
		if (item == null || order == null) {
			LOGGER.info("Some information you entered is incorrect, please try again");
			return null;
		} else {
			OrderLineItem lineItem = lineItemDAO.readByOrderItem(orderId, itemId);
			if (lineItem != null) {
				lineItemDAO.delete(lineItem.getId());
				item.updateStock(lineItem.getQuantity());
				itemDAO.update(item);
				Order updatedOrder = calculateTotal(orderId);
				return updatedOrder;
			} else {
				LOGGER.info("This order does not contain the selected item.");
				return null;
			}
		}

	}

	/**
	 * Updates the order total value in the database based on
	 * the line items.
	 * The Order class instance total is updated automatically when the order
	 * is read.
	 */
	public Order calculateTotal(Long orderId) {
		Order selectedOrder = orderDAO.read(orderId);
		Order revisedOrder = orderDAO.update(selectedOrder);

		return revisedOrder;

	}

	/**
	 * Deletes an existing order by taking in user input
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}

}

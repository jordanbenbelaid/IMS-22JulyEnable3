package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrdersDAO;
import com.qa.ims.persistence.dao.OrdersItemsDAO;
import com.qa.ims.persistence.domain.Orders;
import com.qa.ims.persistence.domain.OrdersItems;
import com.qa.ims.utils.Utils;

public class OrdersController implements CrudController<Orders> {

	public static final Logger LOGGER = LogManager.getLogger();
	Scanner scan = new Scanner(System.in);
	OrdersItemsDAO oi = new OrdersItemsDAO();
	
	private OrdersDAO ordersDAO;
	private Utils utils;
	private OrdersItemsDAO ordersItemsDAO;
	private OrdersItems ordersItems;
	
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
		LOGGER.info("Would you like to add details to this order? Y/N?");
		String details = scan.nextLine();
		if(details.equalsIgnoreCase("y")) {
			boolean isRunning = true;
				
			while(isRunning) {
			LOGGER.info("Please enter the id of the item you would like to order");
			Long itemId = utils.getLong();
			LOGGER.info("Please enter the quanitity you would like of this item");
			Long quantity = utils.getLong();
			
			OrdersItems ordersItems = oi.create(new OrdersItems(orders.getOrderId(), itemId , quantity));
			LOGGER.info("Item added to order");
	
			LOGGER.info("Would you like to order another item? Y/N?");
			String addAnother = scan.nextLine();
			if(addAnother.equalsIgnoreCase("n")) isRunning = false;
			
			}
	
			} else {
				LOGGER.info("Your order has been created without details");
			}
			
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
		LOGGER.info("Please enter the number of the option you would like"
				+ "\n [1] delete an entire order from the system" +
				"\n [2] delete an item from an order");
		int response = scan.nextInt();
		if(response == 1) {
		LOGGER.info("Please enter the id of the order you would like to delete");
		Long id = utils.getLong();
		int orders = ordersDAO.delete(id);
		return ordersDAO.deletePhaseTwo(id);
		}
		else if(response == 2) {
			LOGGER.info("Please enter the id of the order from which you would like to delete an item");
			Long id = utils.getLong();
			LOGGER.info("Please enter the id of the item you wish to delete");
			Long itemId = utils.getLong();
			return oi.deleteItem(id, itemId);
		} else {
			 LOGGER.info("That is not a valid selection");
		}
	 return 0;
	}

	
	
}

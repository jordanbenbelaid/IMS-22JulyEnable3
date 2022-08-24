package com.qa.ims.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderLineItemDAO;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.OrderLineItem;


public class OrderLineItemController {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private OrderLineItemDAO lineItemDAO = new OrderLineItemDAO();
	
	public OrderLineItem addToOrder(Item item, Long orderId, Long itemId, Long quantity) {
		ItemDAO itemDAO = new ItemDAO();
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
		
		return lineItem;
	}

}

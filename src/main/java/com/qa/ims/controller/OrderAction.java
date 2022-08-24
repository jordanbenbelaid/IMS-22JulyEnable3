package com.qa.ims.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.utils.Utils;

public enum OrderAction {
	ADD("To add an item to the order"), REMOVE("To remove and item from the order"),
	UPDATE("To change the order information"), RETURN("To return to order options");
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	private String description;
	
	OrderAction(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.name() + ": " + this.description;
	}
	
	public static void printOrderActions() {
		for (OrderAction action : OrderAction.values()) {
			LOGGER.info(action.getDescription());
		}
	}
	
	public static OrderAction getAction(Utils utils) {
		OrderAction action = null;
		do {
			try {
				action = OrderAction.valueOf(utils.getString().toUpperCase());
			} catch (IllegalArgumentException e) {
				LOGGER.error("Invalid selection please try again");
			}
		} while (action == null);
		return action;
	}

}

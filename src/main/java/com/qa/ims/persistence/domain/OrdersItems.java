package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OrdersItems {

	
	private Long quantity;
	private Long id;
	private Long itemId;

	public OrdersItems(Long id, Long itemId, Long quantity) {
		super();
		this.setId(id);
		this.setItemId(itemId);
		this.setQuantity(quantity);
	}

	public OrdersItems(List<OrdersItems> orderDetails) {
		
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	
	
	
}

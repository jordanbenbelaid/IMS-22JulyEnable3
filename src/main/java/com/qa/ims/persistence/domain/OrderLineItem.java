package com.qa.ims.persistence.domain;

import java.util.Objects;

public class OrderLineItem {
	private Long id;
	private Item item;
	private Long quantity;
	private Long orderId;
	
	public OrderLineItem(Item item, Long quantity, Long orderId) {
		this.item = item;
		this.quantity = quantity;
		this.orderId = orderId;
	}

	public OrderLineItem(Long id, Item item, Long quantity, Long orderId) {
		this.id = id;
		this.item = item;
		this.quantity = quantity;
		this.orderId = orderId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "name: " + item.getName() + " quantity: " + quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, item, orderId, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLineItem other = (OrderLineItem) obj;
		return Objects.equals(id, other.id) && Objects.equals(item, other.item)
				&& Objects.equals(orderId, other.orderId) && Objects.equals(quantity, other.quantity);
	}
	
	

}

package com.qa.ims.persistence.domain;

import java.util.Objects;

public class OrderLineItem {
	private Long id;
	private Item item;
	private Long quantity;
	
	public OrderLineItem(Item item, Long quantity) {
		this.item = item;
		this.quantity = quantity;
	}

	public OrderLineItem(Long id, Item item, Long quantity) {
		this.id = id;
		this.item = item;
		this.quantity = quantity;
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

	@Override
	public String toString() {
		return "id: " + id + " item: " + item + " quantity: " + quantity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, item, quantity);
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
				&& Objects.equals(quantity, other.quantity);
	}

}

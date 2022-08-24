package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
	private Long id;
	private String orderNumber;
	private Customer customer;
	private List<OrderLineItem> orderLineItems = new ArrayList<>();
	private Double orderTotal;
	
	public Order(String orderNumber, Customer customer) {
		this.orderNumber = orderNumber;
		this.customer = customer;
	}

	public Order(Long id, String orderNumber, Customer customer) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.customer = customer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public void addOrderLineItem(OrderLineItem orderLineItem) {
		this.orderLineItems.add(orderLineItem);
	}
	
	public void removeOrderLineItem(OrderLineItem orderLineItem) {
		this.orderLineItems.remove(orderLineItem);
	}
	
	public void calculateTotal() {
		Double total = 0d;
		if (this.orderLineItems.size() > 0) {
			for (OrderLineItem lineItem : this.orderLineItems) {
				Double price = lineItem.getItem().getPrice();
				Long quantity = lineItem.getQuantity();
				total += price * quantity;
			}
			
		} 
		this.orderTotal = total;
	}

	@Override
	public String toString() {
		return "id: " + id + " orderNumber: " + orderNumber + " customer: " + customer + " items: " + orderLineItems + " total price " + orderTotal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customer, id, orderLineItems, orderNumber, orderTotal);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customer, other.customer) && Objects.equals(id, other.id)
				&& Objects.equals(orderLineItems, other.orderLineItems)
				&& Objects.equals(orderNumber, other.orderNumber) && Objects.equals(orderTotal, other.orderTotal);
	}

}

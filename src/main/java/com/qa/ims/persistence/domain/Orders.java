package com.qa.ims.persistence.domain;

import java.util.List;
import java.util.Objects;

public class Orders {

	private Long orderId;
	private Long customerId;
	
	public Orders(Long customerId) {
		super();
		this.setCustomerId(customerId);
	}

	public Orders(Long orderId, Long customerId) {
		super();
		this.setOrderId(orderId);
		this.setCustomerId(customerId);
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long id) {
		this.orderId = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "id: " + orderId + " customer ID: " + customerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, orderId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orders other = (Orders) obj;
		return Objects.equals(customerId, other.customerId) && Objects.equals(orderId, other.orderId);
	}
	
	
	
	
	
	
}

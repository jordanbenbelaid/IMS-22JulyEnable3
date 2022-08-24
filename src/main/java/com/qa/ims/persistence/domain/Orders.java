package com.qa.ims.persistence.domain;

import java.util.List;
import java.util.Objects;

public class Orders {

	private Long id;
	private Long customerId;
	
	public Orders(Long customerId) {
		super();
		this.setCustomerId(customerId);
	}

	public Orders(Long id, Long customerId) {
		super();
		this.setId(id);
		this.setCustomerId(customerId);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Override
	public String toString() {
		return "id: " + id + " customer ID: " + customerId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, id);
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
		return Objects.equals(customerId, other.customerId) && Objects.equals(id, other.id);
	}
	
	
	
	
	
	
}

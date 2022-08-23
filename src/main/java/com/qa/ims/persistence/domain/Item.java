package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Item {
	private Long id;
	private String name;
	private Double price;
	private Long stock;
	
	public Item(String name, Double price, Long stock) {
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public Item(Long id, String name, Double price, Long stock) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "id: " + id + " name: " + name + " price: " + price + " stock: " + stock;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price, stock);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price) && stock == other.stock;
	}

}

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
	
	public void updateStock(Long stock) {
		this.stock = this.getStock() + stock;
	}

	@Override
	public String toString() {
		return "id: " + id + " name: " + name + " price: " + price + " stock: " + stock;
	}

}

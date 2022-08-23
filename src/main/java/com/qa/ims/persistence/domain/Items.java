package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Items {

		private Long id;
		private String itemName;
		private double price;
		
		public Items(String itemName, double price) {
			this.setItemName(itemName);
			this.setPrice(price);
		}

		public Items(Long id, String itemName, double price) {
			this.setId(id);
			this.setItemName(itemName);
			this.setPrice(price);
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getItemName() {
			return itemName;
		}

		public void setItemName(String itemName) {
			this.itemName = itemName;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "id: " + id + " item name: " + itemName + " price: " + price;
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, itemName, price);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Items other = (Items) obj;
			return id == other.id && Objects.equals(itemName, other.itemName)
					&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
		
		
		
		
		
		
	}
	
	
}

package com.reachgoal.assignments.foodOrderingSystem.entities;

import java.util.Map;

public class Order {
	public String orderId;
	Map<Item, Integer> itemQuantityMap;

	public Order(String orderId, Map<Item, Integer> itemQuantityMap) {
		super();
		this.orderId = orderId;
		this.itemQuantityMap = itemQuantityMap;
	}

	public Map<Item, Integer> getItemQuantityMap() {
		return itemQuantityMap;
	}

	public void setItemQuantityMap(Map<Item, Integer> itemQuantityMap) {
		this.itemQuantityMap = itemQuantityMap;
	}

}

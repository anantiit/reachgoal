package com.naidu.java.designpatterns.observer;

import java.util.HashMap;
import java.util.Map;

public class InventoryRepository {
	Map<String, Item> inventory = new HashMap<String, Item>();

	public void addItem(Item item) {
		if (inventory.containsKey(item.getId())) {
			Item prevItem = inventory.get(item.getId());
			prevItem.setStockCount(prevItem.getStockCount() + item.getStockCount());
		}
		inventory.put(item.getId(), item);
	}

	public Item getItem(String itemId) {
		return inventory.get(itemId);
	}

	public void removeItem(Item item) {
		if (inventory.containsKey(item.getId())) {
			Item prevItem = inventory.get(item.getId());
			prevItem.setStockCount(prevItem.getStockCount() - item.getStockCount());
		}
	}
}

package com.reachgoal.assignments.foodOrderingSystem.services;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.reachgoal.assignments.foodOrderingSystem.entities.Item;
import com.reachgoal.assignments.foodOrderingSystem.entities.Order;

public class OrderExecutor implements Runnable {
	Order order;

	public OrderExecutor(Order order) {
		super();
		this.order = order;
	}

	boolean validateOrder() {
		Map<Item, Integer> orderItems = order.getItemQuantityMap();
		Set<Entry<Item, Integer>> items = orderItems.entrySet();
		for (Entry<Item, Integer> itemEntry : items) {
			Integer availableQuantity = availableIngrediants.get(itemEntry.getKey());
			if ((availableQuantity == null && itemEntry.getValue() != null && itemEntry.getValue() > 0)
					|| (itemEntry.getValue() != null && availableQuantity < itemEntry.getValue())) {
				System.out.println(
						beverageName + " cannot be prepared because item " + itemEntry.getKey() + " is not available");
				return false;
			}
		}
		return true;

	}

	void serveOrder() {
		ConcurrentHashMap<String, Integer> availableIngrediants = machine.getTotalIngrediantsMap();
		Iterator<Entry<String, Integer>> beverageIngrediantEntries = beverage.entrySet().iterator();
		while (beverageIngrediantEntries.hasNext()) {
			Entry<String, Integer> itemEntry = beverageIngrediantEntries.next();
			int availableQuantity = availableIngrediants.get(itemEntry.getKey());
			if (itemEntry.getValue() != null && availableQuantity - itemEntry.getValue() > 0) {
				availableIngrediants.put(itemEntry.getKey(), availableQuantity - itemEntry.getValue());
			}
		}
		System.out.println(beverageName + " is prepared");
	}

	@Override
	public void run() {
		try {
			synchronized (order) {
				if (validateOrder()) {
					serveOrder();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}

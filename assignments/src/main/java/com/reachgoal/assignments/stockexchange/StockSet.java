package com.reachgoal.assignments.stockexchange;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class StockSet {
	private final ConcurrentHashMap<String, Integer> stockSetMap = new ConcurrentHashMap<String, Integer>();
	private static StockSet instance;
	Set<String> stockSet = stockSetMap.newKeySet();

	private StockSet() {
	}

	public static StockSet getInstance() {
		synchronized (StockSet.class) {
			if (instance == null) {
				instance = new StockSet();
			}
		}
		return instance;
	}

	public void add(String stock) {
		stockSet.add(stock);
	}

	public boolean contains(String stock) {
		return stockSet.contains(stock);
	}

	public void remove(String stock) {
		if (stockSet.contains(stock)) {
			System.out.println("Removing stock {}" + stock);
			stockSet.remove(stock);
		} else {
			System.out.println("Nothing to remove");
		}
	}
}

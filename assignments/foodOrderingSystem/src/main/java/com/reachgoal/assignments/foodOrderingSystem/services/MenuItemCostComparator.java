package com.reachgoal.assignments.foodOrderingSystem.services;

import java.util.Comparator;

import com.reachgoal.assignments.foodOrderingSystem.entities.MenuItem;

public class MenuItemCostComparator implements Comparator<MenuItem> {

	@Override
	public int compare(MenuItem o1, MenuItem o2) {
		if (o1.cost < o2.cost) {
			return -1;
		}
		if (o1.cost > o2.cost) {
			return 1;
		}
		return 0;
	}

}

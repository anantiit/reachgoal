package com.reachgoal.assignments.foodOrderingSystem.entities;

import java.util.Set;

public class Restaurant {
	public String restName;
	public Set<MenuItem> menu;

	public Restaurant(String restName, Set<MenuItem> menu) {
		super();
		this.restName = restName;
		this.menu = menu;
	}

}

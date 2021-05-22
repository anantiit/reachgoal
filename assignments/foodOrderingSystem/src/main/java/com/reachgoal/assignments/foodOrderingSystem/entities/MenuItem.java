package com.reachgoal.assignments.foodOrderingSystem.entities;

public class MenuItem {
	public String restId;
	public String itemName;
	public Integer maxProcessingCapacity;
	public Double cost;

	public MenuItem(String restId, String itemName, Integer maxProcessingCapacity, Double cost) {
		super();
		this.restId = restId;
		this.itemName = itemName;
		this.maxProcessingCapacity = maxProcessingCapacity;
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemName == null) ? 0 : itemName.hashCode());
		result = prime * result + ((restId == null) ? 0 : restId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MenuItem other = (MenuItem) obj;
		if (itemName == null) {
			if (other.itemName != null)
				return false;
		} else if (!itemName.equals(other.itemName))
			return false;
		if (restId == null) {
			if (other.restId != null)
				return false;
		} else if (!restId.equals(other.restId))
			return false;
		return true;
	}

}

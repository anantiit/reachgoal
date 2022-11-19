package com.reachgoal.assignments.coffeemachine.data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.annotations.SerializedName;

public class Machine {
	private OutLets outlets;
	@SerializedName("total_items_quantity")
	private ConcurrentHashMap<String, Integer> totalIngrediantsMap;
	private Map<String, HashMap<String, Integer>> beverages;

	public Machine() {
	}

	public OutLets getOutlets() {
		return outlets;
	}

	public void setOutlets(OutLets outlets) {
		this.outlets = outlets;
	}

	public ConcurrentHashMap<String, Integer> getTotalIngrediantsMap() {
		return totalIngrediantsMap;
	}

	public void setTotalIngrediantsMap(ConcurrentHashMap<String, Integer> totalIngrediantsMap) {
		this.totalIngrediantsMap = totalIngrediantsMap;
	}

	public Map<String, HashMap<String, Integer>> getBeverages() {
		return beverages;
	}

	public void setBeverages(Map<String, HashMap<String, Integer>> beverages) {
		this.beverages = beverages;
	}

}

package com.reachgoal.hondasales.model;

public class VehicleModelWrapper {
	int count;
	String Message;
	String SearchCriteria;
	VehicleModel[] Results;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getSearchCriteria() {
		return SearchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		SearchCriteria = searchCriteria;
	}

	public VehicleModel[] getResults() {
		return Results;
	}

	public void setResults(VehicleModel[] results) {
		Results = results;
	}

}

package com.reachgoal.assignments.stockexchange;

public interface IRiskManagementSystem {
	public boolean isCapitalAdequate(Order order);

	public boolean isMarginRequirementSatisfied();

}

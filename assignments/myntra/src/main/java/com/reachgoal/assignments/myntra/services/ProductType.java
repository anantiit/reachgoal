package com.reachgoal.assignments.myntra.services;

public enum ProductType {
	MOBILE(3), TV(6), AC(1), REFRIGERATOR(2), WASHING_MACHINE(4), PROJECTOR(5);
	int rank;

	private ProductType(int rank) {
		this.rank = rank;
	}

}

package com.reachgoal.assignments.phonepe.services;

import java.util.Comparator;

import com.reachgoal.assignments.phonepe.entities.RentalPrice;

public class RentalPriceComparator implements Comparator<RentalPrice>, IComparator {

	public int compare(RentalPrice o1, RentalPrice o2) {
		if (o1.getCost() < o2.getCost()) {
			return -1;
		} else if (o1.getCost() > o2.getCost()) {
			return 1;
		}
		return 0;
	}

}

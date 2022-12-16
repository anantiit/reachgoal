package com.reachgoal.assignments.phonepe.services;

import com.reachgoal.assignments.phonepe.model.SelectionStrategy;

public class RentalSystemVehicleComparatorFactory {
	private static RentalPriceComparator rentalPriceComparator = new RentalPriceComparator();

	public RentalSystemVehicleComparatorFactory(RentalPriceComparator rentalPriceComparator) {
		super();
		this.rentalPriceComparator = rentalPriceComparator;
	}

	public static IComparator getComparatorBasedOnSelectionStrategy(SelectionStrategy slectionStrategy) {
		if (SelectionStrategy.LOW_RENTAL_PRICE == slectionStrategy) {
			return rentalPriceComparator;
		}
		return rentalPriceComparator;

	}

}

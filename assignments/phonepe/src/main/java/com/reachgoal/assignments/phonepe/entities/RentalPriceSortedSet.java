package com.reachgoal.assignments.phonepe.entities;

import java.util.Set;
import java.util.TreeSet;

import com.reachgoal.assignments.phonepe.model.SelectionStrategy;
import com.reachgoal.assignments.phonepe.model.VehicleType;
import com.reachgoal.assignments.phonepe.services.IComparator;
import com.reachgoal.assignments.phonepe.services.RentalSystemVehicleComparatorFactory;

public class RentalPriceSortedSet {
	IComparator rentalPriceComparator = RentalSystemVehicleComparatorFactory
			.getComparatorBasedOnSelectionStrategy(SelectionStrategy.LOW_RENTAL_PRICE);
	Set<RentalPrice> minRentalPriceSetForSedan = new TreeSet<RentalPrice>(rentalPriceComparator);
	Set<RentalPrice> minRentalPriceSetForHatchback = new TreeSet<RentalPrice>(rentalPriceComparator);
	Set<RentalPrice> minRentalPriceSetForSUV = new TreeSet<RentalPrice>(rentalPriceComparator);
	private static RentalPriceSortedSet instance;

	private RentalPriceSortedSet() {
	}

	public static RentalPriceSortedSet getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (RentalPriceSortedSet.class) {
			if (instance == null) {
				instance = new RentalPriceSortedSet();
			}
		}
		return instance;
	}

	public void add(RentalPrice rentalPrice) {
		Set<RentalPrice> minRentalPriceSet = getSoretedSetBasedOnVehicleType(rentalPrice.getVehicleType());
		minRentalPriceSet.add(rentalPrice);
	}

	public Set<RentalPrice> getSoretedSetBasedOnVehicleType(VehicleType VehicleType) {
		if (VehicleType.Hatchback == VehicleType) {
			return minRentalPriceSetForHatchback;
		}
		if (VehicleType.Sedan == VehicleType) {
			return minRentalPriceSetForSedan;
		}
		if (VehicleType.SUV == VehicleType) {
			return minRentalPriceSetForSUV;
		}
		return null;
	}

}

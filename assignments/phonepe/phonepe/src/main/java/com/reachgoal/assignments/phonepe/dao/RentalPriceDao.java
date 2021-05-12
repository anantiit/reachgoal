package com.reachgoal.assignments.phonepe.dao;

import com.reachgoal.assignments.phonepe.entities.RentalPrice;
import com.reachgoal.assignments.phonepe.entities.RentalPriceSortedSet;
import com.reachgoal.assignments.phonepe.model.VehicleType;

public class RentalPriceDao {

	public void addRentalPrice(String branchName, VehicleType vehicleType, Double price) {
		RentalPrice vehiclePrice = new RentalPrice(price, branchName, vehicleType);
		RentalPriceSortedSet vehiclePriceHeap = RentalPriceSortedSet.getInstance();
		vehiclePriceHeap.add(vehiclePrice);
	}

}

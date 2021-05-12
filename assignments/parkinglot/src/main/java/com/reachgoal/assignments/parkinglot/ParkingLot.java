package com.reachgoal.assignments.parkinglot;

import java.util.Map;

public class ParkingLot {
	String parkingLotID;
	Map<VehicleType, Integer> capacityMap;
	Map<VehicleType, Integer> rateMap;

	public ParkingLot(String parkingLotID, Map<VehicleType, Integer> capacityMap, Map<VehicleType, Integer> rateMap) {
		super();
		this.parkingLotID = parkingLotID;
		this.capacityMap = capacityMap;
		this.rateMap = rateMap;
	}

}

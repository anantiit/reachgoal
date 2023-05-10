package com.naidu.parkinglot.fee;

import java.util.HashMap;
import java.util.Map;

import com.naidu.parkinglot.entrygate.Ticket;
import com.naidu.parkinglot.parkingspot.ParkingSpotType;
import com.naidu.parkinglot.vehicle.VehicleType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class FeeModel {
	FeeDao feeDao;
	public static Map<VehicleType, ParkingSpotType> vehicleSpotMapping = new HashMap<VehicleType, ParkingSpotType>();
	static {
		vehicleSpotMapping.put(VehicleType.CAR, ParkingSpotType.FOUR_WHEELER);
		vehicleSpotMapping.put(VehicleType.SUV, ParkingSpotType.FOUR_WHEELER);
		vehicleSpotMapping.put(VehicleType.BUS, ParkingSpotType.HEAVY);
		vehicleSpotMapping.put(VehicleType.TRUCK, ParkingSpotType.HEAVY);
		vehicleSpotMapping.put(VehicleType.MotorCycle, ParkingSpotType.TWO_WHEELER);
		vehicleSpotMapping.put(VehicleType.SCOOTER, ParkingSpotType.TWO_WHEELER);
	}
	boolean isCumulative;

	public abstract double calculateCost(Ticket ticket);

}

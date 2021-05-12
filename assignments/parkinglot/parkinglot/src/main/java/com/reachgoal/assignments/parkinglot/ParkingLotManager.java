package com.reachgoal.assignments.parkinglot;

public class ParkingLotManager implements Runnable {
	RequestType requestType;
	Vehicle vehicle;
	String parkingLotUniqueID;

	public ParkingLotManager(RequestType requestType, Vehicle vehicle, String parkingLotUniqueID) {
		super();
		this.requestType = requestType;
		this.vehicle = vehicle;
		this.parkingLotUniqueID = parkingLotUniqueID;
	}

	@Override
	public void run() {
		try {
			if (RequestType.PARK_VEHICLE == requestType) {
				parkVehicle();
			} else if (RequestType.EXIT_VEHICLE == requestType) {
				exitVehicle();
			} else if (RequestType.VEHICLE_HISTORY == requestType) {
				vehicleHistory();
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	public static void parkVehicle() {

	}

	public static void exitVehicle() {

	}

	public static void vehicleHistory() {

	}
}

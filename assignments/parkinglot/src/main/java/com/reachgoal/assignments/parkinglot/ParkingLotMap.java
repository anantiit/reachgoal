package com.reachgoal.assignments.parkinglot;

import java.util.concurrent.ConcurrentHashMap;

public class ParkingLotMap {
	private final ConcurrentHashMap<String, ParkingLot> parkingLotMap = new ConcurrentHashMap<String, ParkingLot>();
	private static ParkingLotMap instance;

	private ParkingLotMap() {
	}

	public static ParkingLotMap getInstance() {
		if (instance != null) {
			return instance;
		}
		synchronized (ParkingLotMap.class) {
			if (instance == null) {
				instance = new ParkingLotMap();
			}
		}
		return instance;
	}

	public void put(String parkingLotUniqueID, ParkingLot parkingLot) {
		parkingLotMap.put(parkingLotUniqueID, parkingLot);
	}

	public ParkingLot get(String parkingLotUniqueID) {
		return parkingLotMap.get(parkingLotUniqueID);
	}

	public void remove(String parkingLotUniqueID) {
		if (parkingLotMap.containsKey(parkingLotUniqueID)) {
			System.out.println("Removing model " + parkingLotUniqueID);
			parkingLotMap.remove(parkingLotUniqueID);
		} else {
			System.out.println("Nothing to remove");
		}
	}
}

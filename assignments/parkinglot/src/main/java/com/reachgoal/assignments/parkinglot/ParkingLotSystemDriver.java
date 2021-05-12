package com.reachgoal.assignments.parkinglot;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.reachgoal.core_utils.ThreadExecutor;

/**
 * Hello world!
 *
 */
public class ParkingLotSystemDriver {

	private ParkingLotSystemDriver() {

	}

	public static void init() {
		// Assumptions
		int numOfParkingLots = 10;
		String parkingLotID1 = "pl1";
		String parkingLotID2 = "pl2";
		Map<VehicleType, Integer> capacityMap1 = new HashMap<VehicleType, Integer>();
		capacityMap1.put(VehicleType.TWO_WHEELER, 50);
		capacityMap1.put(VehicleType.HATCHBACK_CAR, 10);
		capacityMap1.put(VehicleType.SUV_CAR, 10);
		Map<VehicleType, Integer> rateMap1 = new HashMap<VehicleType, Integer>();
		rateMap1.put(VehicleType.TWO_WHEELER, 5);
		rateMap1.put(VehicleType.HATCHBACK_CAR, 10);
		rateMap1.put(VehicleType.SUV_CAR, 20);
		Map<VehicleType, Integer> capacityMap2 = new HashMap<VehicleType, Integer>();
		capacityMap2.put(VehicleType.TWO_WHEELER, 60);
		capacityMap2.put(VehicleType.HATCHBACK_CAR, 20);
		capacityMap2.put(VehicleType.SUV_CAR, 15);
		Map<VehicleType, Integer> rateMap2 = new HashMap<VehicleType, Integer>();
		rateMap2.put(VehicleType.TWO_WHEELER, 5);
		rateMap2.put(VehicleType.HATCHBACK_CAR, 10);
		rateMap2.put(VehicleType.SUV_CAR, 20);
		ParkingLot pl1 = new ParkingLot(parkingLotID1, capacityMap1, rateMap1);
		ParkingLot pl2 = new ParkingLot(parkingLotID2, capacityMap2, rateMap2);
		putParkingLot(parkingLotID1, pl1);
		putParkingLot(parkingLotID2, pl2);

	}

	private void initializeParkingLotSystem(int numOfParkingLots) throws InterruptedException {
		final ExecutorService executorService = Executors.newFixedThreadPool(numOfParkingLots, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				final Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
		final ThreadExecutor parkingLotSystemExecutor = new ThreadExecutor(executorService);
	}

	private void runCoffeeVedingMachine(int numOfParkingLots) throws InterruptedException {
		final ExecutorService executorService = Executors.newFixedThreadPool(numOfParkingLots, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				final Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
		final ThreadExecutor outLetExecutor = new ThreadExecutor(executorService);

	}

	public static void main() {
		init();
		ParkingLotSystemDriver parkingLotSystemDriver = new ParkingLotSystemDriver();
		parkingLotSystemDriver.runParkingLotSystem();
	}

	public void runParkingLotSystem() {
		while (true) {

		}

	}

	private void submitTask(ThreadExecutor parkingLotSystemExecutor, RequestType requestType, Vehicle vehicle,
			String parkingLotUniqueID) {

		try {
			ParkingLotMap instance = ParkingLotMap.getInstance();
			ParkingLotManager parkingLotManager = new ParkingLotManager(requestType, vehicle, parkingLotUniqueID);
			parkingLotSystemExecutor.submitTask(parkingLotManager);
		} catch (final Exception e) {
			System.out.println("Unexpected exception while submitting task " + e);
		}
	}

	public static void putParkingLot(String parkingLotUniqueID, ParkingLot parkingLot) {
		ParkingLotMap instance = ParkingLotMap.getInstance();
		instance.put(parkingLotUniqueID, parkingLot);
	}

}

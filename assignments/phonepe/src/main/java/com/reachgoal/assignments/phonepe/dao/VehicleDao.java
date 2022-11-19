package com.reachgoal.assignments.phonepe.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.reachgoal.assignments.phonepe.entities.Vehicle;
import com.reachgoal.assignments.phonepe.model.BranchVehicleType;
import com.reachgoal.assignments.phonepe.model.VehicleType;

public class VehicleDao {

	Map<BranchVehicleType, List<Vehicle>> vehicleMap = new HashMap<>();

	public void addVehicle(String registrationID, VehicleType vehicleType, String branchName) {
		Vehicle vehicle = new Vehicle(registrationID, vehicleType, branchName);
		List<Vehicle> vehicleList = vehicleMap.get(vehicle.getBranchVehicleType());
		if (vehicleList == null || vehicleList.isEmpty()) {
			vehicleList = new ArrayList<Vehicle>();
		}
		vehicleList.add(vehicle);
		vehicleMap.put(vehicle.getBranchVehicleType(), vehicleList);
		// System.out.println(vehicleMap);
	}

	public List<Vehicle> getVehicles(BranchVehicleType branchVehicleType) {
		List<Vehicle> vehicleList = vehicleMap.get(branchVehicleType);
		if (vehicleList == null || vehicleList.isEmpty()) {
			System.out.println("No vehicles avialable with in this " + branchVehicleType);
		}
		return vehicleList;
	}

	public Map<BranchVehicleType, List<Vehicle>> getAllVehicleMap() {
		return vehicleMap;
	}

}

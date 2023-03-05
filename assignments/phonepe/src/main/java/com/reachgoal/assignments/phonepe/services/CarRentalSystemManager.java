package com.reachgoal.assignments.phonepe.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import com.reachgoal.assignments.phonepe.controller.CarRentalSystem;
import com.reachgoal.assignments.phonepe.dao.BookingSlotDao;
import com.reachgoal.assignments.phonepe.dao.BranchDao;
import com.reachgoal.assignments.phonepe.dao.RentalPriceDao;
import com.reachgoal.assignments.phonepe.dao.VehicleDao;
import com.reachgoal.assignments.phonepe.entities.BookingSlot;
import com.reachgoal.assignments.phonepe.entities.Vehicle;
import com.reachgoal.assignments.phonepe.model.BranchVehicleType;
import com.reachgoal.assignments.phonepe.model.VehicleStatus;
import com.reachgoal.assignments.phonepe.model.VehicleType;

public class CarRentalSystemManager {
	BranchDao branchDao;
	RentalPriceDao rentalPriceDao;
	VehicleDao vehicleDao;
	BookingSlotDao bookingSlotDao;

	public CarRentalSystemManager(BranchDao branchDao, RentalPriceDao rentalPriceDao, VehicleDao vehicleDao,
			BookingSlotDao bookingSlotDao) {
		super();
		this.branchDao = branchDao;
		this.rentalPriceDao = rentalPriceDao;
		this.vehicleDao = vehicleDao;
		this.bookingSlotDao = bookingSlotDao;
	}

	public void addBranch(String branchName) {
		branchDao.addBranch(branchName);
	}

	public void allocatePrice(String branchName, VehicleType vehicleType, Double price) {
		rentalPriceDao.addRentalPrice(branchName, vehicleType, price);

	}

	public void addVehicle(String registrationID, VehicleType vehicleType, String branchName) {
		vehicleDao.addVehicle(registrationID, vehicleType, branchName);
	}

	public void viewInventory(String startDate, String endDate) {
		int startTime = getEpochFromDate(startDate);
		int endTime = getEpochFromDate(endDate);
		getAllVehicleStatusInGivenDuration(startTime, endTime);
	}

	private boolean checkIfVehicleIsAvailable(Vehicle vehicle, int t0, int t1) {
		TreeSet<BookingSlot> bookingSlots = bookingSlotDao.getBookingSlotsForVehicle(vehicle);
		if (bookingSlots == null || bookingSlots.isEmpty()) {
			return true;
		}
		for (BookingSlot slot : bookingSlots) {
			if (slot.getEndTime() < t0) {
				continue;
			}
			if (slot.getStartTime() < t1) {
				return false;
			}
		}
		return true;
	}

	public void getAllVehicleStatusInGivenDuration(int startTime, int endTime) {
		Map<BranchVehicleType, List<Vehicle>> vehiclesMap = vehicleDao.getAllVehicleMap();
		// System.out.println(vehiclesMap.keySet());

		for (BranchVehicleType branchVehicleType : vehiclesMap.keySet()) {
			System.out.println("Branch: " + branchVehicleType.getBranchName());
			List<Vehicle> vehicleList = vehiclesMap.get(branchVehicleType);
			for (Vehicle vehicle : vehicleList) {
				if (checkIfVehicleIsAvailable(vehicle, startTime, endTime)) {
					System.out.println(vehicle.getRegId() + " " + vehicle.getBranchVehicleType().getBranchName() + " "
							+ vehicle.getBranchVehicleType().getVehicleType() + " " + VehicleStatus.Avialable);
				} else {
					System.out.println(vehicle.getRegId() + " " + vehicle.getBranchVehicleType().getBranchName() + " "
							+ vehicle.getBranchVehicleType().getVehicleType() + " " + VehicleStatus.Booked);

				}
			}
		}
	}

	private int getEpochFromDate(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CarRentalSystem.DEFAULT_DATE_FORMAT);
		try {
			Date date = dateFormat.parse(dateStr);
			return (int) (date.getTime() / 1000l);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

}

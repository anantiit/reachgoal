package com.reachgoal.assignments.phonepe.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.reachgoal.assignments.phonepe.controller.CarRentalSystem;
import com.reachgoal.assignments.phonepe.dao.BookingSlotDao;
import com.reachgoal.assignments.phonepe.dao.VehicleDao;
import com.reachgoal.assignments.phonepe.entities.BookingSlot;
import com.reachgoal.assignments.phonepe.entities.RentalPrice;
import com.reachgoal.assignments.phonepe.entities.RentalPriceSortedSet;
import com.reachgoal.assignments.phonepe.entities.Vehicle;
import com.reachgoal.assignments.phonepe.model.VehicleType;

public class BookingExecutor {
	// private static final String DEFAULT_TIMEZONE = "Asia/Kolkata";
	VehicleDao vehicleDao;
	BookingSlotDao bookingSlotDao;

	public BookingExecutor(VehicleDao vehicleDao, BookingSlotDao bookingSlotDao) {
		super();
		this.vehicleDao = vehicleDao;
		this.bookingSlotDao = bookingSlotDao;
	}

	public Vehicle bookVehicle(VehicleType vehicleType, String startDate, String endDate) {
		int startTime = getEpochFromDate(startDate);
		int endTime = getEpochFromDate(endDate);
		RentalPriceSortedSet rentalPriceSortedSet = RentalPriceSortedSet.getInstance();
		Set<RentalPrice> minRentalPriceSet = rentalPriceSortedSet.getSoretedSetBasedOnVehicleType(vehicleType);
		Vehicle vehicle = getAvailableVehicleFromBranch(minRentalPriceSet, startTime, endTime);
		if (vehicle == null) {
			// System.out.println("No vehicle available");
			return null;
		}
		System.out.println("vehicle " + vehicle.getRegId() + " booked for slot " + startDate + "" + endDate);
		return vehicle;
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

	public Vehicle getAvailableVehicleFromBranch(Set<RentalPrice> minRentalPriceSet, int startTime, int endTime) {
		Vehicle availableVehicle = null;
		if (minRentalPriceSet == null) {
			return null;
		}
		for (RentalPrice rentalPrice : minRentalPriceSet) {
			List<Vehicle> vehicles = vehicleDao.getVehicles(rentalPrice.getBranchVehicleType());
			if (vehicles == null || vehicles.isEmpty()) {
				System.out.println("Vehicle not available");
				return null;
			}
			for (Vehicle vehicle : vehicles) {
				if (checkIfVehicleIsAvailable(vehicle, startTime, endTime)) {
					availableVehicle = vehicle;
					break;
				}
			}
			if (availableVehicle != null) {
				addBookingSlot(availableVehicle, startTime, endTime);
				break;
			}
		}
		if (availableVehicle == null) {
			System.out.println("Vehicle not available");
		}

		return availableVehicle;

	}

	private void addBookingSlot(Vehicle vehicle, int startTime, int endTime) {
		bookingSlotDao.addBookingSlot(vehicle, startTime, endTime);
	}

	private boolean checkIfVehicleIsAvailable(Vehicle vehicle, int t0, int t1) {
		TreeSet<BookingSlot> bookingSlots = getBookingSlotsForVehicle(vehicle);
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

	private TreeSet<BookingSlot> getBookingSlotsForVehicle(Vehicle vehicle) {
		return bookingSlotDao.getBookingSlotsForVehicle(vehicle);
	}

}

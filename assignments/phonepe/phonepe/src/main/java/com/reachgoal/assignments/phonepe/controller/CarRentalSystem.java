package com.reachgoal.assignments.phonepe.controller;

import com.reachgoal.assignments.phonepe.dao.BookingSlotDao;
import com.reachgoal.assignments.phonepe.dao.BranchDao;
import com.reachgoal.assignments.phonepe.dao.RentalPriceDao;
import com.reachgoal.assignments.phonepe.dao.VehicleDao;
import com.reachgoal.assignments.phonepe.entities.Vehicle;
import com.reachgoal.assignments.phonepe.model.VehicleType;
import com.reachgoal.assignments.phonepe.services.BookingExecutor;
import com.reachgoal.assignments.phonepe.services.CarRentalSystemManager;

/**
 * Hello world!
 *
 */
public class CarRentalSystem {
	CarRentalSystemManager carRentalSystemManager;
	BookingExecutor bookingExecutor;
	public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy HH:mm a";

	public CarRentalSystem(CarRentalSystemManager carRentalSystemManager, BookingExecutor bookingExecutor) {
		super();
		this.carRentalSystemManager = carRentalSystemManager;
		this.bookingExecutor = bookingExecutor;
	}

	public static void main(String[] args)

	{

		BranchDao branchDao = new BranchDao();
		RentalPriceDao rentalPriceDao = new RentalPriceDao();
		VehicleDao vehicleDao = new VehicleDao();
		BookingSlotDao bookingSlotDao = new BookingSlotDao();
		CarRentalSystemManager carRentalSystemManager1 = new CarRentalSystemManager(branchDao, rentalPriceDao,
				vehicleDao, bookingSlotDao);
		BookingExecutor bookingExecutor1 = new BookingExecutor(vehicleDao, bookingSlotDao);
		CarRentalSystem carRentalSystem = new CarRentalSystem(carRentalSystemManager1, bookingExecutor1);
		carRentalSystem.addBranch("Vasanth Vihar");
		carRentalSystem.addBranch("Cyber City");
		carRentalSystem.allocatePrice("Vasanth Vihar", VehicleType.Sedan, 100d);
		carRentalSystem.allocatePrice("Vasanth Vihar", VehicleType.Hatchback, 80d);
		carRentalSystem.allocatePrice("Cyber City", VehicleType.Sedan, 200d);
		carRentalSystem.allocatePrice("Cyber City", VehicleType.Hatchback, 50d);
		carRentalSystem.addVehicle("DL 01 MR 9310", VehicleType.Sedan, "Vasanth Vihar");
		carRentalSystem.addVehicle("DL 01 MR 9311", VehicleType.Sedan, "Cyber City");
		carRentalSystem.addVehicle("DL 01 MR 9312", VehicleType.Hatchback, "Cyber City");
		carRentalSystem.bookVehicle(VehicleType.Sedan, "29-02-2020 10:00 AM", "29-02-2020 01:00 PM");
		// "DL 01 MR 9310" from Vasanth Vihar booked.
		// Note: Since the strategy is lowest price first, the sedan was allocated from
		// Vasanth Vihar as the >>>price is lower as compared to Cyber City Branch.
		carRentalSystem.bookVehicle(VehicleType.Sedan, "29-02-2020 02:00 PM", "29-02-2020 03:00 PM");
		// "DL 01 MR 9310" from Vasanth Vihar booked.
		carRentalSystem.bookVehicle(VehicleType.Sedan, "29-02-2020 02:00 PM", "29-02-2020 03:00 PM");
		// "DL 01 MR 9311" from Cyber City booked.
		carRentalSystem.bookVehicle(VehicleType.Sedan, "29-02-2020 02:00 PM", "29-02-2020 03:00 PM");
		// NO SEDAN AVAILABLE
		carRentalSystem.viewInventory("29-02-2020 02:00 PM", "29-02-2020 03:00 PM");
//    	Branch: Vasanth Vihar
//    	Sedan DL 01 MR 9310 Booked
//    	Hatchback DL 01 MR 9312 Available
//    	Branch: Cyber City
//    	Sedan DL 01 MR 9311 Booked
		carRentalSystem.viewInventory("29-02-2020 04:00 PM", "29-02-2020 05:00 PM");
//    	Branch: Vasanth Vihar
//    	Sedan DL 01 MR 9310 Available
//    	Hatchback DL 01 MR 9312 Available
//    	Branch: Cyber City
//    	Sedan DL 01 MR 9311 Available

	}

	private void addBranch(String branchName) {
		carRentalSystemManager.addBranch(branchName);
	}

	private void allocatePrice(String branchName, VehicleType vehicleType, Double price) {
		carRentalSystemManager.allocatePrice(branchName, vehicleType, price);
	}

	private void addVehicle(String registrationID, VehicleType vehicleType, String branchName) {
		carRentalSystemManager.addVehicle(registrationID, vehicleType, branchName);
	}

	private void viewInventory(String startDate, String endDate) {
		carRentalSystemManager.viewInventory(startDate, endDate);
	}

	private Vehicle bookVehicle(VehicleType vehicleType, String startDate, String endDate) {
		return bookingExecutor.bookVehicle(vehicleType, startDate, endDate);
	}
}

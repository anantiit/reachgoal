package com.reachgoal.assignments.phonepe.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import com.reachgoal.assignments.phonepe.entities.BookingSlot;
import com.reachgoal.assignments.phonepe.entities.Vehicle;

public class BookingSlotDao {
	Map<String, TreeSet<BookingSlot>> bookingSlotMap = new HashMap<>();

	public void addBookingSlot(Vehicle vehicle, int startTime, int endTime) {
		TreeSet<BookingSlot> bookingSlotSet = bookingSlotMap.get(vehicle.getRegId());
		if (bookingSlotSet == null || bookingSlotSet.isEmpty()) {
			bookingSlotSet = new TreeSet<BookingSlot>();
		}
		bookingSlotSet.add(new BookingSlot(vehicle.getRegId(), startTime, endTime));
		bookingSlotMap.put(vehicle.getRegId(), bookingSlotSet);
		System.out.println(bookingSlotMap);
	}

	public TreeSet<BookingSlot> getBookingSlotsForVehicle(Vehicle vehicle) {
		TreeSet<BookingSlot> bookingSlotSet = bookingSlotMap.get(vehicle.getRegId());
		if (bookingSlotSet == null || bookingSlotSet.isEmpty()) {
			// System.out.println("No slots booked for vehicle " + vehicle);
			return null;
		}
		return bookingSlotSet;
	}
}

package com.reachgoal.assignments.phonepe.entities;

public class BookingSlot implements Comparable<BookingSlot> {
	String vehicleRegistrationNumber;
	int startTime;
	int endTime;

	public BookingSlot(String vehicleRegistrationNumber, int startTime, int endTime) {
		super();
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public String getVehicleRegistrationNumber() {
		return vehicleRegistrationNumber;
	}

	public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
		this.vehicleRegistrationNumber = vehicleRegistrationNumber;
	}

	@Override
	public int compareTo(BookingSlot o) {
		if (this.startTime < o.startTime) {
			return -1;
		} else if (this.startTime > o.startTime) {
			return 1;
		}
		return 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + startTime;
		result = prime * result + ((vehicleRegistrationNumber == null) ? 0 : vehicleRegistrationNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingSlot other = (BookingSlot) obj;
		if (endTime != other.endTime)
			return false;
		if (startTime != other.startTime)
			return false;
		if (vehicleRegistrationNumber == null) {
			if (other.vehicleRegistrationNumber != null)
				return false;
		} else if (!vehicleRegistrationNumber.equals(other.vehicleRegistrationNumber))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookingSlot [vehicleRegistrationNumber=" + vehicleRegistrationNumber + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}

}

package com.reachgoal.assignments.phonepe.entities;

import com.reachgoal.assignments.phonepe.model.BranchVehicleType;
import com.reachgoal.assignments.phonepe.model.VehicleType;

public class RentalPrice {

	Double cost;
	BranchVehicleType branchVehicleType;

	public RentalPrice(Double cost, String branchName, VehicleType vehicleType) {
		super();
		this.cost = cost;
		this.branchVehicleType = new BranchVehicleType(branchName, vehicleType);
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public BranchVehicleType getBranchVehicleType() {
		return branchVehicleType;
	}

	public void setBranchVehicleType(BranchVehicleType branchVehicleType) {
		this.branchVehicleType = branchVehicleType;
	}

	public VehicleType getVehicleType() {
		return getBranchVehicleType().getVehicleType();
	}

	// Note: we are not including cost in hashcode method
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchVehicleType == null) ? 0 : branchVehicleType.hashCode());
		return result;
	}
	// Note: we are not including cost in equals method

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RentalPrice other = (RentalPrice) obj;
		if (branchVehicleType == null) {
			if (other.branchVehicleType != null)
				return false;
		} else if (!branchVehicleType.equals(other.branchVehicleType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RentalPrice [cost=" + cost + ", branchVehicleType=" + branchVehicleType + "]";
	}

}

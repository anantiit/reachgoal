package com.reachgoal.assignments.phonepe.entities;

import com.reachgoal.assignments.phonepe.model.BranchVehicleType;
import com.reachgoal.assignments.phonepe.model.VehicleType;

public class Vehicle {
	String regId; // regId is unique but you are never using it in queries so store
					// BranchVehicleType as the key;

	BranchVehicleType branchVehicleType;

	public Vehicle(String regId, VehicleType vehicleType, String branchName) {
		super();
		this.regId = regId;
		this.branchVehicleType = new BranchVehicleType(branchName, vehicleType);
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public BranchVehicleType getBranchVehicleType() {
		return branchVehicleType;
	}

	public void setBranchVehicleType(BranchVehicleType branchVehicleType) {
		this.branchVehicleType = branchVehicleType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((regId == null) ? 0 : regId.hashCode());
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
		Vehicle other = (Vehicle) obj;
		if (regId == null) {
			if (other.regId != null)
				return false;
		} else if (!regId.equals(other.regId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Vehicle [regId=" + regId + ", branchVehicleType=" + branchVehicleType + "]";
	}

}

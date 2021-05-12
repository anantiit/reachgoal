package com.reachgoal.assignments.phonepe.model;

public class BranchVehicleType {
	String branchName;// Assuming unique as of now
	VehicleType vehicleType;

	public BranchVehicleType(String branchName, VehicleType vehicleType) {
		super();
		this.branchName = branchName;
		this.vehicleType = vehicleType;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result + ((vehicleType == null) ? 0 : vehicleType.hashCode());
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
		BranchVehicleType other = (BranchVehicleType) obj;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (vehicleType != other.vehicleType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BranchVehicleType [branchName=" + branchName + ", vehicleType=" + vehicleType + "]";
	}

}

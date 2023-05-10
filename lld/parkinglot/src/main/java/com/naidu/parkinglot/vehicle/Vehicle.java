package com.naidu.parkinglot.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Vehicle {
	String regNum;
	String ownerName;
	VehicleType vehicleType;
}

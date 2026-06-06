package com.naidu.java.designpatterns.strategypattern;

public class SportsVehicle extends Vehicle {
	SportsVehicle() {
		super(new SuperPickup());
	}
}

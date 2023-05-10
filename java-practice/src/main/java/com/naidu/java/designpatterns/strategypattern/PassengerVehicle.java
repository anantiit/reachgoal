package com.naidu.java.designpatterns.strategypattern;

public class PassengerVehicle extends Vehicle {

	PassengerVehicle() {
		super(new NormalPickup());
		// TODO Auto-generated constructor stub
	}

}

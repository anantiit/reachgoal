package com.naidu.java.designpatterns.strategypattern;

public class DriverClass {

	public static void main(String[] args) {
		Vehicle v = new Vehicle(new NormalPickup());
		Vehicle sv = new SportsVehicle();
		Vehicle pv = new PassengerVehicle();
		Vehicle orv = new OffRoadVehicle();
		v.pickup();
		sv.pickup();
		pv.pickup();
		orv.pickup();

	}

}

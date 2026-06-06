package com.naidu.java.designpatterns.strategypattern;

/*
 * Let us say we have sports vehicle, offroad vehicle and passenger vehicle
 * lets say some functionality of sports vehicle and offroad vehicle are same but they differ from passenger vehicle 
 * In such cases if we put the those functionality in each sub class then there will be same code in sports and offroad vehicles
 * In these cases we can use strategy pattern to resolve the issue of duplicacy. Model the functionality as different interface and extend different ways to that functionality and put that as composition in vehicle the corresponding functionality object.  
 */
public class Vehicle {
	PickupStrategy pickup;

	Vehicle(PickupStrategy pickup) {
		this.pickup = pickup;
	}

	public void pickup() {
		pickup.pickup();
	}
}

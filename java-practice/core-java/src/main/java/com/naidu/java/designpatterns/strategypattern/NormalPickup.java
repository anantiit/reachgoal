package com.naidu.java.designpatterns.strategypattern;

public class NormalPickup implements PickupStrategy {
	public void pickup() {
		System.out.println("Normal drive with normal pickup and cc");
	}
}

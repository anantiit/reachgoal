package com.naidu.java.designpatterns.strategypattern;

public class SuperPickup implements PickupStrategy {
	public void pickup() {
		System.out.println("Super drive with high pickup and cc");
	}
}

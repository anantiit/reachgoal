package com.naidu.parkinglot.payment;

public class Payment {
	public boolean makePayment(PaymentMode mode) {
		System.out.println("Payment done through : " + mode);
		return true;
	}
}

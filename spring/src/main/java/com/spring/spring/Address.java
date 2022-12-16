package com.spring.spring;

public class Address {
	String city;
	String zipCode;
	String street;

	public Address(String city, String zipCode, String street) {
		super();
		this.city = city;
		this.zipCode = zipCode;
		this.street = street;
	}

	@Override
	public String toString() {
		return "Address [city=" + city + ", zipCode=" + zipCode + ", street=" + street + "]";
	}

}

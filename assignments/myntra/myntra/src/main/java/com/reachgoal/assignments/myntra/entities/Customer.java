package com.reachgoal.assignments.myntra.entities;

public class Customer {
	String name;
	Double rating;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}
}
/*
 * Sample Input: Customer Ratings - Nitish,4.3 Amit,5.0 John,4.9 Kanhaiya,3.5
 * Iravati,4.9 Product Order - MOBILE,3 TV,5 AC,4 REFRIGERATOR,1
 * WASHING_MACHINE,4 PROJECTOR,5 Executive Slot Requests -
 * Nitish,MOBILE,10:15AM,12:00PM,8,37
 * Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97 Iravati,TV,05:00PM,06:00PM,13,78
 * Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 * John,PROJECTOR,05:30PM,06:00PM,-13,78 Format for each slot <Client
 * name>,<Product Type>,<Start Time>,<End Time>,<Location X>,<Location Y>
 * Expected Output - Amit,WASHING_MACHINE,11:45AM,01:30PM,19,73
 * Kanhaiya,REFRIGERATOR,03:00PM,05:00PM,68,97 Iravati,TV,05
 */
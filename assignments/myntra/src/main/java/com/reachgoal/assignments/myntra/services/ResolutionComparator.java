package com.reachgoal.assignments.myntra.services;

import java.util.Comparator;

import com.reachgoal.assignments.myntra.entities.Customer;
import com.reachgoal.assignments.myntra.entities.ExecutiveSlot;

public class ResolutionComparator implements Comparator<ExecutiveSlot> {
	@Override
	public int compare(ExecutiveSlot e1, ExecutiveSlot e2) {
//		1. The system has a way of rating customers on a scale of 1-5. So, a customer with a higher rating will be preferred over a customer of lower rating. For equal ratings, any of them can be selected. 
//		2. The company has defined an order of product types based on their service cost. Servicing products of lower rank will be preferred over higher rank. If the products are of the same rank, anyone can be selected. 
//		3. The time slot which has an earlier start time should be selected. 
//		4. Since the company is new, it has only one office location. Customers closer to that office location will be given more preference over the farther ones. 
//		5. If all the above parameters are the same, any of the slots can be selected. 

		Customer c1 = getCustomerByNameId(e1.getClientName());
		Customer c2 = getCustomerByNameId(e2.getClientName());

		if (c1.getRating() > c2.getRating()) {
			return -1;
		} else if (c1.getRating() < c2.getRating()) {
			return 1;
		} else if (e1.getProductType().rank < e1.getProductType().rank) {
			return -1;
		} else if (e1.getProductType().rank > e1.getProductType().rank) {
			return 1;
		} else if (e1.getStartTime() < e2.getStartTime()) {
			return -1;
		} else if (e1.getStartTime() > e2.getStartTime()) {
			return 1;
		} else if (e1.getDistanceFromServiceLocation() < e2.getDistanceFromServiceLocation()) {
			return -1;
		} else if (e1.getDistanceFromServiceLocation() > e2.getDistanceFromServiceLocation()) {
			return 1;
		}
	}
}
package com.csfundamentals.algo.recursion;

import java.util.Arrays;
import java.util.List;

/*
 * Problem Statement: We have some budget for shppping and we want 4 items and the each item available at different prices given as array for each item how many ways we can buy those 4 items with in the budget allocated.
 * Input: budgetamount, jeans[], shoes[], skirt[] , top[]
 */
public class BuyOptionCount {
	public static int buyOptionCount = 0;

	public static long buyOptionCount(long budgetamount, List<Integer> jeans, List<Integer> shoes, List<Integer> skirt,
			List<Integer> top, int counter) {

		if (budgetamount < 0) {
			return buyOptionCount;
		}

		if (counter == 4) {
			if (budgetamount >= 0) {
				return buyOptionCount++;
			} else {
				return buyOptionCount;
			}
		}

		if (counter == 0) {
			for (int i = 0; i < jeans.size(); i++) {
				buyOptionCount(budgetamount - jeans.get(i), jeans, shoes, skirt, top, counter + 1);
			}
		}
		if (counter == 1) {
			for (int j = 0; j < shoes.size(); j++) {
				buyOptionCount(budgetamount - shoes.get(j), jeans, shoes, skirt, top, counter + 1);
			}
		}
		if (counter == 2) {
			for (int k = 0; k < skirt.size(); k++) {
				buyOptionCount(budgetamount - skirt.get(k), jeans, shoes, skirt, top, counter + 1);
			}
		}
		if (counter == 3) {
			for (int l = 0; l < top.size(); l++) {

				buyOptionCount(budgetamount - top.get(l), jeans, shoes, skirt, top, counter + 1);
			}
		}
		return buyOptionCount;

	}

	public static void main(String args[]) {
		long budgetamount = 11;
		List<Integer> jeans = Arrays.asList(2, 3);
		List<Integer> shoes = Arrays.asList(4, 3);
		List<Integer> skirt = Arrays.asList(3, 4);
		List<Integer> top = Arrays.asList(2, 3);
		int counter = 0;
		System.out.println(buyOptionCount(budgetamount, jeans, shoes, skirt, top, counter));
	}

}

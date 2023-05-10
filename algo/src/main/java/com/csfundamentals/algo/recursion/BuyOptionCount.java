package com.csfundamentals.algo.recursion;

import java.util.Arrays;
import java.util.List;

/*
 * Problem Statement: We have some budget for shppping and we want 4 items and the each item available at different prices
 *  given as array for each item. How many ways we can buy exactly 1 item from those with in the budget allocated.
 * Input: budgetamount, jeans[], shoes[], skirt[] , top[]
 */
public class BuyOptionCount {

	public static long buyOptionCount(long budgetamount, List<Integer> jeans, List<Integer> shoes, List<Integer> skirt,
			List<Integer> top, int counter) {

		if (budgetamount < 0) {
			return 0;
		}

		if (counter == 4) {
			if (budgetamount >= 0) {
				return 1;
			} else {
				return 0;
			}
		}

		int buyOptionCount = 0;

		if (counter == 0) {
			for (int i = 0; i < jeans.size(); i++) {
				buyOptionCount += buyOptionCount(budgetamount - jeans.get(i), jeans, shoes, skirt, top, counter + 1);
			}
		}
		if (counter == 1) {
			for (int j = 0; j < shoes.size(); j++) {
				buyOptionCount += buyOptionCount(budgetamount - shoes.get(j), jeans, shoes, skirt, top, counter + 1);
			}
		}
		if (counter == 2) {
			for (int k = 0; k < skirt.size(); k++) {
				buyOptionCount += buyOptionCount(budgetamount - skirt.get(k), jeans, shoes, skirt, top, counter + 1);
			}
		}
		if (counter == 3) {
			for (int l = 0; l < top.size(); l++) {

				buyOptionCount += buyOptionCount(budgetamount - top.get(l), jeans, shoes, skirt, top, counter + 1);
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

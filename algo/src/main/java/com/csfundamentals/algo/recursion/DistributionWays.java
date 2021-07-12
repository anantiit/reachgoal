package com.csfundamentals.algo.recursion;

public class DistributionWays {
	/**
	 * distribute n chocolates to m boys by not giving more than c to any boy
	 * 
	 * @return numOfWaysToDistribute
	 * 
	 *         2,2,2: 02,20,11,
	 */
	public static int numOfWaysToDistribute(int n, int m, int c) {
		if (m == 0 && n == 0) {
			return 1;
		}
		if (m <= 0) {
			return 0;
		}
		if (n < 0) {
			return 0;
		}
		if (m == 1 && n <= c) {
			return 1;
		}
		if (m == 1 && n > c) {
			return 0;
		}
		int totalWays = 0;
		for (int i = 1; i <= m; i++) {
			for (int j = 0; j <= c; j++) {
				int ways = numOfWaysToDistribute(n - j, m - 1, c);
				if (ways > 0) {
					totalWays = totalWays + ways;
					System.out.println(
							"n:" + (n - j) + "m:" + (m - 1) + "c:" + c + "ways:" + ways + "totalWays:" + totalWays);
				}
			}
		}
		return totalWays;
	}

	public static void main(String args[]) {
		System.out.println(numOfWaysToDistribute(2, 2, 2));
	}
}

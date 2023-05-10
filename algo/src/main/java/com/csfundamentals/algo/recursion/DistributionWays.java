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
		// i<=m will recount the possible ways. We dont need to count this as the
		// remaining chocolates need to be given to m th person
		int totalWays = 0;
		for (int j = 0; j <= c; j++) {
			System.out.print("m:" + m + "j:" + j);
			totalWays += numOfWaysToDistribute(n - j, m - 1, c);
			System.out.println();
			System.out.println("n:" + n + "m:" + m + "c:" + c + "j:" + j + "totalWays :" + totalWays);
		}
		return totalWays;
	}

	public static void main(String args[]) {
		System.out.println(numOfWaysToDistribute(4, 3, 2));
	}
}

package com.algo.dynamicprogramming;

public class StairCase {
	static int possibleSteps(int n) {
		int[] dp = new int[n + 1];
		dp[0] = 1;
		dp[1] = 1;
		for (int i = 2; i <= n; i++) {
			dp[i] = dp[i - 1] + dp[i - 2];
		}
		// System.out.println(Arrays.toString(dp));
		return dp[n];
	}

	public static void main(String[] args) {
		System.out.println(possibleSteps(10));
	}

}
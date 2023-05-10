package com.algo.dynamicprogramming;

import java.util.Arrays;

public class CoinChange {

	/*
	 * Recursive function for this problem cc = Coins Count cc(coins[1..m],sum) =
	 * min( cc(coins[1..m-1],sum-coins[m]), cc(coins[1..m-1],sum) ) Including last
	 * coin and not including it.
	 */

	public static long count(int a[], int n, int v) {
		long[][] dp = new long[n + 1][v + 1];
		for (long[] row : dp)
			Arrays.fill(row, 0l);
		long res = coinchangeBottomUp(a, v, n, dp);
		for (int i = 1; i < n + 1; i++) {
			System.out.println(Arrays.toString(dp[i]));
		}
		return res;
	}

	static long coinchangeBottomUp(int[] a, int v, int n, long[][] dp) {
		for (int j = 0; j < v + 1; j++) {
			dp[0][j] = 0;
		}
		for (int i = 0; i < n + 1; i++) {
			dp[i][0] = 1;
		}
		for (int j = 1; j < v + 1; j++) {
			for (int i = 1; i < n + 1; i++) {
				if (a[i - 1] <= j) {
					dp[i][j] = dp[i][j - a[i - 1]] + dp[i - 1][j];
				} else {
					dp[i][j] = dp[i - 1][j];
				}
			}
		}
		return dp[n][v];
	}

	static long coinchangeTopDown(int[] a, int v, int n, long[][] dp) {
		if (v == 0)
			return dp[n][v] = 1l;
		if (n == 0)
			return 0;
		if (dp[n][v] != -1)
			return dp[n][v];
		if (a[n - 1] <= v) {

			// Either Pick this coin or not
			return dp[n][v] = coinchangeTopDown(a, v - a[n - 1], n, dp) + coinchangeTopDown(a, v, n - 1, dp);
		} else // We have no option but to leave this coin
			return dp[n][v] = coinchangeTopDown(a, v, n - 1, dp);
	}

	static int minCoins(int coins[], int m, int V) {
		// base case
		if (V == 0)
			return 0;

		// Initialize result
		int res = Integer.MAX_VALUE;

		// Try every coin that has smaller value than V
		for (int i = 0; i < m; i++) {
			if (coins[i] <= V) {
				int sub_res = minCoins(coins, m, V - coins[i]);

				// Check for INT_MAX to avoid overflow and see if
				// result can minimized
				if (sub_res != Integer.MAX_VALUE && sub_res + 1 < res)
					res = sub_res + 1;
			}
		}
		return res;
	}

	static int minCoinsUsingBottomUpMemoization(int coins[], int n, int V) {
		// table[i] will be storing
		// the minimum number of coins
		// required for i value. So
		// table[V] will have result
		int table[] = new int[V + 1];

		// Base case (If given value V is 0)
		table[0] = 0;

		// Compute minimum coins required for all
		// values from 1 to V
		for (int i = 1; i <= V; i++) {
			// Initialize all table values as Infinite
			table[i] = Integer.MAX_VALUE;

			// Go through all coins smaller than i
			for (int j = 0; j < n; j++)
				if (coins[j] <= i) {
					int sub_res = table[i - coins[j]];
					if (sub_res != Integer.MAX_VALUE && sub_res + 1 < table[i])
						table[i] = sub_res + 1;

				}

		}

		if (table[V] == Integer.MAX_VALUE)
			return -1;

		return table[V];

	}

	// Driver code
	public static void main(String[] args) {
		int tc = 1;
		while (tc != 0) {
			int n, v;
			n = 3;
			v = 17;
			int[] a = { 4, 2, 3 };
			int[][] dp = new int[n + 1][v + 1];

			System.out.println("Minimum coins required is " + minCoinsUsingBottomUpMemoization(a, n, v));
			// System.out.println("Minimum coins required is " + minNumCoins(a, n, v, dp));
//			for (int i = 0; i < n; i++) {
//				System.out.println(Arrays.toString(dp));
//			}
			System.out.println(" number of ways " + count(a, n, v));
			tc--;
		}
	}

}
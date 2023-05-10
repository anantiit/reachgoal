package com.algo.dynamicprogramming;

import java.util.Arrays;

public class DynamicProgrammingExamples {
	// Bottomup memoization
	public static int T(int n) {
		if (n == 0)
			return 2;
		int[] t = new int[n + 1];
		t[0] = 2;
		t[1] = 2;

		for (int i = 2; i < n + 1; i++) {
			t[i] = 0;
			for (int j = 1; j < i; j++) {
				t[i] = t[i] + 2 * t[j] * t[j - 1];
			}
		}
		return t[n];
	}

	// Bottomup memoization
	public static int TOptimized(int n) {
		if (n < 1)
			return 2;
		int[] t = new int[n + 1];
		if (n == 2)
			return 2 * t[0] * t[1];
		t[0] = 2;
		t[1] = 2;
		t[2] = 2 * t[0] * t[1];
		for (int i = 2; i < n + 1; i++) {
			t[i] = t[i - 1] + 2 * t[i - 1] * t[i - 2];
		}
		return t[n];
	}

	public static int longestCommonSubsequence(String s1, String s2) {
		int[][] lcs = new int[s1.length() + 1][s2.length() + 1];
		for (int i = 0; i < s1.length(); i++) {
			for (int j = 0; j < s2.length(); j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					// System.out.print(s1.charAt(i));
					lcs[i + 1][j + 1] = lcs[i][j] + 1;
				} else {
					lcs[i + 1][j + 1] = Math.max(lcs[i][j + 1], lcs[i + 1][j]);
				}
			}
		}
		System.out.println();
		for (int i = 0; i <= s1.length(); i++) {
			System.out.println(Arrays.toString(lcs[i]));
		}
		StringBuffer lcsString = new StringBuffer();
		for (int i = s1.length(), j = s2.length(); i > 0 && j > 0;) {
			if (lcs[i][j] == lcs[i - 1][j]) {
				i--;
			} else if (lcs[i][j] == lcs[i][j - 1]) {
				j--;
			} else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
				lcsString.append(s1.charAt(i - 1));
				i--;
				j--;
			}

		}
		System.out.println(lcsString.reverse());
		return lcs[s1.length()][s2.length()];

	}

	public static int count(int S[], int m, int n) {
		// table[i] will be storing the number of solutions for
		// value i. We need n+1 rows as the table is constructed
		// in bottom up manner using the base case (n = 0)
		int table[] = new int[n + 1];

		// Base case (If given value is 0)
		table[0] = 1;

		// Pick all coins one by one and update the table[] values
		// after the index greater than or equal to the value of the
		// picked coin
		for (int i = 0; i < m; i++) {
			for (int j = S[i]; j <= n; j++) {
				table[j] += table[j - S[i]];
			}
			System.out.println(Arrays.toString(table));
		}

		return table[n];
	}

	public static int longestIncreasingSubsequence(int[] a) {
		int n = a.length;
		int LIS[] = new int[n];
		LIS[0] = 1;
		// LIS[i] is the longest increasing subsequence from 0 to i where increasing
		// sequence ending at a[i] LIS[i] = LIS[j]+1
		// where j=0toi-1
		int globalLIS = 1;
		for (int i = 1; i < n; i++) {
			LIS[i] = 1;
			int j = 0;
			for (j = 0; j < i; j++) {
				if (LIS[j] >= LIS[i] && a[i] > a[j]) {
					LIS[i] = LIS[j] + 1;
					System.out.println("i:" + i + " j:" + j + " LIS[j]:" + LIS[j]);
				}
			}
			if (globalLIS < LIS[i]) {
				globalLIS = LIS[i];
			}
			System.out.println("i: " + i + " j: " + j + " LIS[i]: " + LIS[i] + " globalLIS: " + globalLIS);

		}
		return globalLIS;
	}

	public static int fibonacci(int n, int[] fib) {
		if (n == 0) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		if (fib[n] > 0) {
			return fib[n];
		}
		return fib[n] = fibonacci(n - 1, fib) + fibonacci(n - 2, fib);
	}

	public static void main(String args[]) {
		int arr[] = { 10, 22, 9, 33, 21, 50, 41, 2 };
		int a[] = { 15, 14, 13, 11, 9, 8, 6, 1 };
		System.out.println(longestIncreasingSubsequence(a));
		int n = 12;
//		int[] fib = new int[n + 1];
//		System.out.println(fibonacci(n, fib));
//		System.out.println(Arrays.toString(fib));
		String x = "ABCBDAB";
		String y = "BDCABC";
		System.out.println(longestCommonSubsequence(x, y));
	}

}

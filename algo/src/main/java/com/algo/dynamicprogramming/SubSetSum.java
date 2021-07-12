package com.algo.dynamicprogramming;

import java.util.Arrays;

/**
 * Subset sum: Given array of elements A1..An find sub set such that sum of all
 * the numbers in subset is T
 */
public class SubSetSum {
	public static boolean isSubSetSum(int[] arr, int sum) {
		int n = arr.length;
		int[][] table = new int[n + 1][sum + 1];
		for (int j = 0; j < sum + 1; j++) {
			table[0][j] = 0;
		}
		for (int i = 0; i < n + 1; i++) {
			table[i][0] = 1;
		}
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < sum + 1; j++) {
				table[i][j] = table[i - 1][j];
				if (arr[i - 1] <= j) {
					table[i][j] = Math.max(table[i - 1][j], table[i - 1][j - arr[i - 1]]);
				}
			}
		}
		for (int i = 0; i < n + 1; i++) {
			System.out.println(Arrays.toString(table[i]));
		}
		printSolutions(table, arr, sum);
		return (table[n][sum] == 1);
	}

	private static void printSolutions(int[][] table, int[] arr, int sum) {
		for (int j = sum; j >= 0; j++) {

		}
	}

	public static void main(String args[]) {
		int set[] = { 3, 34, 4, 12, 5, 2 };
		int sum = 9;
		if (isSubSetSum(set, sum) == true)
			System.out.println("Found a subset" + " with given sum");
		else
			System.out.println("No subset with" + " given sum");
	}
}

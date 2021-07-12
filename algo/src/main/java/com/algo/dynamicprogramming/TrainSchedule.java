package com.algo.dynamicprogramming;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

public class TrainSchedule {
	public static void main(String args[]) {
		int arr[] = { 910, 930, 940, 1050 };
		int dep[] = { 935, 950, 1040, 1110 };
		double profit[] = { 12, 14, 13, 18 };
		int npf = 3;
		int n = arr.length;
		int maxprofit[] = new int[n];

		System.out.println("Minimum Number of Platforms Required = " + findPlatform(arr, dep, n));
		System.out.println(findHighProfitTrainSchedule(arr, dep, profit, npf, maxprofit, n));

	}

	// A utility function that returns maximum of two integers
	private static double max(double a, double b) {
		return (a > b) ? a : b;
	}

	// Returns the maximum value that can be put in a knapsack of capacity W
	public static double findHighProfitTrainSchedule(int[] arr, int[] dep, double[] profit, int npf, int maxprofit[],
			int n) {
		double K[][] = new double[n][npf];
		Set<Integer> maxProfitSchedule;
		// Sort arrival and departure arrays
		Arrays.sort(dep);
		// Build table K[][] in bottom up manner
		int i = 1;

		// Similar to merge in merge sort to process
		// all events in sorted order
		for (int pf = 1; pf < npf; pf++) {
			maxProfitSchedule = new TreeSet<Integer>();
			while (i < n) {
				if (arr[i] <= dep[i - 1]) {
					K[i][pf] = profit[i] + K[i - 1][pf - 1];
					if (K[i][pf] < K[i - 1][pf]) {
						K[i][pf] = K[i - 1][pf];
						maxProfitSchedule.add(i - 1);
					}
					i++;
				} else {
					K[i][pf] = K[i - 1][pf];
				}
			}
			System.out.println("maxProfitSchedule :" + maxProfitSchedule + "profit" + K[n][pf]);

		}

		return K[n][npf];
	}

	// Returns minimum number of platforms reqquired
	static int findPlatform(int arr[], int dep[], int n) {
		// Sort arrival and departure arrays
		Arrays.sort(arr);
		Arrays.sort(dep);

		// plat_needed indicates number of platforms
		// needed at a time
		int plat_needed = 1, result = 1;
		int i = 1, j = 0;

		// Similar to merge in merge sort to process
		// all events in sorted order
		while (i < n && j < n) {
			// If next event in sorted order is arrival,
			// increment count of platforms needed
			if (arr[i] <= dep[j]) {
				plat_needed++;
				i++;

				// Update result if needed
				if (plat_needed > result)
					result = plat_needed;
			}

			// Else decrement count of platforms needed
			else {
				plat_needed--;
				j++;
			}
		}

		return result;
	}

}

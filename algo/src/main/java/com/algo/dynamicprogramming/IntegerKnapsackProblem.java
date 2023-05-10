package com.algo.dynamicprogramming;

/**
 * Problem statement Integer knapsack problem: Given number of items and a bag
 * with capacity W, each item can either be placed in bag wholly or we dont
 * place. Each item is associated with profit & weight while we place it in bag
 * we need to maximize profit
 */
public class IntegerKnapsackProblem {

	// Returns the maximum value that can
	// be put in a knapsack of capacity W
	public static int knapSack(int W, int wt[], int val[], int n) {
		int i, w;
		int K[][] = new int[n + 1][W + 1];

		// Build table K[][] in bottom up manner
		for (i = 0; i <= n; i++) {
			for (w = 0; w <= W; w++) {
				if (i == 0 || w == 0)
					K[i][w] = 0;
				else if (wt[i - 1] <= w)
					K[i][w] = Math.max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
				else
					K[i][w] = K[i - 1][w];
			}
		}

		return K[n][W];
	}

	public static int knapsackWithDuplicates(int[] weights, int[] values, int capacity) {
		int n = weights.length;
		int[] K = new int[capacity + 1];
		for (int w = 1; w <= capacity; w++) {
			K[w] = Integer.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				if (w >= weights[i] && (K[w] < (K[w - weights[i]] + values[i]))) {
					K[w] = K[w - weights[i]] + values[i];
				}
			}
		}
		return K[capacity];
	}

	public static void main(String args[]) {
		int value[] = { 120, 100, 60 };
		int weight[] = { 30, 20, 10 };
		int capacity = 50;
		System.out.println(knapSack(capacity, weight, value, weight.length));

		int W = 8;
		int val[] = { 10, 40, 50, 70 };
		int wt[] = { 1, 3, 4, 5 };

		int W1 = 100;
		int val1[] = { 10, 30, 20 };
		int wt1[] = { 5, 10, 15 };
		System.out.println(knapsackWithDuplicates(wt1, val1, W1));
	}

}

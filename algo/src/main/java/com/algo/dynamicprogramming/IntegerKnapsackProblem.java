package com.algo.dynamicprogramming;

/**
 * Problem statement Integer knapsack problem: Given number of items and a bag
 * with capacity W, each item can either be placed in bag wholly or we dont
 * place. Each item is associated with profit if we place it in bag
 */
public class IntegerKnapsackProblem {
	public static int knapsack(int[] weights, int[] values, int capacity) {
		int n = weights.length;
		int[][] K = new int[n + 1][capacity + 1];
		for (int i = 1; i <= n; i++) {
			for (int j = 0; j <= capacity; j++) {
				if (j >= weights[i - 1]) {
					K[i][j] = Math.max(K[i - 1][j - weights[i - 1]] + values[i - 1], K[i - 1][j]);
				} else {
					K[i][j] = K[i - 1][j];
				}
			}
		}
		return K[n][capacity];
	}

	static int max(int a, int b) {
		return (a > b) ? a : b;
	}

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
					K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
				else
					K[i][w] = K[i - 1][w];
			}
		}

		return K[n][W];
	}

	public static int knapsackWithDuplicates(int[] weights, int[] values, int capacity) {
		int n = weights.length;
		int[] K = new int[capacity + 1];
		for (int j = 1; j <= capacity; j++) {
			K[j] = Integer.MIN_VALUE;
			for (int i = 0; i < n; i++) {
				if (j >= weights[i] && (K[j] < (K[j - weights[i]] + values[i]))) {
					K[j] = K[j - weights[i]] + values[i];
				}
			}
		}
		return K[capacity];
	}

	public static void main(String args[]) {
		int value[] = { 120, 100, 60 };
		int weight[] = { 30, 20, 10 };
		int capacity = 50;
		System.out.println(knapsack(weight, value, capacity));
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

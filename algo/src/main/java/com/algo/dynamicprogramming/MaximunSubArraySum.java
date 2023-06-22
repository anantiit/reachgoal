package com.algo.dynamicprogramming;

public class MaximunSubArraySum {
	public static int maximunSubArraySum(int[] a) {
		int n = a.length;
		int sum = 0;
		int globalMaxSum = Integer.MIN_VALUE;
		for (int i = 0; i < n; i++) {
			sum = Math.max(sum + a[i], a[i]);
			globalMaxSum = Math.max(globalMaxSum, sum);
		}
		return globalMaxSum;
	}

	public static int maximunSubArrayProduct(int[] a) {
		int n = a.length;
		int product = 1;
		int maxProduct = 0;
		for (int i = 0; i < n; i++) {
			product = Math.max(product * a[i], a[i]);
			maxProduct = Math.max(maxProduct, product);
		}
		return maxProduct;
	}

	public static void main(String args[]) {
		// int[] arr = { -4, 3, 2, 1, -9, -2, 13, -1, 5, 6, 20, -1 };
		int arr[] = { 6, 3, 10, 0, 2 };
		System.out.println(maximunSubArrayProduct(arr));
	}
}

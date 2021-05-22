package com.problemsolving.dynamicprogramming;

public class MaximunSubArraySum {
	public static int maximunSubArraySum(int[] a) {
		int n = a.length;
		int sum = 0;
		int maxSum = 0;
		for (int i = 0; i < n; i++) {
			sum = Math.max(sum + a[i], a[i]);
			maxSum = Math.max(maxSum, sum);
		}
		return maxSum;
	}

	public static void main(String args[]) {
		int[] arr = { -4, 3, 2, 1, -9, -2, 13, -1, 5, 6, 20, -1 };
		System.out.println(maximunSubArraySum(arr));
	}
}

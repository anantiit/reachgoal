package com.algo.dynamicprogramming;

public class MaximunSubArraySum {

	public static int maximunSubArraySumKadane(int[] a) {
		int max_so_far = Integer.MIN_VALUE;
		int max_ending_here = 0;
		int x = 0;
		int y = 0;
		int n = a.length;
		int globalX = 0;
		int globalY = 0;
		for (int i = 0; i < n; i++) {
			max_ending_here = max_ending_here + a[i];
			if (max_so_far < max_ending_here) {
				max_so_far = max_ending_here;
				y = i;
			}
			if (max_ending_here < 0) {
				max_ending_here = 0;
			}
		}
		System.out.println("x:" + x + "y:" + y);
		return max_so_far;

	}

	public static int minimumSubArraySumKadane(int[] a) {
		int min_so_far = Integer.MAX_VALUE;
		int min_ending_here = 0;
		int x = 0;
		int y = 0;
		int n = a.length;
		int globalX = 0;
		int globalY = 0;
		for (int i = 0; i < n; i++) {
			min_ending_here = min_ending_here + a[i];
			if (min_so_far > min_ending_here) {
				min_so_far = min_ending_here;
				y = i;
			}
			if (min_ending_here > 0) {
				min_ending_here = 0;
				x = i;
			}
		}
		System.out.println("x:" + x + "y:" + y);
		return min_so_far;

	}

	public static int maximunSubArraySum(int[] a) {
		int n = a.length;
		int sum = 0;
		int globalMaxSum = Integer.MIN_VALUE;
		int x = 0;
		int y = 0;
		int globalX = 0;
		int globalY = 0;
		for (int i = 0; i < n; i++) {
			if (sum + a[i] > a[i]) {
				sum = sum + a[i];
				y = i;
			} else {
				sum = a[i];
				x = i;
				y = i;
			}
			if (globalMaxSum < sum) {
				globalMaxSum = sum;
				globalX = x;
				globalY = y;
			}
		}
		System.out.println("x:" + globalX + "y:" + globalY);
		return globalMaxSum;
	}

	public static int minimumSubArraySum(int[] a) {
		int n = a.length;
		int sum = 0;
		int globalMinSum = Integer.MAX_VALUE;
		int x = 0;
		int y = 0;
		int globalX = 0;
		int globalY = 0;
		for (int i = 0; i < n; i++) {
			if (sum + a[i] < a[i]) {
				sum = sum + a[i];
				y = i;
			} else {
				sum = a[i];
				x = i;
				y = i;
			}
			if (globalMinSum > sum) {
				globalMinSum = sum;
				globalX = x;
				globalY = y;
			}
		}
		System.out.println("x:" + globalX + "y:" + globalY);
		return globalMinSum;
	}

	static int maxSubArrSumDP(int a[]) {
		int n = a.length;
		int sum = a[0];
		int globalMax = sum;
		for (int i = 1; i < n; i++) {
			sum = sum > 0 ? sum + a[i] : a[i];
			globalMax = Math.min(globalMax, sum);
		}
		return globalMax;
	}

	static int minSubArrSumDP(int a[]) {
		int n = a.length;
		int sum = a[0];
		int globalMin = sum;
		for (int i = 1; i < n; i++) {
			sum = sum < 0 ? sum + a[i] : a[i];
			globalMin = Math.min(globalMin, sum);
		}
		return globalMin;
	}

	public static int minimumSubArrayAbsSum(int[] a) {
		int n = a.length;
		int sum = 0;
		int globalMinSum = Integer.MAX_VALUE;
		int x = 0;
		int y = 0;
		int globalX = 0;
		int globalY = 0;
		for (int i = 0; i < n; i++) {
			if (Math.abs(sum + a[i]) < Math.abs(a[i])) {
				sum = sum + a[i];
				y = i;
			} else {
				sum = a[i];
				x = i;
				y = i;
			}
			if (globalMinSum > Math.abs(sum)) {
				globalMinSum = Math.abs(sum);
				globalX = x;
				globalY = y;
			}
		}
		System.out.println("x:" + globalX + "y:" + globalY);
		return globalMinSum;
	}

	public static int maximunSubArrayAbsoluteSum(int[] a) {
		int n = a.length;
		int sum = 0;
		int globalMaxSum = Integer.MIN_VALUE;
		int x = 0;
		int y = 0;
		int globalX = 0;
		int globalY = 0;
		for (int i = 0; i < n; i++) {
			if (Math.abs(sum + a[i]) > Math.abs(a[i])) {
				sum = sum + a[i];
				y = i;
			} else {
				sum = a[i];
				x = i;
				y = i;
			}
			if (globalMaxSum < Math.abs(sum)) {
				globalMaxSum = Math.abs(sum);
				globalX = x;
				globalY = y;
			}
		}
		System.out.println("x:" + globalX + "y:" + globalY);
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
		int arr[] = { -7, 25, -29, 17, -2, 15 };
		int[] a1 = { -1, 1, -10, 2, 7, -19, 6, -5, 50, -10 };
		int[] a2 = { -23, 10, 1, 7, 4, -30, 50, -30 };
		int[] a = { -20, 10, 1, 2, 7 };
		System.out.println(maximunSubArraySum(a));
		System.out.println(maximunSubArraySumKadane(a));
		System.out.println(maxSubArrSumDP(a));
		System.out.println(minimumSubArraySum(a));
		System.out.println(minimumSubArraySumKadane(a));
		System.out.println(minSubArrSumDP(a));

	}
}

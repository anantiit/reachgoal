package com.csfundamentals.arrays;

// Java program to find number of subarrays
//https://www.geeksforgeeks.org/number-subarrays-sum-exactly-equal-k/

// with sum exactly equal to k.
import java.util.HashMap;

public class SubArraySumEqualsToK {

	public static int subarraySum(int[] nums, int k) {
		int count = 0, sum = 0;
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, 1);
		for (int i = 0; i < nums.length; i++) {
			sum += nums[i];
			if (map.containsKey(sum - k))
				count += map.get(sum - k);
			map.put(sum, map.getOrDefault(sum, 0) + 1);
		}
		return count;
	}

	// Function to find number of subarrays
	// with sum exactly equal to k.
	static int findSubarraySum(int arr[], int n, int sum) {
		// HashMap to store number of subarrays
		// starting from index zero having
		// particular value of sum.
		HashMap<Integer, Integer> prevSum = new HashMap<>();

		int res = 0;

		// Sum of elements so far.
		int currsum = 0;

		for (int i = 0; i < n; i++) {

			// Add current element to sum so far.
			currsum += arr[i];

			// If currsum is equal to desired sum,
			// then a new subarray is found. So
			// increase count of subarrays.
			if (currsum == sum)
				res++;

			// currsum exceeds given sum by currsum
			// - sum. Find number of subarrays having
			// this sum and exclude those subarrays
			// from currsum by increasing count by
			// same amount.
			if (prevSum.containsKey(currsum - sum))
				res += prevSum.get(currsum - sum);

			// Add currsum value to count of
			// different values of sum.
			Integer count = prevSum.get(currsum);
			if (count == null)
				prevSum.put(currsum, 1);
			else
				prevSum.put(currsum, count + 1);

			System.out.println(prevSum);
		}

		return res;
	}

	public static void main(String[] args) {

		int arr[] = { 10, 2, -2, -20, 10 };
		int sum = 0;
		int n = arr.length;
		System.out.println(findSubarraySum(arr, n, sum));
		System.out.println(subarraySum(arr, sum));
	}
}

// This code is contributed by Rituraj Jain

package com.csfundamentals.heap;

import java.math.BigInteger;
import java.util.PriorityQueue;

/*
https://leetcode.com/problems/find-the-kth-largest-integer-in-the-array/
Input: nums = ["3","6","7","10"], k = 4
Output: "3"
Explanation:
The numbers in nums sorted in non-decreasing order are ["3","6","7","10"].
The 4th largest integer in nums is "3".

imp: 1 <= nums[i].length <= 100

Integer Long, can not hold this much value
 */

public class KLargestIntegerBigNum {
	public static void main(String args[]) {
		String[] nums = new String[] { "3343434343434343434343434343", "53", "34343", "1" };
		String result = kthLargestNumber(nums, 3);
		System.out.println(result);
	}

	public static String kthLargestNumber(String[] nums, int k) {
		PriorityQueue<BigInteger> minPriorityQueue = new PriorityQueue<BigInteger>();
		BigInteger bigNum;
		String strNum;
		for (int i = 0; i < nums.length; i++) {
			strNum = nums[i];
			bigNum = new BigInteger(strNum);
			if (minPriorityQueue.size() < k) {
				minPriorityQueue.add(bigNum);
			} else if (bigNum.compareTo(minPriorityQueue.peek()) > 0) {
				minPriorityQueue.poll();
				minPriorityQueue.add(bigNum);
			}
		}
		return minPriorityQueue.peek().toString();
	}
}

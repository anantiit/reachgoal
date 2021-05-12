package com.csfundamentals.arrays.problems;

import java.util.Arrays;

public class ArrayProblemSolving {
	/*
	 * Given a sorted array nums, remove the duplicates in-place such that
	 * duplicates appeared at most twice and return the new length. [1,2,2,2,3,4] -
	 * [1,2,2,3,4]
	 * 
	 * [0, 0, 1, 1, 2, 3, 3, 3, 3]
	 */
	public static void main(String[] args) {
		int[] arr = { 0, 0, 1, 1, 2, 3, 3, 3, 3 };
		replaceDuplicatesInArray(arr);
	}

	public static int replaceDuplicatesInArray(int[] arr) {
		int counter = 1;
		int windowStart = 0;
		for (int i = 0; i < arr.length; i++) {
			while (i < arr.length - 1 && arr[i] == arr[i + 1]) {
				counter++;
				i++;
			}
			System.out.println("windowStart:" + windowStart + " i:" + i + "counter :" + counter);
			windowStart = windowStart + counter - 1;
			if (counter > 2 && i < arr.length - 1) {
				arr[windowStart] = arr[i + 1];
			} else {
				arr[windowStart] = arr[i];
				windowStart++;
			}
			counter = 1;
		}
		System.out.println(Arrays.toString(arr));
		return windowStart + 1;
	}
}

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
	public static int DUPLICATE_COUNT_ALLOWED = 3;

	public static void main(String[] args) {
		int[] arr = { 0, 0, 0, 0, 1, 1, 2, 3, 3, 3, 3, 4 };
		replaceDuplicatesInArray(arr);
	}

	public static int replaceDuplicatesInArray(int[] arr) {
		int counter = 1;
		int indexAfterRemovingDuplicates = 0;
		for (int i = 0; i < arr.length; i++, indexAfterRemovingDuplicates++) {
			// window size more if there are duplicate entries
			while (i < arr.length - 1 && arr[i] == arr[i + 1]) {
				counter++;
				// place the entries till the allowed duplicate count
				if (counter <= DUPLICATE_COUNT_ALLOWED) {
					arr[indexAfterRemovingDuplicates] = arr[i];
					indexAfterRemovingDuplicates++;
				}
				i++;
			}
			// window size 1
			arr[indexAfterRemovingDuplicates] = arr[i];
			System.out.println(
					"indexAfterRemovingDuplicates:" + indexAfterRemovingDuplicates + " i:" + i + "counter :" + counter);
			counter = 1;
		}
		System.out.println(Arrays.toString(arr));
		return indexAfterRemovingDuplicates + 1;
	}
}

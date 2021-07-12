package com.algo.divideandconquer;

import java.util.Arrays;

/**
 * 
 * @author anant
 *
 *         Count Inversions in an array | Set 1 (Using Merge Sort) Difficulty
 *         Level : Hard Last Updated : 22 Mar, 2021
 * 
 *         Inversion Count for an array indicates – how far (or close) the array
 *         is from being sorted. If the array is already sorted, then the
 *         inversion count is 0, but if the array is sorted in the reverse
 *         order, the inversion count is the maximum. Formally speaking, two
 *         elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j
 */
public class CountInversionsInArray {

	public static int countInversions(int[] a, int low, int high) {
		int mid = (low + high) / 2;
		if (low >= high) {
			return 0;
		}
		int count = countInversions(a, low, mid);
		count += countInversions(a, mid + 1, high);
		// merge step
		int i = 0;
		int j = 0;
		int k = low;
		// Left subarray
		int[] left = Arrays.copyOfRange(a, low, mid + 1);

		// Right subarray
		int[] right = Arrays.copyOfRange(a, mid + 1, high + 1);
		while (i < left.length && j < right.length) {
			if (left[i] <= right[j]) {
				a[k++] = left[i++];
			} else {
				a[k++] = right[j++];
				count += mid + 1 - (low + i);
			}
		}
		while (i < left.length)
			a[k++] = left[i++];
		while (j < right.length)
			a[k++] = right[j++];
		return count;
	}

	public static void main(String args[]) {
		int[] arr = { 1, 20, 6, 4, 5 };

		System.out.println(countInversions(arr, 0, arr.length - 1));
	}
}

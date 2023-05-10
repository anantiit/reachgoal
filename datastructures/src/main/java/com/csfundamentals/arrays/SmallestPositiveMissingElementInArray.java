package com.csfundamentals.arrays;

import java.util.HashSet;
import java.util.Set;

/*
 * https://www.geeksforgeeks.org/find-the-smallest-positive-number-missing-from-an-unsorted-array/
 */
public class SmallestPositiveMissingElementInArray {

	public static int smallestPositiveMissingElementInArray(int a[]) {
		int positiveArrEnd = partition(a, 0, a.length);
		makeNegativeBasedOnIndex(a, positiveArrEnd);
		return findFirstNonNegativeIndex(a, positiveArrEnd);
	}

	private static int findFirstNonNegativeIndex(int[] a, int size) {
		for (int i = 0; i < size; i++) {
			if (a[i] > 0) {
				return i + 1;
			}
		}
		return size + 1;
	}

	private static void makeNegativeBasedOnIndex(int[] a, int positiveArrEndIndex) {
		for (int i = 0; i < positiveArrEndIndex; i++) {
			if (a[i] <= positiveArrEndIndex) {
				int index = a[i] > 0 ? a[i] : -a[i];
				if (a[index - 1] > 0)
					a[index - 1] = -a[index - 1];
			}
		}
	}

	public static int partition(int a[], int low, int high) {
		int i = low - 1;
		for (int j = 0; j < high; j++) {
			if (a[j] >= 0) {
				int temp = a[j];
				i++;
				a[j] = a[i];
				a[i] = temp;
			}
		}
		return i + 1;
	}

	public static void main(String args[]) {
		int A[] = { 3, 1, -6, 4, -1, 2 };
		// System.out.println(smallestPositiveMissingElementInArray(A));
		missingPositiveUsingCycleSort(A);
	}

	// Simple way with O(n) extra space
	public static int smallestMissingPositive(int[] a) {
		Set<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < a.length; i++) {
			set.add(a[i]);
		}
		int i = 1;
		for (i = 1; i < a.length; i++) {
			if (!set.contains(i)) {
				return i;
			}
		}
		return i;
	}

	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static int missingPositiveUsingCycleSort(int[] arr) {
		int n = arr.length;
		int i = 0;
		while (i < n) {
			int correctPos = arr[i] - 1;
			if (correctPos >= 0 && correctPos < n && arr[correctPos] != arr[i]) {
				swap(arr, i, correctPos);
			} else {
				i++;
			}
		}
		int j = 0;
		while (arr[j] == j + 1) {
			j++;
		}
		System.out.println("First positive missing num : " + (j + 1));
		return j + 1;
	}

}

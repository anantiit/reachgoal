package com.algo.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Given 1toN integers in an unsorted array we need to sort it.
 */
public class CyclicSort {
	public static void cyclesort(int[] a, boolean indexed0) {
		int n = a.length;
		int i = 0;
		while (i < n) {
			int correct = a[i] - 1;
			if (indexed0)
				correct = a[i]; // This is the correct postion of the element at i position
			if (a[i] <= n && a[i] != a[correct]) {// Why a[i] <= n this condition is needed? if a[i] is not in range we
				swap(a, i, correct);
			} else {
				i++;
			}
		}
//		i = 0;
//		while (i < n) {
//			if (a[i] != i + 1) {
//				System.out.println("Array is not sorted: " + (i + 1));
//			}
//			i++;
//		}
	}

	/*
	 * https://leetcode.com/problems/missing-number/ Given an array nums containing
	 * n distinct numbers in the range [0, n], return the only number in the range
	 * that is missing from the array.
	 */
	public static int missingNumber(int[] a) {
		int n = a.length;
		int i = 0;
		while (i < n) {
			int correct = a[i]; // This is the correct postion of the element at i position
			if (a[i] < n && a[i] != a[correct]) {// Why a[i] <= n this condition is needed? if a[i] is not in range we
				// ignore and move if its in range we a[i] to the correct postion.
				swap(a, i, correct);
			} else {
				i++;
			}
		}
		i = 0;
		while (i < n) {
			if (a[i] != i) {
				System.out.println("Missing Element is" + i);
				return i;
			}
			i++;
		}
		System.out.println("Missing Element is" + i);
		return i;
	}

	/*
	 * https://leetcode.com/problems/find-all-numbers-disappeared-in-an-array/ Given
	 * an array nums of n integers where nums[i] is in the range [1, n], return an
	 * array of all the integers in the range [1, n] that do not appear in nums.
	 */
	public static List<Integer> findDisappearedNumbers(int[] a) {
		List<Integer> result = new ArrayList<Integer>();
		int n = a.length;
		int i = 0;
		while (i < n) {
			int correct = a[i] - 1; // This is the correct postion of the element at i position
			if (a[i] <= n && a[i] != a[correct]) {// Why a[i] <= n this condition is needed? if a[i] is not in range we
				// ignore and move if its in range we a[i] to the correct postion.
				swap(a, i, correct);
			} else {
				i++;
			}
		}
		i = 0;

		while (i < n) {
			if (a[i] != i + 1) {
				result.add(i + 1);
			}
			i++;
		}
		return result;
	}

	public static void swap(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	public static void findRepeatingNum(int[] a) {
		int n = a.length;
		int i = 0;
		while (i < n) {
			int correct = a[i] - 1; // This is the correct postion of the element at i position
			if (a[i] <= n && a[i] != a[correct]) {// Why a[i] <= n this condition is needed? if a[i] is not in range we
				// ignore and move if its in range we a[i] to the correct postion.
				swap(a, i, correct);
			} else {
				i++;
			}
		}
		i = 0;
		while (i < n) {
			if (a[i] != i + 1) {
				System.out.println("Duplicate: " + (a[i]));
			}
			i++;
		}
	}

	public static void findFirstPositiveMissingNum(int[] a) {
		int n = a.length;
		int i = 0;
		while (i < n) {
			int correct = a[i] - 1; // This is the correct postion of the element at i position
			if (a[i] <= n && correct < n && correct >= 0 && a[i] != a[correct]) {// Why a[i] <= n this condition is
																					// needed? if a[i] is not in range
																					// we
				// ignore and move if its in range we a[i] to the correct postion.
				swap(a, i, correct);
			} else {
				i++;
			}
		}
		System.out.println(Arrays.toString(a));
		i = 0;
		while (i < n) {
			if (a[i] != i + 1) {
				System.out.println("First missing positive: " + (i + 1));
				break;
			}
			i++;
		}
	}

	public static void cycleSort(int[] arr) {
		int n = arr.length;

		for (int cycleStart = 0; cycleStart < n - 1; cycleStart++) {
			int currentValue = arr[cycleStart];

			// Find the correct position for the current element
			int pos = cycleStart;
			for (int i = cycleStart + 1; i < n; i++) {
				if (arr[i] < currentValue)
					pos++;
			}

			// If the element is already in the correct position, continue to the next
			// iteration
			if (pos == cycleStart)
				continue;

			// Skip any duplicate elements
			while (currentValue == arr[pos])
				pos++;

			// Swap the current element with the element at its correct position
			if (pos != cycleStart) {
				int temp = arr[pos];
				arr[pos] = currentValue;
				currentValue = temp;
			}

			// Move other elements that belong to the same cycle
			while (pos != cycleStart) {
				pos = cycleStart;
				for (int i = cycleStart + 1; i < n; i++) {
					if (arr[i] < currentValue)
						pos++;
				}

				// Skip any duplicate elements
				while (currentValue == arr[pos])
					pos++;

				// Swap the current element with the element at its correct position
				if (currentValue != arr[pos]) {
					int temp = arr[pos];
					arr[pos] = currentValue;
					currentValue = temp;
				}
			}
		}
	}

//	    public static void main(String[] args) {
//	        int[] arr = {5, 2, 8, 5, 9, 1, 3, 5};
//	        
//	        System.out.println("Before sorting:");
//	        for (int num : arr) {
//	            System.out.print(num + " ");
//	        }
//	        
//	        cycleSort(arr);
//	        
//	        System.out.println("\nAfter sorting:");
//	        for (int num : arr) {
//	            System.out.print(num + " ");
//	        }
//	    }

	public static void main(String[] args) {
		int[] a = { 3, 5, 2, 1, 4 };
		int[] b1 = { 2, 0, 2, 1, 1, 0 };
		// cyclesort(b1, true);
		// cycleSort(b1);
		System.out.println(Arrays.toString(b1));
		System.out.println(Arrays.toString(a));
		int[] b = { 2, 3, 1 };
		firstMissingPositive(b);
		int[] nums = { 4, 3, 2, 7, 8, 2, 3, 1 };
		System.out.println(findDisappearedNumbers(nums));
		int[] c = { 4, 3, 5, 7, 2, 3, 1, 6 };
		findRepeatingNum(c);
		int[] d = { 7, 8, 9, 11, 12 };
		int[] e = { 3, 4, -1, 1 };
		int[] e1 = { 4, 2, 1, 3 };
		findFirstPositiveMissingNum(e1);
		firstMissingPositive(e);

	}

	public static int firstMissingPositive(int[] a) {
		int n = a.length;
		int i = 0;
		while (i < n) {
			int correctIndex = a[i] - 1;
			if (correctIndex >= 0 && correctIndex < n && a[i] != a[correctIndex]) {
				swap(a, i, correctIndex);
			} else {
				i++;
			}
		}
		int j = 0;
		while (j < n && a[j] == j + 1) {
			j++;
		}
		System.out.println("First missing positive is :" + (j + 1));
		return -1;
	}
}

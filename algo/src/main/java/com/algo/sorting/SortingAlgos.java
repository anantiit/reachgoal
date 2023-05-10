package com.algo.sorting;

import java.util.Arrays;
import java.util.Random;

public class SortingAlgos {

	private static void quicksort(int[] arr, int left, int right) {
		int pivotInd = partition(arr, left, right, false);
		if (left < pivotInd - 1) {
			quicksort(arr, left, pivotInd - 1);
		}
		if (pivotInd < right) {
			quicksort(arr, pivotInd, right);
		}
	}

	private static int partition(int[] arr, int left, int right, boolean random) {
		int pivotInd = left + (right - left) / 2;
		if (random) {
			pivotInd = getRandomPivotInd(left, right);
		}
		int pivot = arr[pivotInd];
		int i = left, j = right;
		while (i <= j) {
			while (arr[i] < pivot) {
				i++;
			}
			while (arr[j] > pivot) {
				j--;
			}
			if (i <= j) {
				int temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
				i++;
				j--;
			}
		}
		if (i > right) {
			pivotInd = i - 1;
		} else
			pivotInd = i;
		return pivotInd;
	}

	private static int getRandomPivotInd(int left, int right) {
		return rand.nextInt(right - left + 1) + left;
	}

	// For every iteration we will find out bubble (min/max) that pops out
	public static void bubbleSort(int[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = i + 1; j < n - i; j++) {
				if (a[i] > a[j]) {
					swap(a, i, j);
				}
			}
		}
	}

	public static void selectionSort(int[] a) {
		int n = a.length;
		for (int i = 0; i < n; i++) {
			int min = a[i];
			int minIndex = i;
			for (int j = i + 1; j < n; j++) {
				if (min > a[j]) {
					min = a[j];
					minIndex = j;
				}
			}
			if (minIndex != i) {
				swap(a, i, minIndex);
			}
		}
	}

	// For every ith Iteration we insert a[i] at the rightplace in the left side
	// sorted array. Left side 0->i-1 is already sorted in the last i-1 iterations
	// as we inserted each element in the right place in each iteration.
	public static void insertionSort(int[] a) {
		int n = a.length;
		for (int i = 1; i < n; i++) {
			int j = i - 1;
			int k = i;
			while (j >= 0) {
				if (a[k] < a[j]) {
					swap(a, k, j);
					k = j;
				} else {
					break;
				}
				j--;
			}

		}
	}

	public static void main(String[] args) {
		int[] a = { 3, 5, 2, -1, 4, 2, 1, -1, 10, 11 };
//		bubbleSort(a);
//		System.out.println("Arr:" + Arrays.toString(a));	
//		bubbleSort(a);
//		System.out.println("Arr:" + Arrays.toString(a));
		int arr[] = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
//		int n = arr.length;
//		int median = quickSelectMedian(arr);
//		System.out.println("Median of array is: " + median);
		quicksort(a, 0, a.length - 1);
		System.out.println(Arrays.toString(a));
		// System.out.println(quickSelectMedian(a));
	}

	private static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	private static Random rand = new Random();

	public static int quickSelectMedian(int[] arr) {
		int n = arr.length;
		int mid = n / 2;
		int start = 0;
		int end = n - 1;
		int pivotValue = 0;
		int prevMidValue = 0;
		int midValue = randomquickselectmedian(arr, mid, start, end, pivotValue);
		if (n % 2 == 0) {
			prevMidValue = randomquickselectmedian(arr, mid - 1, start, end, pivotValue);
			return (prevMidValue + midValue) / 2;
		}
		return midValue;
	}

	private static int randomquickselectmedian(int[] arr, int mid, int start, int end, int pivotValue) {
		while (start <= end) {

			int pivotIndex = partition(arr, start, end, true);
			pivotValue = arr[pivotIndex];
			System.out.println(" pivotInd:" + pivotIndex + " pivot:" + pivotValue + " start:" + start + " end:" + end
					+ Arrays.toString(arr));

			if (pivotIndex == mid) {
				return pivotValue;
			} else if (pivotIndex < mid) {
				start = pivotIndex - 1;
			} else {
				end = pivotIndex;
			}

		}
		return pivotValue;
	}
}
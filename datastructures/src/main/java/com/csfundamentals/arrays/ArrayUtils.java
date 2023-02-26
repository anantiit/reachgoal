package com.csfundamentals.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class ArrayUtils {
	public static void main(String[] args) {
		ArrayUtils au = new ArrayUtils();
//		int[] nums = { 5, 1, 3 };
//		System.out.println(search(nums, 0));
//		int[] arr1 = { 1, 9, 13, 19 };
//		int[] arr2 = { 2, 10, 15, 18 };
//
//		mergeSortedArrays(arr1, arr2);
//		System.out.println(Arrays.toString(arr1));
//		System.out.println(Arrays.toString(arr2));
//		int[] arr = { 11, 13, 21, 2, 5, 4, 3 };
//		// int arr1[] = { 8, 5, 10, 7, 9, 4, 15, 12, 90, 13 };
//		int k = 4;
//		System.out.println(Arrays.toString(maximumOfAllKSizeSubArrays(arr1, k)));
//		// { 5, 3, 2, 4, 1 };
//		nextGreaterElement(arr);
		// int[] arr = {0,1,2,3,4,5,6,7};
		int arr[] = { 1, 2, 3, 4, 5, 6, 7 };
		// Output: 3 4 5 6 7 1 2
		System.out.println(Arrays.toString(arr));
		int rotateLength = 3;
		// rotate(arr, rotateLength);
		au.leftRotate(arr, rotateLength, arr.length);
		// System.out.println(Arrays.toString(arr));
		// System.out.println(longestCommonSubsequence("ABCBDAB", "BDCABA"));
//		ArrayList<ArrayList<Integer>> mat = new ArrayList<ArrayList<Integer>>();
//		ArrayList<Integer> row1 = new ArrayList<Integer>();
//		row1.add(1);
//		// row1.add(2);
//		ArrayList<Integer> row2 = new ArrayList<Integer>();
//		row2.add(3);
//		row2.add(4);
//		mat.add(row1);
//		// mat.add(row2);
//		System.out.println(mat);
//		rotateMatrix(mat);
//		System.out.println(mat);
	}

	void leftRotate(int arr[], int d, int n) {
		/* To handle if d >= n */
		d = d % n;
		int i, j, k, temp;
		int g_c_d = gcd(d, n);
		for (i = 0; i < g_c_d; i++) {
			/* move i-th values of blocks */

			temp = arr[i];
			j = i;
			while (true) {
				k = j + d;
				if (k >= n)
					k = k - n;
				if (k == i)
					break;
				arr[j] = arr[k];
				System.out.println("in while " + Arrays.toString(arr) + j + "," + k);
				j = k;
			}
			arr[j] = temp;
			System.out.println("in for:" + Arrays.toString(arr));
		}
	}

	/* UTILITY FUNCTIONS */

	/* function to print an array */
	void printArray(int arr[], int size) {
		int i;
		for (i = 0; i < size; i++)
			System.out.print(arr[i] + " ");
	}

	/* Function to get gcd of a and b */
	int gcd(int a, int b) {
		if (b == 0)
			return a;
		else
			return gcd(b, a % b);
	}

//Worst case it is O(n*m) and no extra space being used.
	public static void mergeSortedArrays(int[] arr1, int[] arr2) {
		int i = 0;
		while (arr1[arr1.length - 1] > arr2[0]) {
			if (arr1[i] > arr2[0]) {
				swap(arr1, arr2, i, 0);
				Arrays.sort(arr2);// Note that this will take O(n) max as only the first element is not at the
									// right place and the remaining arr is already sorted
			}
			i++;
		}

	}

	private static void swap(int[] arr1, int[] arr2, int i, int j) {
		int temp = arr1[i];
		arr1[i] = arr2[j];
		arr2[j] = temp;
	}

	private static void nextGreaterElement(int a[]) {
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < a.length; i++) {
			while (!stack.isEmpty() && stack.peek() < a[i]) {
				System.out.println(stack.pop() + "," + a[i]);
			}
			stack.push(a[i]);
		}
		while (!stack.isEmpty()) {
			System.out.println(stack.pop() + ", No next greater element");
		}
	}

	// rotate matrix 90 degrees in clockwise.
	public static void rotateMatrix(ArrayList<ArrayList<Integer>> a) {
		int n = a.size();
		ArrayList<ArrayList<Integer>> b = new ArrayList<ArrayList<Integer>>(n);
		for (int j = 0; j < n; j++) {
			ArrayList<Integer> modifiedRowi = new ArrayList<Integer>(n);
			for (int i = n - 1; i >= 0; i--) {
				ArrayList<Integer> rowi = a.get(i);
				modifiedRowi.add(rowi.get(j));
			}
			b.add(j, modifiedRowi);
		}
		for (int j = 0; j < n; j++) {
			a.set(j, b.get(j));
		}
	}

	static int[] maximumOfAllKSizeSubArrays(int[] a, int k) {
		int kSizeSubArraysCount = a.length - k + 1;
		int[] maxArr = new int[kSizeSubArraysCount];
		int maxIndex = -1;
		int max = Integer.MIN_VALUE;
		int startIndex = 0;
		int endIndex = k - 1;
		for (int i = startIndex; i <= endIndex; i++) {
			if (a[i] > max) {
				max = a[i];
				maxIndex = i;
			}
		}
		maxArr[startIndex] = max;
		startIndex++;
		endIndex++;
		for (; (endIndex < a.length) && (startIndex < kSizeSubArraysCount); endIndex++, startIndex++) {
			if (a[endIndex] > max) {
				max = a[endIndex];
				maxIndex = endIndex;
			} else if (startIndex > maxIndex) {
				for (int i = startIndex; i <= endIndex; i++) {
					if (a[i] > max) {
						max = a[i];
						maxIndex = i;
					}
				}
			}
			maxArr[startIndex] = max;
		}
		return maxArr;
	}

	public static int search(int[] nums, int target) {
		if (nums[0] == target) {
			return 0;
		} else if (nums.length == 1) {
			return -1;
		}
		if (nums.length == 2) {
			return (nums[1] == target) ? 1 : -1;
		}
		int pivot = binaryPivotSearch(nums, 0, nums.length - 1);
		if (nums[pivot] == target) {
			return pivot;
		}
		if (nums[0] > target) {
			return binarySearch(nums, 0, pivot, target);
		} else {
			return binarySearch(nums, pivot, nums.length - 1, target);
		}

	}

	public static int binaryPivotSearch(int[] a, int l, int h) {
		if (a[h] >= a[l]) {
			return l;
		}
		if (h - l == 1) {
			return h;
		}

		if (l < h) {

			int mid = l + (h - l) / 2;
			// System.out.println("mid"+mid+"l"+l+"h"+h);
			// System.out.println("mid"+a[mid]+"l"+a[mid-1]+"h"+a[mid+1]);
			if ((a[mid - 1] < a[mid]) && (a[mid] > a[mid + 1])) {
				return mid;
			}
			if (a[l] < a[mid]) {
				return binaryPivotSearch(a, l, mid - 1);
			} else {
				return binaryPivotSearch(a, mid + 1, h);
			}
		}
		return l;
	}

	public static int binarySearch(int[] a, int l, int h, int target) {
		if (h - l == 0 || h - l == 1) {
			if (a[h] == target) {
				return h;
			} else if (a[l] == target) {
				return l;
			}
			return -1;
		}
		if (l < h) {
			int mid = l + (h - l) / 2;
			if (a[mid] == target) {
				return mid;
			}
			if (a[mid] > target) {
				return binarySearch(a, l, mid - 1, target);

			} else {
				return binarySearch(a, mid + 1, h, target);
			}
		}
		return -1;
	}

	public static void rearrangeArray(int[] arr) {
		// Assuming that array contains the numbers which are 0 to n-1 and each only
		// once
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i + 1];
		}
	}
}

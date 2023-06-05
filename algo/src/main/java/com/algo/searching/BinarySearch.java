package com.algo.searching;

public class BinarySearch {

	public static void main(String args[]) {
//		System.out.println(mySqrt(1000));
//		int[] a = { -1, 2, 3, 4, 10, 11 };
//		int[] aRotated = { 11, 10, 4, -1, 2, 3 };
//		int[] a1 = { 3, 4, 3, 2, 1 };
//		int[] a2 = { 2, 9, 12, 16, 25 };
//		int key = -10;
////		binarySearch(a, key, 0, a.length - 1, false);
////		System.out.println("Arr:" + Arrays.toString(a));
////		System.out.println(findPeakElement(a1));
//		System.out.println(findCeiling(a2, 1));
//		System.out.println(findCeiling(a2, 25));
//		System.out.println(findCeiling(a2, 11));
//		int[] b = { 1, 2, 3, 5 };
//		int[] aRotated1 = { 1, 0, 1, 1, 1 };
//		int[] aRotated2 = { 4, 5, 1, 2, 3 };
//		int nums[] = { 7, 2, 5, 10, 8 };
//		int m = 3;
//		System.out.println(findInMountainArray(b, 3));
//		System.out.println(findInMountainArray(b, -1));
//		System.out.println(findInMountainArray(b, 1));
//		System.out.println(findInRotatedArray(aRotated1, 0));
//		System.out.println(splitArrayMinLargestSums(nums, m));
		System.out.println(findFloorCubeRoot(1000000000l));
	}

	public static long findFloorCubeRoot(long n) {
		long low = 0;
		long high = n;
		long mid = 0;
		while (low <= high) {
			// why cant we do this (low+high)/2 , is because low+high can some times cross
			// the limit of its datatype
			mid = low + (high - low) / 2;
			long midCheck = n / (mid * mid);
			if (mid == midCheck) {
				return mid;
			}
			if (mid < midCheck && (mid + 1) > n / ((mid + 1) * (mid + 1))) {
				return mid;
			}
			if (mid > midCheck) {
				high = mid - 1;
			} else {
				low = mid + 1;
			}
		}
		return mid;
	}

	public static int findCeiling(int[] a, int key, int left, int right) {
		int mid = (left + right) / 2;
		if (mid > 0 && a[mid - 1] <= key && a[mid] >= key) {
			System.out.println("Ceiling: " + mid);
			return mid;
		}
		if (left == right) {
			return -1;
		}
		int ceilingInd = -1;
		if (a[mid] > key) {
			ceilingInd = findCeiling(a, key, left, mid);
		} else if (ceilingInd == -1 && mid < a.length - 1) {
			ceilingInd = findCeiling(a, key, mid + 1, right);
		}
		return ceilingInd;

	}

	// Iterative is always better if it is easy to implement. As it is easy to
	// debug, and check for corner cases.
	public static int findCeiling(int[] a, int key) {
		if (key < a[0]) {
			return 0;
		} else if (key > a[a.length - 1]) {
			return -1;
		}
		int left = 1;
		int right = a.length - 1;
		while (left <= right) {
			int mid = (left + right) / 2;
			if (mid > 0 && a[mid - 1] <= key && a[mid] >= key) {
				System.out.println("Ceiling: " + mid);
				return mid;
			}
			if (a[mid] > key) {
				right = mid;
			} else if (mid < a.length - 1) {
				left = mid + 1;
			}
		}
		return -1;

	}

	public static int findPeakElement(int[] a, int left, int right) {
		int mid = (left + right) / 2;
		if (mid > 0 && (a[mid - 1] < a[mid]) && mid < a.length - 1 && (a[mid] > a[mid + 1])) {
			System.out.println("Peak found at index: " + mid);
			return mid;
		}
		int peakIndex = -1;
		if (left < mid) {
			peakIndex = findPeakElement(a, left, mid);
		}
		if (peakIndex == -1 && right > mid) {
			peakIndex = findPeakElement(a, mid + 1, right);
		}
		return peakIndex;
	}

	// https://leetcode.com/problems/find-in-mountain-array/

	public static int findInMountainArray(int[] a, int key) {
		int peakInd = findPeakElementInMountainArray(a, 0, a.length - 1);
		if (peakInd == -1) {
			return binarySearch(a, key, 0, a.length - 1, false);
		}
		if (a[peakInd] == key) {
			return peakInd;
		}
		int resultIndex = -1;
		resultIndex = binarySearch(a, key, 0, peakInd, false);
		if (resultIndex == -1) {
			resultIndex = binarySearch(a, key, peakInd, a.length - 1, true);
		}
		return resultIndex;

	}

	private static int findPeakElementInMountainArray(int[] a, int left, int right) {
		if (left > right) {
			return -1;
		}
		int mid = (left + right) / 2;
		if (mid > 0 && (a[mid - 1] < a[mid]) && mid < a.length - 1 && (a[mid] > a[mid + 1])) {
			System.out.println("Peak found at index: " + mid);
			return mid;
		}
		int peakIndex = -1;
		if (left == right) {
			return -1;
		}
		if (a[mid - 1] > a[mid]) {
			peakIndex = findPeakElementInMountainArray(a, left, mid);
		} else {
			peakIndex = findPeakElementInMountainArray(a, mid + 1, right);
		}
		return peakIndex;
	}

	public static int findPeakElement(int[] a) {
		if (a.length == 1) {
			return 0;
		}
		int ind = findPeakElement(a, 0, a.length - 1);
		if (ind == -1) {
			if (a[0] > a[1]) {
				return 0;
			} else {
				return a.length - 1;
			}
		}
		return ind;
	}

	public static int binarySearch(int[] a, int key, int left, int right, boolean reverse) {
		if (left > right) {
			return -1;
		}
		int mid = (left + right) / 2;
		if (a[mid] == key) {
			System.out.println("Found at index: " + mid);
			return mid;
		}
		if (left == right) {
			return -1;
		}
		if (!reverse) {
			if (a[mid] > key) {
				return binarySearch(a, key, left, mid - 1, reverse);
			} else {
				return binarySearch(a, key, mid + 1, right, reverse);
			}
		} else {
			if (a[mid] < key) {
				return binarySearch(a, key, left, mid - 1, reverse);
			} else {
				return binarySearch(a, key, mid + 1, right, reverse);
			}
		}

	}

	public static int findInRotatedArray(int[] a, int key) {

		int pivot = findPivotIterative(a);
		if (pivot == -1) {
			return binarySearch(a, key, 0, a.length - 1, false);
		}
		if (a[pivot] == key) {
			return pivot;
		}
		int resultIndex = -1;
		resultIndex = binarySearch(a, key, 0, pivot - 1, false);
		if (resultIndex == -1) {
			resultIndex = binarySearch(a, key, pivot + 1, a.length - 1, false);
		}
		return resultIndex;

	}

	private static int findPivotInRotatedArray(int[] a, int left, int right) {
		if (left > right)
			return -1;
		int mid = (left + right) / 2;
		if (mid < a.length - 1 && (a[mid] > a[mid + 1])) {
			System.out.println("pivot found at index: " + mid);
			return mid;
		}
		if (mid > 0 && (a[mid - 1] > a[mid])) {
			System.out.println("pivot found at index: " + (mid - 1));
			return mid - 1;
		}
		if (a[left] < a[mid]) {
			return findPivotInRotatedArray(a, mid + 1, right);
		}

		return findPivotInRotatedArray(a, left, mid - 1);

	}

	private static int findPivotIterative(int[] nums) {
		int start = 0, end = nums.length - 1;
		if (start == end) {
			return start;
		}
		// In case of start is pivot
		if (nums[start] > nums[start + 1])
			return start;

		while (start < end) {

			int mid = (start + end) / 2;
			if (mid > 0 && nums[mid] > nums[mid - 1])
				return mid;
			if (nums[start] <= nums[mid] && nums[mid] > nums[end])
				start = mid + 1;
			else
				end = mid - 1;
		}
		return start;
	}

	/*
	 * Given an integer array nums and an integer k, split nums into k non-empty
	 * subarrays such that the largest sum of any subarray is minimized.
	 * 
	 * Return the minimized largest sum of the split.
	 * 
	 * A subarray is a contiguous part of the array.
	 * 
	 * 
	 * 
	 * Example 1:
	 * 
	 * Input: nums = [7,2,5,10,8], k = 2 Output: 18 Explanation: There are four ways
	 * to split nums into two subarrays. The best way is to split it into [7,2,5]
	 * and [10,8], where the largest sum among the two subarrays is only 18.
	 */
	public static int splitArrayMinLargestSums(int[] a, int m) {
		int start = a[0]; // minSumPossible
		int end = a[0]; // maxSumPossible
		for (int i = 1; i < a.length; i++) {
			if (start < a[i]) {
				start = a[i];
			}
			end = end + a[i];
		}
		while (start < end) {
			int tempSum = 0;
			int numSubarays = 1;
			int mid = start + (end - start) / 2;
			for (int i = 0; i < a.length; i++) {
				if (tempSum + a[i] > mid) {
					numSubarays++; // Start new subarray
					tempSum = a[i];
				} else {
					tempSum += a[i];
				}
			}

			if (numSubarays > m) {
				start = mid + 1;
			} else {
				end = mid;
			}
			System.out.println("numSubarays:" + numSubarays + " start:" + start + " end:" + end);
		}
		return start; // Here start == end
	}

	public int find(int[] nums1, int l1, int r1, int[] nums2, int l2, int r2, int targetIndex) {
		if (r1 < l1) {
			return nums2[l2 + targetIndex];
		}
		if (r2 < l2) {
			return nums1[l1 + targetIndex];
		}
		int mid1 = (r1 + l1) / 2;
		int mid2 = (r2 + l2) / 2;
		if (nums1[mid1] > nums2[mid2]) {
			if (targetIndex >= ((mid2 - l2) + (mid1 - l1) + 1)) {
				return find(nums1, l1, r1, nums2, mid2 + 1, r2, targetIndex - (mid2 - l2 + 1));
			} else {
				return find(nums1, l1, mid1 - 1, nums2, l2, r2, targetIndex);
			}
		} else {
			if (targetIndex >= (mid2 - l2 + mid1 - l1 + 1)) {
				return find(nums1, mid1 + 1, r1, nums2, l2, r2, targetIndex - (mid1 - l1 + 1));
			} else {
				return find(nums1, l1, r1, nums2, l2, mid2 - 1, targetIndex);
			}
		}
	}

//Problem: Median of Two Sorted Arrays
	public double findMedianSortedArrays(int[] nums1, int[] nums2) {
		int l = nums1.length + nums2.length;
		if (l % 2 == 0) {
			int mid1 = find(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, l / 2);
			int mid2 = find(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, l / 2 - 1);
			return ((double) mid1 + (double) mid2) / 2.0;
		}
		double ans = find(nums1, 0, nums1.length - 1, nums2, 0, nums2.length - 1, l / 2);
		return ans;
	}

	public static int mySqrt(int x) {
		int low = 1, high = x, ans = 0;

		while (low <= high) {
			int mid = low + (high - low) / 2;

			if (x / mid == mid)
				return mid;
			else if (x / mid < mid)
				high = mid - 1;
			else {
				low = mid + 1;
				ans = mid;
			}
		}
		return ans;
	}

}

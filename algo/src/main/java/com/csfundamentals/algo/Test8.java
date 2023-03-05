package com.csfundamentals.algo;

import java.util.HashMap;
import java.util.Map;

/*
 * String: ABCDDEFCB
 * Ans: B
 * first character repeating in the given string
 * map: 
 * A,0
 * B,1,8
 * C,2,7
 * D,3,4
 * E,5
 * F,6
 * 
 * TreeSet{1,2,3}-> 1
 * 
 * 
 * given sorted array find the frequency of specific element.
 * 
 * 1,1,1,2,2,2,3,3,3,3,4,5    5
 * 
 * 
 */
public class Test8 {

	static int frequencyinSortedArr(int[] a, int target) {
		int index = binarySearch(a, 0, a.length - 1, target);
		if (index == -1) {
			return 0;
		} else {
			int leftCount = 0;
			int rightCount = 0;
			int leftIndex = index - 1;
			int rightIndex = index + 1;
			while (leftIndex >= 0) {
				if (a[leftIndex] == target) {
					leftCount++;
				} else {
					break;
				}
				leftIndex--;
			}
			while (rightIndex < a.length) {
				if (a[rightIndex] == target) {
					rightCount++;
				} else {
					break;
				}
				rightIndex++;
			}
			return rightCount + leftCount + 1;
		}
	}

	static int binarySearch(int[] a, int l, int h, int target) {
		if (h >= l) {
			int mid = l + (h - l) / 2;
			if (a[mid] == target) {
				return mid;
			}
			if (a[mid] < target) {
				return binarySearch(a, mid + 1, h, target);
			}
			return binarySearch(a, l, mid - 1, target);
		}
		return -1;
	}

	public static void main(String args[]) {
		String s = "ABCDDEFCB";
		int[] arr = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 5 };
		System.out.println(frequencyinSortedArr(arr, 5));
		// System.out.println(fistRepeatingCharacter(s));
	}

	public static Character fistRepeatingCharacter(String s) {
		char[] a = s.toCharArray();
		Map<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < a.length; i++) {
			Integer count = map.get(a[i]);
			if (count == null) {
				count = 0;
			}
			count++;
			map.put(a[i], count);
		}
		for (int i = 0; i < a.length; i++) {
			Integer count = map.get(a[i]);
			if (count > 1) {
				return a[i];
			}
		}
		return null;
	}
}

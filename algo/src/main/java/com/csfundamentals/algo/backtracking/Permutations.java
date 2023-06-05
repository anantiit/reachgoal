package com.csfundamentals.algo.backtracking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class Permutations {
	public static int counter = 0;

	public static void main(String[] args) {
		char[] str = { 'a', 'b', 'c', 'd' };
		Set<String> set = new HashSet<String>();
		findPermutations(str, 0, str.length - 1);
//		System.out.println(set);
//		set = new HashSet<String>();
		findPalindromePermutations(str, 0, str.length - 1, set);
		System.out.println(set);

		int[] a = { 2, 3, 1, 4 };
		nextPermutation(a);
		System.out.println(Arrays.toString(a));
	}

	public static void findPalindromePermutations(char[] str, int l, int r, Set<String> set) {
		if (l >= r) {
			if (isPalindrome(str)) {
				set.add(new String(str));
			}
			return;
		}
		for (int i = l; i <= r; i++) {
			swap(str, l, i);
			findPalindromePermutations(str, l + 1, r, set);
			swap(str, l, i);
		}
	}

	public static void findPermutations(char[] str, int l, int r) {
		if (l >= r)
			System.out.println(Arrays.toString(str) + ":" + ++counter);
		else {
			for (int i = l; i <= r; i++) {
				swap(str, l, i);
				findPermutations(str, l + 1, r);
				swap(str, l, i);
			}
		}
	}

	private static void swap(char[] str, int i, int j) {
		char temp = str[i];
		str[i] = str[j];
		str[j] = temp;
	}

	static boolean isPalindrome(char[] str) {
		for (int i = 0, j = str.length - 1; j >= i; i++, j--) {
			if (str[i] != str[j]) {
				return false;
			}
		}
		return true;
	}
	/*
	 * 
	 * Given an array A[] of N distinct integers. The task is to return the
	 * lexicographically greater permutation than the given arrangement. If no such
	 * arrangement is possible, return the array sorted in non-decreasing order.
	 * 
	 * Examples:
	 * 
	 * Input: A[] = [1, 2, 3] Output: [1, 3, 2] Explanation: The lexicographically
	 * greater permutation than A[] is [1, 3, 2]
	 * 
	 * Input: A[] = [4, 3, 2, 1]
	 * 
	 * Output: [1, 2, 3, 4]
	 * 
	 * 
	 */

	public static void nextPermutation(int[] A) {
		if (A == null || A.length <= 1)
			return;
		int i = A.length - 2;
		while (i >= 0 && A[i] >= A[i + 1])
			i--;
		if (i >= 0) {
			int j = A.length - 1;
			while (A[j] <= A[i])
				j--;
			swap(A, i, j);
		}
		reverse(A, i + 1, A.length - 1);
	}

	public static void swap(int[] A, int i, int j) {
		int tmp = A[i];
		A[i] = A[j];
		A[j] = tmp;
	}

	public static void reverse(int[] A, int i, int j) {
		while (i < j)
			swap(A, i++, j--);
	}

}

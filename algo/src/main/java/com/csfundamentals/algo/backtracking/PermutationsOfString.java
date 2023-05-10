package com.csfundamentals.algo.backtracking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class PermutationsOfString {
	public static void main(String[] args) {
		char[] str = { 'a', 'b', 'a', 'b', 'd' };
		Set<String> set = new HashSet<String>();
//		findPermutations(str, 0, str.length - 1);
//		System.out.println(set);
//		set = new HashSet<String>();
		findPalindromePermutations(str, 0, str.length - 1, set);
		System.out.println(set);
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
			System.out.println(Arrays.toString(str));
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
}

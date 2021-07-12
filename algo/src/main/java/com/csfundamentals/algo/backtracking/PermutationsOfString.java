package com.csfundamentals.algo.backtracking;

import java.util.HashSet;
import java.util.Set;

// Main class should be named 'Solution'
class PermutationsOfString {
	public static void main(String[] args) {
		System.out.println("Hello, World");
		char[] str = { 'a', 'b', 'c' };
		Set<String> set = new HashSet<String>();
		findPermutations(str, 0, set);
		System.out.println(set);
	}

	public static void findPermutations(char[] str, int j, Set<String> set) {
		if (j == str.length - 1) {
			if (isPalindrome(str)) {
				set.add(new String(str));
			}
			return;
		}
		for (int i = j + 1; i < str.length; i++) {
			char temp = str[i];
			str[i] = str[j];
			str[j] = temp;

			findPermutations(str, j + 1, set);
			temp = str[i];
			str[i] = str[j];
			str[j] = temp;
		}
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

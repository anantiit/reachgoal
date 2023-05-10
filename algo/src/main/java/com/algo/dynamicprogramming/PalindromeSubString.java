package com.algo.dynamicprogramming;

import java.util.Arrays;

public class PalindromeSubString {
	public static int longestPalindromeSubString(String a) {
		int n = a.length();
		if (a == null || a.isBlank()) {
			return 0;
		}
		int L[][] = new int[n][n];
		int max = 1;
		int maxStart = 0;
		int maxEnd = 0;
		for (int i = 0; i < n; i++) {
			L[i][i] = 1;
		}
		// Initialized as all strings with length 1 are palindromes
		int palindromeSubStringCount = n;
		for (int i = 0; i < n - 1; i++) {
			if (a.charAt(i) == a.charAt(i + 1)) {
				L[i][i + 1] = 2;
				max = 2;
				maxStart = i;
				maxEnd = i + 1;
				palindromeSubStringCount++;
				System.out.println("Palindrome count :" + palindromeSubStringCount + " max size: " + max
						+ "substring : " + a.substring(maxStart, maxEnd + 1));
			}

		}
		for (int k = 3; k <= n; k++) {
			for (int i = 0; i < n - k + 1; i++) {
				int j = i + k - 1;
				if (a.charAt(i) == a.charAt(j) && L[i + 1][j - 1] > 0) {
					L[i][j] = L[i + 1][j - 1] + 2;
					max = k;
					maxStart = i;
					maxEnd = j;
					palindromeSubStringCount++;
					System.out.println("Palindrome count :" + palindromeSubStringCount + " max size: " + max
							+ "substring : " + a.substring(maxStart, maxEnd + 1));

				}
				else {
					Math.max(L[i + 1][j], L[i][j - 1]);
				}
			}
		}
		for (int i = 0; i < n; i++) {
			System.out.println(Arrays.toString(L[i]));
		}
		System.out.println("Palindrome : max size: " + max + "substring : " + a.substring(maxStart, maxEnd + 1));
		return max;
	}

	public static void main(String args[]) {
		System.out.println(longestPalindromeSubString("abaabaa"));
	}

}

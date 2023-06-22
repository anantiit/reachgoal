package com.algo.dynamicprogramming;

public class PalindromeSubString {
	public static String longestPalindromeSubString(String a) {
		int n = a.length();
		if (a == null || a.isBlank()) {
			return a;
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
//				System.out.println("Palindrome count :" + palindromeSubStringCount + " max size: " + max
//						+ "substring : " + a.substring(maxStart, maxEnd + 1));
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
//					System.out.println("Palindrome count :" + palindromeSubStringCount + " max size: " + max
//							+ "substring : " + a.substring(maxStart, maxEnd + 1));

				} else {
					Math.max(L[i + 1][j], L[i][j - 1]);
				}
			}
		}
//		for (int i = 0; i < n; i++) {
//			System.out.println(Arrays.toString(L[i]));
//		}
		// System.out.println("Palindrome : max size: " + max + "substring : " +
		// a.substring(maxStart, maxEnd + 1));
		return a.substring(maxStart, maxEnd + 1);
	}

	// Given a string of digits b, return the number of palindromic subsequences of
	// b having length 5 Input: b = "123321"

	// Output: 2
	// Explanation:
	// There are 6 possible subsequences of length 5: "12332","12331","12321","1
	// 2321","13321","23321".
	// Two of them (both equal to "12321") are palindromic..
	public static int countPalindromicSubsequences(String s, int k) {
		int n = s.length();

		// Create a 2D table to store the counts of palindromic subsequences of length k
		int[][] dp = new int[n][n];

		// Initialize the table for subsequences of length 1 (single characters)
		for (int i = 0; i < n; i++) {
			dp[i][i] = 1;
		}

		// Build the table for longer subsequences
		for (int length = 2; length <= k; length++) {
			for (int i = 0; i < n - length + 1; i++) {
				int j = i + length - 1;

				if (s.charAt(i) == s.charAt(j)) {
					// If the characters at the ends match, the count is increased by 1
					dp[i][j] = dp[i + 1][j - 1] + 1;
				} else {
					// If the characters at the ends don't match, the count is the sum of counts
					// without the ends
					dp[i][j] = dp[i + 1][j] + dp[i][j - 1] - dp[i + 1][j - 1];
				}
			}
		}

		// The count of palindromic subsequences of length k is stored at dp[0][n - 1]
		return dp[0][n - 1];
	}

	public static void main(String args[]) {
		System.out.println(longestPalindromeSubString("abaabaa"));
		String s = "123321";
		int k = 5;

		int result = countPalindromicSubsequences(s, k);
		System.out
				.println("Number of fixed-length palindromic subsequences of length " + k + " in " + s + ": " + result);

	}

}

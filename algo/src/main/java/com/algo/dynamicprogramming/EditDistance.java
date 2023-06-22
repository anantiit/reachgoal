package com.algo.dynamicprogramming;

public class EditDistance {
	public int minDistance(String word1, String word2) {
		int m = word1.length();
		int n = word2.length();
		int[][] dp = new int[m + 1][n + 1];
		int delete_cost = 1;
		int insert_cost = 1;
		int replace_cost = 1;
		// dp[i][j] is the min cost to convert word[1 to i] to word2[1..j]
		for (int j = 0; j < n + 1; j++) {
			dp[0][j] = insert_cost * j;
		}
		for (int i = 0; i < m + 1; i++) {
			dp[i][0] = delete_cost * i;
		}
		for (int i = 1; i < m + 1; i++) {
			int minDist = 0;
			for (int j = 1; j < n + 1; j++) {
				if (word1.charAt(i - 1) == word2.charAt(j - 1))
					minDist = dp[i - 1][j - 1];
				else {
					minDist = dp[i - 1][j - 1] + replace_cost;
				}
				if (minDist > dp[i - 1][j] + insert_cost) {
					minDist = dp[i - 1][j] + insert_cost;
				}
				if (minDist > dp[i][j - 1] + delete_cost) {
					minDist = dp[i][j - 1] + delete_cost;
				}

				dp[i][j] = minDist;
			}

		}

		return dp[m][n];
	}

	public static void main(String[] args) {
		String word1 = "";
		String word2 = "a";
		EditDistance ed = new EditDistance();
		System.out.println(ed.minDistance(word1, word2));
	}
}

package com.csfundamentals.graphs;

public class ReachDestFromSource {
	/*
	 * NxM mesh. 0 and x are the type nodes. move from top left to bottom right.
	 * calculate total of paths I cean reach top left to bottom right
	 * 
	 * 0 00 0 x x 0 0 0 x 0 0
	 * 
	 * 
	 * 
	 * tw(1,1,n,m) = tw(1,2,n,m)+tw(2,1,n,m) if(i==n && j==m)
	 * 
	 */

	public static int totalWays(int i, int j, char[][] a, int n, int m) {
		if (a[n - 1][m - 1] == 'x') {
			return 0;
		}
		// base case resursive
		if (i == n - 1 && j == m - 1) {
			return 1;
		}
		int totalWays = 0;
		if (isSafe(a, i, j + 1, n, m)) {
			totalWays = totalWays + totalWays(i, j + 1, a, n, m);
		}
		if (isSafe(a, i + 1, j, n, m)) {
			totalWays = totalWays + totalWays(i + 1, j, a, n, m);
		}
		return totalWays;
	}

	static boolean isSafe(char[][] a, int i, int j, int n, int m) {
		return i < n && j < m && a[i][j] != 'x';
	}

	public static void main(String args[]) {
		char[][] mat = { { '0', '0', '0', '0' }, { 'x', 'x', '0', '0' }, { '0', 'x', '0', '0' } };
		System.out.println(totalWays(0, 0, mat, mat.length, mat[0].length));
	}

}

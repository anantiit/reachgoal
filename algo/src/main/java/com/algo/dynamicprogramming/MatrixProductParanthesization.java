package com.algo.dynamicprogramming;

import java.util.Arrays;

/*
 * @ProblemStatement Matrix product paranthesizations : Given a series of matrices we need to find the paranthesizations so that number of multiplications is minimized
 */
public class MatrixProductParanthesization {
	public static int minMatProductParanthesization(int[] P) {
		int n = P.length - 1;
		int[][] M = new int[n][n];
		int[][] Path = new int[n][n];
		for (int i = 0; i < n; i++) {
			M[i][i] = 0;
		}
		// M[i][j] should be filled in acsending order of i-j, as for higher length
		// lower length should be already be available calculated . So better have l
		// =i-j as the outer loop
		for (int l = 2; l <= n; l++) {
			for (int i = 0; i < n - l + 1; i++) {
				int j = i + l - 1;
				M[i][j] = Integer.MAX_VALUE;
				for (int k = i; k < j; k++) {
					int thisCost = M[i][k] + M[k + 1][j] + P[i] * P[k + 1] * P[j + 1];
					if (thisCost < M[i][j]) {
						M[i][j] = thisCost;
						Path[i][j] = k;
					}
				}
			}
		}
		for (int i = 0; i < n; i++) {
			System.out.println(Arrays.toString(Path[i]));
		}
		for (int i = 0; i < n; i++) {
			System.out.println(Arrays.toString(M[i]));
		}
		return M[0][n - 1];
	}

	public static void main(String args[]) {
		int[] P = { 3, 100, 2, 5, 2 };
		System.out.println(minMatProductParanthesization(P));
	}
}

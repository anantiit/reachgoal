package com.csfundamentals.arrays;
/*
 * 
 * Given row wise and column wise sorted matrix search an element in that

4*4
19 - target
1  5  8  10
9  12 13 30
11 17 19 40
16 18 20 50


*/

public class FindIn2DSortedArray {
	// Finds Element in row, column wise sorted matrix
	public static void findElement(int[][] mat, int row, int col, int target) {
		int i = 0;
		int j = col - 1;
		while (i < row && j >= 0) {
			if (mat[i][j] == target) {
				System.out.println("found i:" + i + "j:" + j);
				return;
			}
			if (mat[i][j] < target) {
				i++;
			} else {
				j--;
			}
		}
		System.out.println("Not found");
	}

	public static void main(String[] args) {
		int[][] mat = { { 1, 5, 8, 10 }, { 9, 12, 13, 30 }, { 11, 17, 19, 40 }, { 16, 18, 20, 50 } };
		findElement(mat, 4, 4, 30);

	}
}

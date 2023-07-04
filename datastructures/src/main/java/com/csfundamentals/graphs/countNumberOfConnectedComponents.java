package com.csfundamentals.graphs;

public class CountNumberOfConnectedComponents {

	public int numIslands(int[][] matrixGraph0s1s) {
		int numberOfConnectedComponents = 0;
		int rows = matrixGraph0s1s.length;
		int cols = matrixGraph0s1s[0].length;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (matrixGraph0s1s[i][j] != '0' && matrixGraph0s1s[i][j] != 'v') {// Not visited || not a vertex
					dfs(matrixGraph0s1s, rows, cols, i, j);
					numberOfConnectedComponents++;
				}
			}
		}
		return numberOfConnectedComponents;

	}

	public void dfs(int[][] matrixGraph0s1s, int rows, int cols, int i, int j) {
		matrixGraph0s1s[i][j] = 'v'; // Making it visited
		if (isSafe(matrixGraph0s1s, rows, cols, i - 1, j))// up
			dfs(matrixGraph0s1s, rows, cols, i - 1, j);
		if (isSafe(matrixGraph0s1s, rows, cols, i + 1, j)) // down
			dfs(matrixGraph0s1s, rows, cols, i + 1, j);
		if (isSafe(matrixGraph0s1s, rows, cols, i, j - 1))// left
			dfs(matrixGraph0s1s, rows, cols, i, j - 1);
		if (isSafe(matrixGraph0s1s, rows, cols, i, j + 1))// right
			dfs(matrixGraph0s1s, rows, cols, i, j + 1);
		return;
	}

	public boolean isSafe(int[][] matrixGraph0s1s, int rows, int cols, int row, int col) {
		return row < rows && row >= 0 && col < cols && col >= 0 && matrixGraph0s1s[row][col] == '1';
	}

	public static void main(String args[]) {
		int[][] matrixGraph0s1s = { { 1, 1, 1, 1, 0 }, { 1, 1, 0, 1, 0 }, { 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0 } };
		CountNumberOfConnectedComponents cc = new CountNumberOfConnectedComponents();
		System.out.println(cc.numIslands(matrixGraph0s1s));
	}
}

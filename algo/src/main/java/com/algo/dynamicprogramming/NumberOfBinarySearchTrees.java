package com.algo.dynamicprogramming;

/*
 * How many binary search trees are there with n vertices
 */
public class NumberOfBinarySearchTrees {

	static int[] table;

	public static int numberOfBinarySearchTreesUtil(int n) {
		table = new int[n + 1];
		return numberOfBinarySearchTrees(n);
	}

	public static int numberOfBinarySearchTrees(int n) {
		if (n == 1) {
			return 1;
		}
		if (n == 0) {
			return 1;
		}
		if (table[n] != 0) {
			return table[n];
		}
		int sum = 0;
		for (int i = 1; i < n + 1; i++) {
			sum = sum + numberOfBinarySearchTrees(i - 1) * numberOfBinarySearchTrees(n - i);
		}
		table[n] = sum;
		return table[n];
	}

	public static void main(String args[]) {
		System.out.println(numberOfBinarySearchTreesUtil(5));
	}

}

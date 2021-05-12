package com.problemsolving.dynamicprogramming;

public class DynamicProgrammingExamples {
	// Bottomup memoization
	public static int T(int n) {
		if (n == 0)
			return 2;
		int[] t = new int[n + 1];
		t[0] = 2;
		t[1] = 2;

		for (int i = 2; i < n + 1; i++) {
			t[i] = 0;
			for (int j = 1; j < i; j++) {
				t[i] = t[i] + 2 * t[j] * t[j - 1];
			}
		}
		return t[n];
	}

	public static int TOptimized(int n) {
		if (n < 1)
			return 2;
		int[] t = new int[n + 1];
		if (n == 2)
			return 2 * t[0] * t[1];
		t[0] = 2;
		t[1] = 2;
		t[2] = 2 * t[0] * t[1];
		for (int i = 3; i < n + 1; i++) {
			t[i] = t[i - 1] + 2 * t[i - 1] * t[i - 2];
		}
		return t[n];
	}

}

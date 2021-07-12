package com.csfundamentals.algo.recursion;

import com.algo.dynamicprogramming.DynamicProgrammingExamples;

public class RecursionTests {
	/*
	 * write method for T(n) = sum of 2*T(i)*T(i-1) 1<=i<=n-1
	 */
	public int T(int n) {
		if (n == 0 || n == 1)
			return 2;
		int sum = 0;
		for (int i = 1; i < n; i++) {
			sum = sum + 2 * T(i) * T(i - 1);
		}
		return sum;
	}

	public static void main(String args[]) {
		RecursionTests rt = new RecursionTests();
		int n = 3;
		System.out.println(rt.T(n));
		System.out.println(DynamicProgrammingExamples.T(n));
		System.out.println(DynamicProgrammingExamples.TOptimized(n));
	}
}

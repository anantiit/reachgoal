package com.csfundamentals.algo.problemsolving;

public class MiscellaneousProblems {
	public static int findClosestNumber(int n, int m) {
		int reminder = n % m;
		reminder = Math.abs(reminder);
		m = Math.abs(m);
		System.out.println("reminder:" + reminder);
		if (reminder < Math.abs(m - reminder)) {
			if (n < 0) {
				return n + reminder;
			}
			return n - reminder;
		} else {
			if (n < 0) {
				return n - m + reminder;
			}
			return n + m - reminder;
		}
	}

	static int closestNumber(int n, int m) {
		// find the quotient
		int q = n / m;

		// 1st possible closest number
		int n1 = m * q;

		// 2nd possible closest number
		int n2 = (n * m) > 0 ? (m * (q + 1)) : (m * (q - 1));

		// if true, then n1 is the required closest number
		if (Math.abs(n - n1) < Math.abs(n - n2))
			return n1;

		// else n2 is the required closest number
		return n2;
	}

// Driver program to test above
	public static void main(String args[]) {
		int n = 13, m = 4;
		System.out.println(findClosestNumber(n, m));

		n = -15;
		m = 6;
		System.out.println(findClosestNumber(n, m));

		n = 0;
		m = 8;
		System.out.println(findClosestNumber(n, m));

		n = 18;
		m = -7;
		System.out.println(findClosestNumber(n, m));
	}
}

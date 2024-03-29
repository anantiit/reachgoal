package com.csfundamentals.java.bitwiseopertors;

public class BitwiseOperatorExamples {
	private static final double NODE_ID_BIT_LEN = 10;

	public static void main(String[] args) {
		int arr1[] = { 1, 3, 5 };
		int n1 = arr1.length;

		// System.out.println(sumBitDifferences(arr1, n1));
		// Initial values
		int a = 5;
		int b = 7;
		System.out.println((9 << 1) + "");
		// bitwise and
		// 0101 & 0111=0101 = 5
		System.out.println("a&b = " + (a & b));

		int maxNodeVal = (int) Math.pow(2, NODE_ID_BIT_LEN);
		a = 421414214;
		System.out.println(Integer.toBinaryString(maxNodeVal));
		System.out.println(Integer.toBinaryString(a));
		System.out.println("a&b = " + Integer.toBinaryString((a & maxNodeVal)));
		System.out.println("a&b = " + (a & maxNodeVal));

		// bitwise or
		// 0101 | 0111=0111 = 7
		System.out.println("a|b = " + (a | b));

		// bitwise xor
		// 0101 ^ 0111=0010 = 2
		System.out.println("a^b = " + (a ^ b));

		// bitwise not
		// ~0101=1010
		// will give 2's complement of 1010 = -6
		System.out.println("~a = " + ~a);

		// can also be combined with
		// assignment operator to provide shorthand
		// assignment
		// a=a&b
		a &= b;
		System.out.println("a= " + a);
		int arr[] = { 12, 1, 12, 3, 12, 1, 1, 2, 3, 2, 2, 3, 8 };
		int n = arr.length;
		System.out.println("The element with single occurrence is " + getSingle(arr, n));
		int[] a1 = { 2, 3, 5, 4, 5, 3, 4, 2, 8 };
		System.out.println(
				"The element with single occurrence is " + findElementAppearsOnceInArrayWithOtherElemTwice(a1));
	}

	// Java code to find the element
	// that occur only once

	static final int INT_SIZE = 32;

	// Method to find the element that occur only once
	static int getSingle(int arr[], int n) {
		int result = 0;
		int x, sum;

		// Iterate through every bit
		for (int i = 0; i < INT_SIZE; i++) {
			// Find sum of set bits at ith position in all
			// array elements
			sum = 0;
			x = (1 << i);
			for (int j = 0; j < n; j++) {
				if ((arr[j] & x) == x)
					sum++;
			}
			// The bits with sum not multiple of 3, are the
			// bits of element with single occurrence.
			if ((sum % 3) != 0)
				result |= x;
			// System.out.println(Integer.toBinaryString(result));
		}
		// System.out.println(Integer.toBinaryString(~result));
		return result;
	}

	public static int findElementAppearsOnceInArrayWithOtherElemTwice(int[] a) {
		int n = a.length;
		int result = 0;
		for (int i = 0; i < n; i++) {
			result = result ^ a[i];
		}
		return result;
	}

	static int sumBitDifferences(int arr[], int n) {

		int ans = 0; // Initialize result

		// traverse over all bits
		for (int i = 0; i < 32; i++) {

			// count number of elements
			// with i'th bit set
			int count = 0;

			for (int j = 0; j < n; j++) {
				// System.out.println(1 << i);
				if ((arr[j] & (1 << i)) == 0)
					count++;
			}

			// Add "count * (n - count) * 2"
			// to the answer
			ans += (count * (n - count) * 2);
		}

		return ans;
	}

}

package com.csfundamentals.arrays;

import java.util.HashSet;
import java.util.Set;

public class TripletSumToK {
	public static void allTripletsSumToK(int a[], int K) {
		for (int i = 0; i < a.length; i++) {
			findPairWhichSumToT(a, K - a[i], i);
		}
	}

	public static boolean findPairWhichSumToT(int a[], int T, int j) {
		Set<Integer> set = new HashSet<Integer>();
		boolean isFound = false;
		for (int i = 0; i < a.length; i++) {
			if (i != j && set.contains(T - a[i])) {
				isFound = true;
				System.out.println(a[j] + " " + a[i] + " " + (T - a[i]));
			}
			set.add(a[i]);

		}
		return isFound;
	}

	public static void main(String[] args) {
		int arr[] = { 0, -1, 2, -3, 1 };
		allTripletsSumToK(arr, 0);

	}
}

package com.csfundamentals.arrays;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripletSumToK {
	public static List<ArrayList<Integer>> allTripletsSumToK(int a[], int K) {
		List<ArrayList<Integer>> uniqTriplets = new ArrayList<>();
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < a.length; i++) {
			map.put(a[i], i);
		}
		for (int i = 0; i < a.length; i++) {
			findPairWhichSumToT(a, K - a[i], i, uniqTriplets, map);

		}
		return uniqTriplets;
	}

	public static boolean findPairWhichSumToT(int a[], int T, int i, List<ArrayList<Integer>> uniqTriplets,
			Map<Integer, Integer> map) {
		boolean isFound = false;
		for (int j = i + 1; j < a.length; j++) {
			ArrayList<Integer> triplet = new ArrayList<Integer>();
			Integer k = map.get(T - a[j]);
			// Note: j < k is necessary as we dont want duplicate triplets
			if (j != i && k != null && j < k && i != k) {
				isFound = true;
				System.out.println(a[i] + " " + a[j] + " " + a[k]);
				triplet.add(i);
				triplet.add(j);
				triplet.add(k);
				uniqTriplets.add(triplet);
			}
		}
		return isFound;
	}

	public static void main(String[] args) {
		int arr[] = { -1, 0, 1, 2, -1, -4 };
		System.out.println(allTripletsSumToK(arr, 0));

	}
}

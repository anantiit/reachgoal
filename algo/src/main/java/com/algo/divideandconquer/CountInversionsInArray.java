package com.algo.divideandconquer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author anant
 *
 *         Count Inversions in an array | Set 1 (Using Merge Sort) Difficulty
 *         Level : Hard Last Updated : 22 Mar, 2021
 * 
 *         Inversion Count for an array indicates – how far (or close) the array
 *         is from being sorted. If the array is already sorted, then the
 *         inversion count is 0, but if the array is sorted in the reverse
 *         order, the inversion count is the maximum. Formally speaking, two
 *         elements a[i] and a[j] form an inversion if a[i] > a[j] and i < j
 */
public class CountInversionsInArray {
	// https://www.hackerrank.com/challenges/crush/problem?isFullScreen=true&h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=arrays
	public static long arrayManipulation(int n, List<List<Integer>> queries) {
		// Write your code here
		long[] arr = new long[n + 1];
		long max = Integer.MIN_VALUE;
		List<List<Long>> modifiedQueries = preprocessInput(n, queries);
		for (List<Long> operation : modifiedQueries) {
			long a = operation.get(0);
			long b = operation.get(1);
			long k = operation.get(2);
			for (int i = (int) a; i <= b; i++) {
				arr[i] += k;
				if (arr[i] > max) {
					max = arr[i];
				}
			}
		}
		return max;
	}

	private static List<List<Long>> preprocessInput(int n, List<List<Integer>> queries) {
		Collections.sort(queries, (a, b) -> (a.get(0) == b.get(0)) ? a.get(1) - b.get(1) : a.get(0) - b.get(0));
		int i = 0;
		List<List<Long>> modifiedQueries = new ArrayList<List<Long>>();
		Iterator<List<Integer>> itr = queries.iterator();
		List<Integer> prev = itr.next();
		modifiedQueries.add(List.of((long) prev.get(0), (long) prev.get(1), (long) prev.get(2)));
		int prevEntry = modifiedQueries.size() - 1;
		while (itr.hasNext()) {
			List<Integer> cur = itr.next();
			if ((prev.get(0) == cur.get(0)) && (prev.get(1) == cur.get(1))) {
				modifiedQueries.get(prevEntry).set(2, modifiedQueries.get(prevEntry).get(2) + cur.get(2));
			} else if (prev.get(1) < cur.get(1)) {
				modifiedQueries.get(prevEntry).set(1, (long) cur.get(0));
				long midK = modifiedQueries.get(prevEntry).get(2) + cur.get(2);
				modifiedQueries.add(List.of((long) cur.get(0), (long) prev.get(1), (long) midK));
				// modifiedQueries.add(List.of(prev.get(1), cur.get(1), midK));
			} else {
				// result.add(cur);
			}
		}
		return modifiedQueries;
	}

	public static int countInversions(int[] a, int low, int high) {
		int mid = (low + high) / 2;
		if (low >= high) {
			return 0;
		}
		int count = countInversions(a, low, mid);
		count += countInversions(a, mid + 1, high);
		// merge step
		int i = 0;
		int j = 0;
		int k = low;
		// Left subarray
		int[] left = Arrays.copyOfRange(a, low, mid + 1);

		// Right subarray
		int[] right = Arrays.copyOfRange(a, mid + 1, high + 1);
		while (i < left.length && j < right.length) {
			if (left[i] <= right[j]) {
				a[k++] = left[i++];
			} else {
				a[k++] = right[j++];
				count += mid + 1 - (low + i);
			}
		}
		while (i < left.length)
			a[k++] = left[i++];
		while (j < right.length)
			a[k++] = right[j++];
		return count;
	}

	public static void main(String args[]) {
		int[] arr = { 2, 4, 1, 3, 5 };

		System.out.println(countInversions(arr, 0, arr.length - 1));
		System.out.println(Arrays.toString(arr));
	}

}

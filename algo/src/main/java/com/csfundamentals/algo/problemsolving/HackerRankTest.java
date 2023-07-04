package com.csfundamentals.algo.problemsolving;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class HackerRankTest {
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
}

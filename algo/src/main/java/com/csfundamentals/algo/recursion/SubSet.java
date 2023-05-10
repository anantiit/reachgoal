package com.csfundamentals.algo.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SubSet {
	public static void findAllSubsets(int[] a, int offset, ArrayList<Set<Integer>> subsets, Set<Integer> cur) {
		if (offset == a.length) {
			subsets.add(cur);
			return;
		}
		Set<Integer> cur1 = new HashSet<Integer>();
		cur1.addAll(cur);
		cur1.add(a[offset]);
		subsets.addAll(findAllSubsets(a, offset + 1, cur1));
		subsets.addAll(findAllSubsets(a, offset + 1, cur));
	}

	public static ArrayList<Set<Integer>> findAllSubsets(int[] a, int offset, Set<Integer> cur) {
		ArrayList<Set<Integer>> subsets = new ArrayList<Set<Integer>>();
		if (offset == a.length) {
			subsets.add(cur);
			return subsets;
		}
		Set<Integer> cur1 = new HashSet<Integer>();
		cur1.addAll(cur);
		cur1.add(a[offset]);
		subsets.addAll(findAllSubsets(a, offset + 1, cur1));
		subsets.addAll(findAllSubsets(a, offset + 1, cur));
		return subsets;
	}

	public static ArrayList<Set<Integer>> findAllSubsetsIteratively(int[] a) {
		ArrayList<Set<Integer>> subsets = new ArrayList<Set<Integer>>();
		int n = a.length;
		subsets.add(new HashSet<Integer>());

		for (int i = 0; i < n; i++) {
			ArrayList<Set<Integer>> curSubset = new ArrayList<Set<Integer>>();
			int m = subsets.size();
			for (int j = 0; j < m; j++) {
				Set<Integer> tempSet = new HashSet<Integer>(subsets.get(j));
				tempSet.add(a[i]);
				curSubset.add(tempSet);
			}
			subsets.addAll(curSubset);
		}
		return subsets;
	}

	public static void main(String args[]) {
		int[] a = { 3, 5, 4 };
		System.out.println("All subsets:" + findAllSubsets(a, 0, new HashSet<Integer>()));
		ArrayList<Set<Integer>> subsets = new ArrayList<Set<Integer>>();
		findAllSubsets(a, 0, subsets, new HashSet<Integer>());
		System.out.println("All subsets" + subsets);
		System.out.println("All subsets iterative:" + findAllSubsetsIteratively(a));
	}
}

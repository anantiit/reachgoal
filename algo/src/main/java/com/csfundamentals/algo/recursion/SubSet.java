package com.csfundamentals.algo.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
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
		findAllSubsets(a, offset + 1, subsets, cur1);
		findAllSubsets(a, offset + 1, subsets, cur);
	}

	public static ArrayList<Set<Integer>> findAllSubsetsRecursionWithReturn(int[] a, int offset, Set<Integer> cur) {
		ArrayList<Set<Integer>> subsets = new ArrayList<Set<Integer>>();
		if (offset == a.length) {
			subsets.add(cur);
			return subsets;
		}
		Set<Integer> cur1 = new HashSet<Integer>();
		cur1.addAll(cur);
		cur1.add(a[offset]);
		subsets.addAll(findAllSubsetsRecursionWithReturn(a, offset + 1, cur1));
		subsets.addAll(findAllSubsetsRecursionWithReturn(a, offset + 1, cur));
		return subsets;
	}

	public static ArrayList<Set<Integer>> findAllSubsetsIteratively(int[] a) {
		ArrayList<Set<Integer>> subsets = new ArrayList<Set<Integer>>();
		int n = a.length;
		subsets.add(new HashSet<Integer>());
		for (int i = 0; i < n; i++) {
			ArrayList<Set<Integer>> curSubset = new ArrayList<Set<Integer>>();
			int m = subsets.size();
			// For every set in the previous level, making new set with additional element.
			// so the previous sets does not contain this ith element and the new sets will
			// contain.So principal of inclusion and exclusion is followed
			for (int j = 0; j < m; j++) {
				Set<Integer> tempSet = new HashSet<Integer>(subsets.get(j));
				tempSet.add(a[i]);
				curSubset.add(tempSet);
			}
			subsets.addAll(curSubset);
		}
		return subsets;
	}

	public static void getAllKSizeSubsequencesInTheOrderGivenInArray(int[] a, int k, int offset,
			ArrayList<ArrayList<Integer>> result, ArrayList<Integer> cur) {
		if (cur.size() == k) {
			result.add(cur);
			return;
		}
		if (offset == a.length) {
			return;
		}
		ArrayList<Integer> cur1 = new ArrayList<Integer>();
		cur1.addAll(cur);
		cur1.add(a[offset]);
		getAllKSizeSubsequencesInTheOrderGivenInArray(a, k, offset + 1, result, cur1);
		getAllKSizeSubsequencesInTheOrderGivenInArray(a, k, offset + 1, result, cur);
	}

	public static ArrayList<ArrayList<Integer>> getAllKSizeSubsequencesInTheOrderGivenInArray(int[] a, int k) {
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		int n = a.length;
		result.add(new ArrayList<Integer>());
		for (int i = 0; i < n; i++) {
			ArrayList<ArrayList<Integer>> curList = new ArrayList<ArrayList<Integer>>();
			int m = result.size();
			// For every set till the previous index i, making new set with additional
			// element. so the previous sets does not contain this ith element and the new
			// sets will contain.So principal of inclusion and exclusion is followed
			for (int j = 0; j < m; j++) {
				if (result.get(j).size() < k) {
					ArrayList<Integer> tempList = new ArrayList<Integer>(result.get(j));
					tempList.add(a[i]);
					curList.add(tempList);
				}
			}
			result.addAll(curList);
		}
		// Remove all sets/lists of size less than given k
		ListIterator<ArrayList<Integer>> lItr = result.listIterator();
		ArrayList<Integer> removeList;
		while (lItr.hasNext()) {
			removeList = lItr.next();
			if (removeList.size() != k)
				lItr.remove();
		}

		return result;
	}

	public static void main(String args[]) {
		int[] a = { 3, 5, 4 };
		System.out.println("All subsets:" + findAllSubsetsRecursionWithReturn(a, 0, new HashSet<Integer>()));
		ArrayList<Set<Integer>> subsets = new ArrayList<Set<Integer>>();
		findAllSubsets(a, 0, subsets, new HashSet<Integer>());
		System.out.println("All subsets" + subsets);
		System.out.println("All subsets iterative:" + findAllSubsetsIteratively(a));
		int[] a1 = { 1, 2, 3, 4, 5 };
		ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> cur = new ArrayList<Integer>();
		getAllKSizeSubsequencesInTheOrderGivenInArray(a1, 2, 0, result, cur);
		System.out.println("AllKSizeSubsequencesInTheOrderGivenInArray: \n" + result);
		System.out.println(getAllKSizeSubsequencesInTheOrderGivenInArray(a1, 2));
	}
}

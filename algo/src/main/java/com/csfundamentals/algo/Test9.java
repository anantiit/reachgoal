package com.csfundamentals.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/*
 * Find the shortest subarray whose degree is same as parent array. 
 * degree: maximum number of times an element is repeated
 * [3,4,5,4,5,5,6,4,3,10,12] ->3
 * [5,4,5,5]->3 
 * 
 * hashmap<>
 * 
 */
public class Test9 {
	public void shortestDegreeArray(int[] a) {
		int n = a.length;
		// To store the location of each element
		HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> indexList = map.get(a[i]);
			if (indexList == null) {
				indexList = new ArrayList<Integer>();
			}
			indexList.add(i);
			map.put(a[i], indexList);
		}
		int shortSizeArrayStart = -1;
		int shortSizeArrayEnd = -1;
		int shortArraySize = 1;
		int maxDegree = 1;
		for (Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
			ArrayList<Integer> list = entry.getValue();
			if (list.size() >= maxDegree) {
				int arraysize = list.get(list.size() - 1) - list.get(0);
				if (maxDegree == list.size() && shortArraySize < arraysize) {
					continue;
				}
				shortSizeArrayStart = list.get(0);
				shortSizeArrayEnd = list.get(list.size() - 1);
				shortArraySize = arraysize;
				maxDegree = list.size();
			}
		}
	}

}

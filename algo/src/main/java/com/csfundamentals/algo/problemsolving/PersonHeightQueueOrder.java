package com.csfundamentals.algo.problemsolving;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class PersonHeightQueueOrder {
	public static void main(String[] args) {
		// Each pair array represents [person height, number of persons who are taller
		// than him in front]
		int[][] people = { { 7, 0 }, { 4, 4 }, { 7, 1 }, { 5, 0 }, { 6, 1 }, { 5, 2 } };
		System.out.println(Arrays.toString(reconstructQueue(people)));
	}

	// Here we need to put linked list as we need to insert in between the list
	public static int[][] reconstructQueue(int[][] people) {
		Arrays.sort(people, (a, b) -> a[0] == b[0] ? a[1] - b[1] : b[0] - a[0]);

		List<int[]> ordered = new LinkedList<>();
		for (int[] p : people)
			ordered.add(p[1], p);

		for (int[] op : ordered) {
			System.out.print(Arrays.toString(op));
		}
		return ordered.toArray(new int[people.length][2]);
	}
}

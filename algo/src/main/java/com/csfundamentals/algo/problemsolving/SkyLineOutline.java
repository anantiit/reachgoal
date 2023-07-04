package com.csfundamentals.algo.problemsolving;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class SkyLineOutline {
//https://leetcode.com/problems/the-skyline-problem/description/?envType=featured-list&envId=top-interview-questions

	// Sweep line and priority Queue method
	public static List<List<Integer>> getSkyline(int[][] buildings) {
		// Iterate over all buildings, for each building = [left, right, height]
		// add (left, height) and (right, height) to 'edges'.
		List<List<Integer>> edges = new ArrayList<>();
		for (int i = 0; i < buildings.length; ++i) {
			edges.add(Arrays.asList(buildings[i][0], buildings[i][2]));
			edges.add(Arrays.asList(buildings[i][1], -buildings[i][2]));
		}
		Collections.sort(edges, (a, b) -> {
			return a.get(0) - b.get(0);
		});

		// Initailize two empty priority queues 'live' and 'past',
		// an empty list 'answer' to store the skyline key points.
		Queue<Integer> live = new PriorityQueue<>((a, b) -> {
			return b - a;
		});
		Queue<Integer> past = new PriorityQueue<>((a, b) -> {
			return b - a;
		});
		List<List<Integer>> answer = new ArrayList<>();

		int idx = 0;

		// Iterate over all the sorted edges.
		while (idx < edges.size()) {
			// Since we might have multiple edges at same x,
			// Let the 'currX' be the current position.
			int currX = edges.get(idx).get(0);

			// While we are handling the edges at 'currX':
			while (idx < edges.size() && edges.get(idx).get(0) == currX) {
				// The height of the current building.
				int height = edges.get(idx).get(1);

				// If this is a left edge, add `height` to 'live'.
				// Otherwise, add `height` to `past`.
				if (height > 0) {
					live.offer(height);
				} else {
					past.offer(-height);
				}
				idx++;
			}

			// If the tallest live building has been passed,
			// we remove it from 'live'.
			while (!past.isEmpty() && live.peek().equals(past.peek())) {
				live.remove();
				past.remove();
			}

			// Get the maximum height from 'live'.
			int currHeight = live.isEmpty() ? 0 : live.peek();

			// If the height changes at 'currX', we add this
			// skyline key point [currX, max_height] to 'answer'.
			if (answer.isEmpty() || answer.get(answer.size() - 1).get(1) != currHeight) {
				answer.add(Arrays.asList(currX, currHeight));
			}
		}

		// Return 'answer' as the skyline.
		return answer;
	}

	public static void main(String args[]) {
		int[][] buildings = { { 2, 9, 10 }, { 3, 7, 15 }, { 5, 12, 12 }, { 15, 20, 10 }, { 19, 24, 8 } };
		System.out.println(getSkyline(buildings));

	}
}

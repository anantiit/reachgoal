package com.csfundamentals.stack;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NextGreatorElement {
	public static void main(String args[]) {
		int[] arr = { 1, 2, 5, 7, 5, 9 };
		int[] heights = { 0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1 };
		System.out.println(nextGratorElement(arr));
		// System.out.println(tappedWaterUnits(heights));
	}

	public static Map<Integer, Integer> nextGratorElement(int[] arr) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		Stack<Integer> s = new Stack<Integer>();
		s.add(arr[0]);
		int i = 1;
		while (!s.isEmpty() && i < arr.length) {
			while (!s.isEmpty() && s.peek() < arr[i]) {
				map.put(s.pop(), arr[i]);
			}
			s.push(arr[i]);
			i++;
		}
		while (!s.isEmpty()) {
			map.put(s.pop(), -1);
		}
		return map;
	}

}

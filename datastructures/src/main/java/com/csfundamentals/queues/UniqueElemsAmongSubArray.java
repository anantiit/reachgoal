package com.csfundamentals.queues;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

public class UniqueElemsAmongSubArray {

	public static void main(String[] args) {
		/*
		 * Enter your code here. Read input from STDIN. Print output to STDOUT. Your
		 * class should be named Solution.
		 */
		Scanner scanner = new Scanner(System.in);

		// Read the first line containing two integers
		int n = scanner.nextInt();
		int m = scanner.nextInt();

		// Read the second line containing n integers
		int[] a = new int[n];
		for (int i = 0; i < n; i++) {
			a[i] = scanner.nextInt();
		}

		System.out.println(maxUniqueElemsAmongSubArrays(a, n, m));
	}

	public static int maxUniqueElemsAmongSubArrays(int[] a, int n, int m) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Deque<Integer> deque = new LinkedList<Integer>();
		int maxUniqueCount = 0;
		for (int i = 0; i < m; i++) {
			deque.addLast(a[i]);
			map.compute(a[i], (k, v) -> (v == null) ? 1 : v + 1);
		}
		maxUniqueCount = map.keySet().size();
		for (int i = m; i < n; i++) {
			int currentUniqCount = 0;
			deque.addLast(a[i]);
			map.compute(a[i], (k, v) -> (v == null) ? 1 : v + 1);
			int temp = deque.removeFirst();
			map.compute(temp, (k, v) -> (v == 1) ? null : v - 1);
			currentUniqCount = map.keySet().size();
			if (maxUniqueCount < currentUniqCount) {
				maxUniqueCount = currentUniqCount;
			}
		}
		return maxUniqueCount;
	}
}

package com.csfundamentals.heap;

import java.util.PriorityQueue;

public class Heap {
	private void getMaxKElements(int[] arr, int n, int k) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		int i = 0;
		for (; i < k; i++) {
			pq.add(arr[i]);
		}
		for (; i < n; i++) {
			if (pq.peek() < arr[i]) {
				pq.poll();
				pq.add(arr[i]);
			}
		}
		System.out.print(pq);
	}

	// Driver code
	public static void main(String[] args) {
		int[] arr = { 11, 3, 2, 1, 15, 5, 4, 45, 88, 96, 50, 45 };
		int size = arr.length;
		int k = 3;
		Heap mh = new Heap();
		mh.getMaxKElements(arr, size, k);
	}
}

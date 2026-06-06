package com.naidu.java_practice;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class TestAnything {
	public static void main(String[] args) {
		System.out.println('a' + 0);
		PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(10, new Comparator<Integer>() {
			@Override
			public int compare(Integer a, Integer b) {
				return b - a;
			}
		});

		PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();

		int[] ia = { 1, 10, 5, 3, 4, 7, 6, 9, 8 };

		for (int item : ia) {
			minHeap.add(item);
			maxHeap.add(item);
		}

		System.out.println("Min heap:");

		while (minHeap.size() != 0) {
			System.out.println(minHeap.poll());
		}

		System.out.println("Max heap:");
		while (maxHeap.size() != 0) {
			System.out.println(maxHeap.poll());
		}

		PriorityQueue<String> strMaxHeap = new PriorityQueue<String>(10, new Comparator<String>() {
			@Override
			public int compare(String a, String b) {
				return b.compareTo(a);
			}
		});
		strMaxHeap.addAll(List.of("jcva", "javac", "handson", "xsgood"));
		System.out.println("Max heap:" + strMaxHeap);
		while (strMaxHeap.size() != 0) {
			System.out.println(strMaxHeap.poll());
		}
	}
}

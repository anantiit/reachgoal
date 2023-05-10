package com.problemsolving.random;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

public class AnyProgramTest {
	public static void main(String[] args) {

		int variable = 15;
		System.out.println("Original value of the variable = " + variable);

		// after using increment operator
		System.out.println("++variable = " + (++variable));
		System.out.println("variable++ = " + (variable++));

		// priorityQueueTest();
//		System.out.println(numJewelsInStones("aA", "aAafsafsdf"));
//		System.out.println('A' + 0 + "," + ('a' + 0));
//		System.out.println(numJewelsInStones1("aA", "aAafsafsdf"));
	}

	public static int numJewelsInStones(String J, String S) {
		int count = 0;
		Set<Character> jewelSet = new HashSet<Character>();
		for (int i = 0; i < J.length(); i++) {
			jewelSet.add(J.charAt(i));

		}
		for (int i = 0; i < S.length(); i++) {
			if (jewelSet.contains(S.charAt(i))) {
				count++;
			}
		}
		return count;
	}

	public static int numJewelsInStones1(String J, String S) {
		int count = 0;
		boolean[] jewelArray = new boolean[256];
		for (int i = 0; i < J.length(); i++) {
			jewelArray[J.charAt(i)] = true;
		}
		for (int i = 0; i < S.length(); i++) {
			if (jewelArray[S.charAt(i)]) {
				count++;
			}
		}
		return count;
	}

	public static void priorityQueueTest() {
		PriorityQueue<String> pq = new PriorityQueue<>();

		pq.add("Geeks");
		pq.add("For");
		pq.add("Geeks1");
		System.out.println(pq.toString() + " ");
		Iterator<String> iterator = pq.iterator();
		int counter = 0;
		while (iterator.hasNext()) {
			counter++;
			System.out.print(iterator.next() + " ");
			if (counter == 2) {
				iterator.remove();
			}
		}
		System.out.println(pq.toString() + " ");
	}
}

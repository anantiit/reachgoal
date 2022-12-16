/**
 * @author appalanaiduabotula
 * 
 */
package com.naidu.recursion;

import java.util.Stack;

public class RecursionProblems {
	public static void main(String[] args) {
		RecursionProblems recursionPromlems = new RecursionProblems();
		int[] a = { 1, 2, 3, 6, 7, 9 };
		System.out.println(recursionPromlems.checkSorted(a, a.length));
	}

	/**
	 * Recusive solution for towers of hanoi
	 * 
	 * @return
	 */

	public void towersOfHanoi(Stack a, Stack b, Stack auxillary, int n) {
		if (a.isEmpty()) {
			return;
		}
		towersOfHanoi(a, auxillary, b, n - 1);
		System.out.println("move disc " + n + "from a to b");
		towersOfHanoi(auxillary, b, a, n - 1);
	}

	public boolean checkSorted(int[] a, int index) {
		if (a.length == 1 || index == 1) {
			return true;
		}
		return (a[index - 1] <= a[index - 2]) ? false : checkSorted(a, index - 1);
	}

	public void generateAllStringOfNbits(int n) {
		if (n < 1) {
			// System.out.println(Arrays.toString(a));
		} else {

		}
	}
}
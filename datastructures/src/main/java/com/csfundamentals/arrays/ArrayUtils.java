package com.csfundamentals.arrays;

import java.util.Stack;

public class ArrayUtils {
	public static void main(String[] args) {
		int[] arr = { 11, 13, 21, 3 };
		// { 5, 3, 2, 4, 1 };
		nextGreaterElement(arr);
		// System.out.println(Arrays.toString(arr));
		// int rotateLength = 4;
		// rotate(arr, rotateLength);
		// System.out.println(Arrays.toString(arr));
	}

	public static void rotate(int[] arr, int rotateLength) {
		if (arr != null || arr.length > 1) {
			if (rotateLength >= arr.length) {
				rotateLength = arr.length % rotateLength;
			}
			for (int fromFirst = 0, fromLast = arr.length - 1; fromFirst < rotateLength; fromFirst++, fromLast--) {
				int temp = arr[fromFirst];
				arr[fromFirst] = arr[fromLast];
				arr[fromLast] = temp;
			}
		}
	}

	private static void nextGreaterElement(int a[]) {
		Stack<Integer> stack = new Stack<Integer>();
		for (int i = 0; i < a.length; i++) {
			while (!stack.isEmpty() && stack.peek() < a[i]) {
				System.out.println(stack.peek() + "," + a[i]);
				stack.pop();
			}
			stack.push(a[i]);
		}
		while (!stack.isEmpty()) {
			System.out.println(stack.pop() + ", No next greater element");
		}
	}
}

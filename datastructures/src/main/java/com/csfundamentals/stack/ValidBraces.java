package com.csfundamentals.stack;

import java.util.Stack;

public class ValidBraces {

	public int countBalancedSubstrings(String string) {
		int count = 0;
		Stack<Integer> stack = new Stack<>();
		int i = 0;
		for (char ch : string.toCharArray()) {
			if (ch == '(') {
				stack.push(i);
			} else if (ch == ')') {
				if (!stack.isEmpty()) {
					int k = stack.pop();
					System.out.println("SubString from :" + k + ":to:" + i + string.substring(k, i + 1));
					count++;
				}
			}
			i++;
		}

		return count;
	}

	public static void main(String[] args) {
		String string = "))(((()()))))())";
		int count = new ValidBraces().countBalancedSubstrings(string);
		System.out.println(count);
	}
}

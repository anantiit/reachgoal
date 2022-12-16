package com.csfundamentals.algo.recursion;

import java.util.Arrays;

public class PrintPermutations {
//Given a string generate all permutations
	public static int counter = 0;

	public static void printPermutations(char[] str, int s) {
		if (s == str.length - 1) {
			System.out.println(Arrays.toString(str) + ":" + counter++);
			return;
		}
		for (int i = s; i < str.length; i++) {
			char temp = str[s];
			str[s] = str[i];
			str[i] = temp;
			printPermutations(str, s + 1);
		}
	}

	public static void main(String[] args) {
		String s = "abcd";
		printPermutations(s.toCharArray(), 0);
	}
}

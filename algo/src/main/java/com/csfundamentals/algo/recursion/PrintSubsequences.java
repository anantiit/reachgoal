package com.csfundamentals.algo.recursion;

public class PrintSubsequences {
//Given a string print all its subsequences
	public static void printSubSequences(char[] str, String output, int index) {
		if (index == str.length) {
			if (!output.isBlank())
				System.out.println(output);
			return;
		}
		printSubSequences(str, output + str[index], index + 1);
		printSubSequences(str, output, index + 1);
	}

	public static void main(String[] args) {
		String s = "abc";
		printSubSequences(s.toCharArray(), "", 0);
	}
}

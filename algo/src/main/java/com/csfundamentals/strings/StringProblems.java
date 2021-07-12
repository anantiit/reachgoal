package com.csfundamentals.strings;

public class StringProblems {
	static String reverseWords(String S) {
		String s1[] = S.split("\\.");
		StringBuilder sb = new StringBuilder("");
		for (int i = s1.length - 1; i > 0; i--) {
			sb.append(s1[i] + ".");
			System.out.println(sb);
		}
		if (s1.length > 0)
			sb.append(s1[0]);
		return sb.toString();
	}

	public static void main(String args[]) {
		String S = "i.like.this.program.very.much";
		System.out.println(reverseWords(S));
	}
}

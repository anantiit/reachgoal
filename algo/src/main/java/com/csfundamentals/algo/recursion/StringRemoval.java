package com.csfundamentals.algo.recursion;

public class StringRemoval {
	// Observe two ways of constructing result from recursive methods. 1st way
	public static void charRemove(String s, char c, String result) {
		if (s.isEmpty()) {
			System.out.println(result);
			return;
		}
		if (s.charAt(0) == c) {
			charRemove(s.substring(1), c, result);
		} else {
			charRemove(s.substring(1), c, result + s.charAt(0));
		}
	}

	// Observe two ways of constructing result from recursive methods. 2nd way
	public static String charRemove(String s, char c) {
		if (s.isEmpty()) {
			return "";
		}
		if (s.charAt(0) == c) {
			return charRemove(s.substring(1), c);
		} else {
			return s.charAt(0) + charRemove(s.substring(1), c);
		}
	}

	public static String stringRemove(String s, String rm) {
		if (s.isEmpty() || rm.isEmpty()) {
			return s;
		}
		if (s.startsWith(rm)) {
			return stringRemove(s.substring(rm.length()), rm);
		} else {
			return s.charAt(0) + stringRemove(s.substring(1), rm);
		}
	}

	public static void main(String[] args) {
		String s = "dbaddcssdfgddcghd";
		String result = "";
		charRemove(s, 'd', result);
		System.out.println(charRemove(s, 'd'));
		System.out.println(stringRemove(s, "ddc"));
	}
}

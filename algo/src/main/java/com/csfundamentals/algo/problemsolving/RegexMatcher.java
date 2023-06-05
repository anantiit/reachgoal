package com.csfundamentals.algo.problemsolving;

public class RegexMatcher {
	// Different states dotmatch, *match, charmatch, end.

	public static boolean isMatch(String s, String p, int i, int j) {
		if (i == s.length() && j == p.length()) {
			return true;
		} else if (i == s.length() && (j < p.length() && p.charAt(j) != '*')) {
			return false;
		}
		while (i < s.length() && j < p.length()) {
			if (p.charAt(j) == '.') {
				i++;
				j++;
			} else if (p.charAt(j) == '*') {
				if (j == p.length() - 1) {
					return true;
				}
				return isMatch(s, p, i + 1, j + 1) || isMatch(s, p, i + 1, j);
			} else {
				while (i < s.length() && j < p.length() && (p.charAt(j) == s.charAt(i) && !isRegexChar(p.charAt(j)))) {
					i++;
					j++;
				}
				return isMatch(s, p, i, j);
			}
		}
		return false;
	}

	private static boolean isRegexChar(char character) {
		return (character == '.' && character == '*');
	}

	public static void main(String[] args) {
		String s = "afsaafafa";
		String p = "*a";
		System.out.println(isMatch(s, p, 0, 0));
	}

}

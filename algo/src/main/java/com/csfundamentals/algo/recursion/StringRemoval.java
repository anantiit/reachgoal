package com.csfundamentals.algo.recursion;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

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

	public static void removeDuplicates() {
		/*
		 * Enter your code here. Read input from STDIN. Print output to STDOUT. Your
		 * class should be named Solution.
		 */
		Scanner scanner = new Scanner(System.in);
		// Read the first line containing two integers
		int n = scanner.nextInt();
		// Read the second line containing n integers
		List<String> a = new ArrayList<String>();
		for (int i = 0; i < n + 1; i++) {
			a.add(scanner.nextLine());
		}
		scanner.close();
		for (String line : a) {
			Set<String> set = new HashSet<String>();
			String[] wordArr = line.split(" ");
			int i = 0;
			while (i < wordArr.length - 1) {
				String word = wordArr[i];
				String wordLowerCase = word.toLowerCase();
				if (!set.contains(wordLowerCase)) {
					set.add(wordLowerCase);
					System.out.print(word + " ");
				}
				i++;
			}
			String word = wordArr[i];
			if (!set.contains(word.toLowerCase()))
				System.out.println(wordArr[i]);
		}
	}

	public static void main(String[] args) {
//		String s = "dbaddcssdfgddcghd";
//		String result = "";
//		charRemove(s, 'd', result);
//		System.out.println(charRemove(s, 'd'));
//		System.out.println(stringRemove(s, "ddc"));
		removeDuplicates();
	}
}

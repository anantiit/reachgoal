package com.problemsolving.random;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AnyProgramTest {
	public static void main(String[] args) {
		System.out.println(numJewelsInStones("aA", "aAafsafsdf"));
		System.out.println('A' + 0 + "," + ('a' + 0));
		System.out.println(numJewelsInStones1("aA", "aAafsafsdf"));
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
}

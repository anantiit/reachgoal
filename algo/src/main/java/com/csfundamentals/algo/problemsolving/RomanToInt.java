package com.csfundamentals.algo.problemsolving;

import java.util.HashMap;
import java.util.Map;

public class RomanToInt {
	Map<Character, Integer> map = new HashMap<Character, Integer>();

	RomanToInt() {
		map.put('I', 1);
		map.put('V', 5);
		map.put('X', 10);
		map.put('L', 50);
		map.put('C', 100);
		map.put('D', 500);
		map.put('M', 1000);
	}

	public int romanToInt(String s) {
		int val = 0;
		for (int i = 0; i < s.length(); i++) {
			int cur = map.get(s.charAt(i));
			if (i < s.length() - 1) {
				if (s.charAt(i) == 'I') {
					if (s.charAt(i + 1) == 'V') {
						cur = 4;
						i = i + 1;
					} else if (s.charAt(i + 1) == 'X') {
						cur = 9;
						i = i + 1;
					}
				}

				else if (s.charAt(i) == 'X') {
					if (s.charAt(i + 1) == 'L') {
						cur = 40;
						i = i + 1;
					} else if (s.charAt(i + 1) == 'C') {
						cur = 90;
						i = i + 1;
					}
				}

				else if (s.charAt(i) == 'C') {
					if (s.charAt(i + 1) == 'D') {
						cur = 400;
						i = i + 1;
					} else if (s.charAt(i + 1) == 'M') {
						cur = 900;
						i = i + 1;
					}
				}
			}
			val = val + cur;
		}
		return val;
	}

	public static void main(String args[]) {
		RomanToInt romanToInt = new RomanToInt();
		System.out.println(romanToInt.romanToInt("MCMXCIV"));

	}
}

package com.csfundamentals.algo;

/*
From Malay Rajpara to Everyone:  04:09 PM
I can hear you
Example 1:
	Input: [1,2,0] max=1,2,
	Output: 3
	Example 2:
	Input: [3,4,-1,1] max=3,4,    
	Output: 2
	Example 3:
	Input: [7,8,9,11,12]
	Output: 1
	
	
	
From Malay Rajpara to Everyone:  04:19 PM
string could be empty and contains only lowercase letters a-z
pattern could be empty and contains only lowercase letters a-z, and characters like ? or *.
'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
Example 1:
	Input:
	s = "aa"
	p = "a"
	Output: false
	Explanation: "a" does not match the entire string "aa".
	Example 2:
	Input:
	s = "aa"
	p = "*"
	Output: true
	Explanation: '*' matches any sequence.
	Example 3:
	Input:
	s = "cb"
	p = 	"?a"
	Output: false
	Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.

	Example 4:
	Input:
	s = "abceb"
	p = "*a*b"
	Output: true
	Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
	Example 5:
	Input:
	s = "acdcb"
	p = "a*c?b"
	Output: false
	
	match(s,p,i,j)={match(s,p,i+1,j+1) s[i]==p[j] or p[j] =='?', if p[j]='*' match(s,p,i+1,j) || match(s,p,i,j+1)}

 * 
 */
public class Test {

	public static boolean match(char s[], char p[], int i, int j) {
		boolean isMatched = false;
		if (i == s.length && j == p.length) {
			return true;
		}
		if (i >= s.length && j <= p.length) {
			if (j == p.length - 1 && p[j] == '*') {
				return true;
			}
			return false;
		}
		if (i == s.length && j == p.length) {
			return true;
		}
		if (i < s.length && j >= p.length) {
			return false;
		}
		if (s[i] == p[j] || p[j] == '?') {
			isMatched = match(s, p, i + 1, j + 1);
		} else if (p[j] == '*') {
			isMatched = match(s, p, i + 1, j) || match(s, p, i, j + 1);
		}
		return isMatched;
	}

	public static void main(String args[]) {
		String s = "abceb";
		String p = "*a*b*";
		char[] sArr = s.toCharArray();
		char[] pArr = p.toCharArray();
		// System.out.println(match(sArr, pArr, 0, 0));
		int[] a = { -1, -2 };
		System.out.println(smallestMissingPositive(a));
	}
}

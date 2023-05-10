package com.csfundamentals.algo.recursion;

import java.util.ArrayList;

/*
Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

 

Example 1:

Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
Example 2:

Input: n = 1
Output: ["()"]


t(n)=3*t(n-2)

*/

public class GenerateValidParanthesis {

	public static void main(String[] args) {
		ArrayList<String> all = new ArrayList<String>();
		String s = "";
		generateParenthesis(3, 0, 0, s, all);

	}

	public static void generateParenthesis(int n, int open, int close, String s, ArrayList<String> ans) {

		// if the count of both open and close parentheses
		// reaches, it means we have generated a valid
		// parentheses. So, we add the currently generated
		// string s to the final ans and return.
		if (open == n && close == n) {
			ans.add(s);
			return;
		}

		// At any index i in the generation of the string s,
		// we can put an open parentheses only if its count
		// until that time is less than n.
		if (open < n) {
			generateParenthesis(n, open + 1, close, s + "{", ans);
		}

		// At any index i in the generation of the string s,
		// we can put a closed parentheses only if its count
		// until that time is less than the count of open
		// parentheses.
		if (close < open) {
			generateParenthesis(n, open, close + 1, s + "}", ans);
		}
	}

}

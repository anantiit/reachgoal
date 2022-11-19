package com.csfundamentals.algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;

public class CordinateTraversal {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter wr = new PrintWriter(System.out);
		int T = Integer.parseInt(br.readLine().trim());
		for (int t_i = 0; t_i < T; t_i++) {
			String[] str = br.readLine().split(" ");
			long x = Long.parseLong(str[0]);
			long y = Long.parseLong(str[1]);
			HashMap<Cordinate, String> map = new HashMap<Cordinate, String>();
			System.out.println(solve1(x, y, 7l, 5l));
		}

		wr.close();
		br.close();
	}

	static boolean solve1(long x, long y, long a, long b) {
		long gcd1 = gcd(x, y);
		long gcd2 = gcd(a, b);
		if (gcd1 == gcd2) {
			return true;
		}
		return false;
	}

	private static long gcd(long x, long y) {
		if (x == y) {
			return x;
		}
		if (x > y) {
			return gcd(x - y, y);
		}
		return gcd(x, y - x);
	}

	// This code goes to stack overflow error for very big numbers so the above
	// euclidian method is suggested
	static String solve(long x, long y, long startx, long starty, HashMap<Cordinate, String> map) {
		// Write your code here
		String no = "No";
		String yes = "Yes";
		if (map.get(new Cordinate(startx, starty)) != null) {
			return map.get(new Cordinate(startx, starty));
		}
		if (startx == x && starty == y) {

			map.put(new Cordinate(x, y), yes);
			return "Yes";
		}
		if (startx > x || starty > y) {
			map.put(new Cordinate(startx, starty), no);
			return "No";
		}
		if ("Yes".equals(solve(x, y, startx + starty, starty, map))) {
			map.put(new Cordinate(startx, starty), yes);
			return "Yes";
		}
		if ("Yes".equals(solve(x, y, startx, startx + starty, map))) {
			map.put(new Cordinate(startx, starty), yes);
			return "Yes";
		}

		return no;
	}
}

class Cordinate {
	long x;
	long y;

	Cordinate(long x, long y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean equals(Object n1) {
		Cordinate n2 = (Cordinate) n1;
		return this.x == n2.x && this.y == n2.y;
	}
}

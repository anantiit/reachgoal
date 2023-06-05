package com.naidu.java_practice.regex;

import java.util.Scanner;
import java.util.regex.Pattern;

public class IPAddress {
	public static void main(String args[]) {
		String regexPart = "([0-1]?\\d\\d|2[0-4]\\d|25[0-5])";
		String fullRegex = "^" + regexPart + "\\." + regexPart + "\\." + regexPart + "\\." + regexPart + "$";
		Scanner s = new Scanner(System.in);
		s.useDelimiter("\r");
		String[] ipArr;

		String line = s.next();
		ipArr = line.split("\r");
		s.close();
		for (String ipAddress : ipArr) {

			System.out.println(Pattern.matches(fullRegex, ipAddress));
		}
	}
}

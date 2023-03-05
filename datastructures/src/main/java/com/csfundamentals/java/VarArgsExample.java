package com.csfundamentals.java;

public class VarArgsExample {
	public static void main(String args[]) {
		printVarArgs("Hi", "How", "are", "you");
		String[] arr = { "Hello", "Naidu" };
		printVarArgs(arr);
	}

	public static void printVarArgs(String... args) {
		System.out.println(args.length);
		for (String arg : args) {
			System.out.print(arg);
		}
		System.out.println();
	}
}

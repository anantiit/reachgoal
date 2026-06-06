package com.naidu.java_practice.functionalprogramming;

import java.util.List;

/*
 * Streams
 * Lambda expression
 */
public class FunctionaPractice {
	public static void main(String[] args) {
		List<Integer> nums = List.of(12, 13, 56, 3, 17, 13);
		var courses = List.of("cs", "ece", "mech", "llb", "spring", "spring boot", "kubernetes", "Docker");
		printAllNumInStructured(nums);
		System.out.println("---");
		printAllNumInFunctional(nums);
		System.out.println("---");
		printEvenNumInFunctional(nums);
		System.out.println("---");
		printOddNumInFunctional(nums);
		printSpringCoursesFunctional(courses);
		printLongCourseNameFunctional(courses);
		printSquareFunctional(nums);
		System.out.println("--*");

		printCubeOddFunctional(nums);
		System.out.println("--**");
		System.out.println(sumList(nums));

	}

	private static void printSpringCoursesFunctional(List<String> courses) {
		courses.stream().filter(course -> course.contains("spring")).forEach(System.out::println);

	}

	private static void printLongCourseNameFunctional(List<String> courses) {
		courses.stream().filter(course -> course.length() >= 4).forEach(System.out::println);

	}

	// We will focus on how to do in structural programming
	// number -> number % 2 == 0 this is lambda expresion it is simple way to give
	// simple methods

	private static void printAllNumInFunctional(List<Integer> nums) {
		nums.stream().forEach(System.out::println);
	}

	private static void printCubeOddFunctional(List<Integer> nums) {
		nums.stream().filter(number -> number % 2 != 0).map(num -> Math.pow(num, 3)).forEach(System.out::println);
	}

	private static void printSquareFunctional(List<Integer> nums) {
		nums.stream().map(num -> Math.pow(num, 2)).forEach(System.out::println);
	}

	private static void printEvenNumInFunctional(List<Integer> nums) {
		nums.stream().filter(number -> number % 2 == 0).forEach(System.out::println);
	}

	private static void printOddNumInFunctional(List<Integer> nums) {
		nums.stream().filter(number -> number % 2 != 0).forEach(System.out::println);
	}

//We will focus on how to do in structural programming
	private static void printAllNumInStructured(List<Integer> nums) {
		for (int number : nums) {
			System.out.println(number);
		}
	}

	private static int sumList(List<Integer> list) {
		return list.stream().filter(i -> i > 10).mapToInt(i -> i).sum();
	}

}

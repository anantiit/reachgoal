package com.csfundamentals.linkedlist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MultiplyLinkedList {
	/*
	 * Input: s1 = "12453456456898" s2 = "5" Output: "60" Eg: 1234*123 3702 2468
	 * 1234
	 * 
	 */

	public static List<Integer> multiplyLinkedList(String s1, String s2) {
		List<Integer> n1 = converToList(s1);
		List<Integer> n2 = converToList(s2);
		LinkedList<Integer> resultList = new LinkedList<Integer>();
		int index = 1;
		for (int i = n1.size() - 1; i >= 0; i--) {
			LinkedList<Integer> currentList = new LinkedList<Integer>();
			int carry = 0;
			for (int j = n2.size() - 1; j >= 0; j--) {
				int temp = n1.get(i) * n2.get(j);
				temp = temp + carry;
				carry = temp / 10;
				temp = temp % 10;
				currentList.addFirst(temp);
			}
			if (carry > 0) {
				currentList.addFirst(carry);
			}
			System.out.println(currentList);
			addAndAssignResultToFirstList(resultList, currentList, index++);
			System.out.println(resultList);
		}
		System.out.println(resultList);
		return resultList;
	}

	private static void addAndAssignResultToFirstList(LinkedList<Integer> resultList, LinkedList<Integer> currentList,
			int index) {
		if (resultList.isEmpty()) {
			resultList.addAll(currentList);
			return;
		}
		int carry = 0;
		int i = resultList.size() - index;
		int j = currentList.size() - 1;
		for (j = currentList.size() - 1; j >= 0 && i >= 0; j--, i--) {
			int temp = resultList.get(i) + currentList.get(j) + carry;
			carry = temp / 10;
			temp = temp % 10;
			resultList.set(i, temp);
		}
		while (j >= 0) {
			int temp = currentList.get(j) + carry;
			carry = temp / 10;
			temp = temp % 10;
			resultList.addFirst(temp);
			j--;
		}
		if (carry > 0) {
			resultList.addFirst(carry);
		}
	}

	public static List<Integer> converToList(String s1) {
		List<Integer> list = new ArrayList<Integer>();
		char[] charArr = s1.toCharArray();
		for (int i = 0; i < charArr.length; i++) {
			list.add((int) charArr[i] - 48);
		}
		return list;
	}

	public static void main(String args[]) {
		multiplyLinkedList("12453456456898", "1");
	}

}

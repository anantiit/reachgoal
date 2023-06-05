package com.csfundamentals.linkedlist;
import java.util.LinkedList;
import java.util.List;

public class BigNumberMultiply {

	public static List<Integer> listMultiply(List<Integer> list1, List<Integer> list2) {
		LinkedList<Integer> resultList = new LinkedList<Integer>();
		int index = 0;
		int carry = 0;
		for (int i = list1.size() - 1; i >= 0; i--) {
			LinkedList<Integer> currentList = new LinkedList<Integer>();
			int list1Digit = list1.get(i);
			int temp = 0;
			for (int j = list2.size() - 1; j >= 0; j--) {
				int list2Digit = list2.get(j);
				temp = list1Digit * list2Digit + carry;
				carry = temp / 10;
				temp = temp % 10;
				currentList.addFirst(temp);
			}
			if (carry != 0) {
				currentList.addFirst(carry);
				carry = 0;
			}
			addAndAssignResultToFirstList(resultList, currentList, index++);
		}
		return resultList;
	}

	private static void addAndAssignResultToFirstList(LinkedList<Integer> resultList, LinkedList<Integer> currentList,
			int index) {
		if (resultList.isEmpty()) {
			resultList.addAll(currentList);
			return;
		}
		int carry = 0;
		int i = resultList.size() - index - 1;
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

	private static List<Integer> addLists(LinkedList<Integer> list1, LinkedList<Integer> list2) {
		// Case used only for multiplication
		int m = list1.size();
		int n = list2.size();
		if (m == 0) {
			return list2;
		} else if (n == 0) {
			return list1;
		}
		LinkedList<Integer> bigList = null;
		LinkedList<Integer> smallList = null;
		if (m >= n) {
			bigList = list1;
			smallList = list2;
		} else {
			bigList = list2;
			smallList = list1;
		}
		int carry = 0;
		int i;
		int j;
		for (i = bigList.size() - 1, j = smallList.size() - 1; i >= 0 && j >= 0; j--, i--) {
			int bigListDigit = bigList.get(i);
			int smallListDigit = smallList.get(j);
			int temp = bigListDigit + smallListDigit + carry;
			carry = temp / 10;
			temp = temp % 10;
			smallList.set(j, temp);
		}
		while (i >= 0) {
			int bigListDigit = bigList.get(i);
			int temp = bigListDigit + carry;
			carry = temp / 10;
			temp = temp % 10;
			smallList.addFirst(temp);
			i--;
		}
		if (carry != 0) {
			int smallListDigit = smallList.get(0) + carry;
			carry = smallListDigit / 10;
			smallListDigit = smallListDigit % 10;
			smallList.set(0, smallListDigit);
			if (carry > 0) {
				smallList.addFirst(carry);
			}
		}
		return smallList;
	}

	public static void main(String[] args) {
		LinkedList<Integer> list2 = new LinkedList<Integer>(List.of(5, 6, 7));
		LinkedList<Integer> list1 = new LinkedList<Integer>(List.of(1, 9, 4, 5, 5, 6, 6));

		System.out.println(listMultiply(list1, list2));
		System.out.println(addLists(list2, list1));
	}
}

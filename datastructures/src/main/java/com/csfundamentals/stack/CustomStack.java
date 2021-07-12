package com.csfundamentals.stack;

import java.util.ArrayList;
import java.util.List;

public class CustomStack {
	/*
	 * stack with additional functionality Implement getMin() -> return min in the
	 * stack P 5, gM -> 5, P 2, P 3, gM -> 2, 6> 10, 000 6, 10, 12, 14 14, 2
	 */

	List<Integer> minArrayIndexes = new ArrayList<Integer>();
	List<Integer> stack = new ArrayList<Integer>();
	int maxSize;

	CustomStack(int maxSize) {
		this.maxSize = maxSize;
	}

	public void push(int a) {
		if (stack.size() > maxSize) {
			System.out.print("Stack is full");
			return;
		}
		if (minArrayIndexes.isEmpty() || a < stack.get(minArrayIndexes.get(minArrayIndexes.size() - 1))) {
			minArrayIndexes.add(stack.size());
		}
		stack.add(a);
		System.out.println(stack);
	}

	public int pop() {
		if (stack.size() <= 0) {
			System.out.println("Stack underflow");
			return -1;
		}
		if (stack.size() - 1 <= minArrayIndexes.get(minArrayIndexes.size() - 1)) {
			minArrayIndexes.remove(minArrayIndexes.size() - 1);
		}
		int removedElem = stack.remove(stack.size() - 1);
		System.out.println(stack);
		return removedElem;
	}

	public int getMin() {
		System.out.println("minArray:" + minArrayIndexes);
		return stack.get(minArrayIndexes.get(minArrayIndexes.size() - 1));
	}

	public static void main(String args[]) {
		CustomStack cs = new CustomStack(30);
		cs.push(5);
		cs.push(2);
		System.out.println("Min:" + cs.getMin());
		cs.push(3);
		cs.push(2);
		cs.push(6);
		cs.push(4);
		System.out.println("Min:" + cs.getMin());
		cs.push(1);
		System.out.println("Min:" + cs.getMin());
		cs.push(8);
		cs.pop();
		cs.pop();
		System.out.println("Min:" + cs.getMin());
		cs.pop();
		cs.pop();
		cs.pop();
		System.out.println("Min:" + cs.getMin());
		cs.pop();
		cs.pop();
		System.out.println("Min:" + cs.getMin());

	}

}

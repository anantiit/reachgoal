package com.csfundamentals.queues;

import java.util.Iterator;
import java.util.PriorityQueue;

/*
 * Given a string of lowercase alphabets and a number k, the task is to print the minimum value of the string after removal of ‘k’ characters. 
 * The value of a string is defined as the sum of squares of the count of each distinct character.
 * For example consider the string “saideep”, here frequencies of characters are s-1, a-1, i-1, e-2, d-1, p-1 and 
 * value of the string is 1^2 + 1^2 + 1^2 + 1^2 + 1^2 + 2^2 = 9.
Expected Time Complexity: O(k*logn)
Examples: 
 

Input :  str = abccc, K = 1
Output :  6
We remove c to get the value as 12 + 12 + 22

Input :  str = aaab, K = 2
Output :  2
Asked In : Amazon
 
*/
public class MaxFrequency {
	public static int maxFrequencyCharRemoval(String s, int k) {
		PriorityQueue<CharFreq> charQ = new PriorityQueue<CharFreq>();
		char[] charArr = s.toCharArray();
		int[] freqArr = new int[26];
		for (int i = 0; i < charArr.length; i++) {
			// System.out.println('a' - 97);
			freqArr[charArr[i] - 97]++;
		}
		for (int i = 0; i < 26; i++) {
			if (freqArr[i] != 0)
				charQ.add(new CharFreq((char) (i + 97), freqArr[i]));
		}
		int freqSquareSum = 0;
		System.out.println(charQ);
		for (int j = 0; j < k; j++) {
			// Note : if we just peek() then the max/min priority queue does not adjust to
			// keep the latest max/min at the top.
			CharFreq cf = charQ.poll();
			cf.frequency--;
			charQ.add(cf);
			System.out.println(charQ);

		}
		System.out.println(charQ);
		Iterator<CharFreq> itr1 = charQ.iterator();
		while (itr1.hasNext()) {
			CharFreq cf = itr1.next();
			freqSquareSum = freqSquareSum + cf.frequency * cf.frequency;

		}

		return freqSquareSum;
	}

	public static void main(String args[]) {
		String s = "abbbbcccccddeeeee";
		System.out.println(maxFrequencyCharRemoval(s, 3));
	}

}

class CharFreq implements Comparable<CharFreq> {
	int frequency;
	char c;

	public CharFreq(char c, int freq) {
		// TODO Auto-generated constructor stub
		this.c = c;
		this.frequency = freq;
	}

	@Override
	public int compareTo(CharFreq o) {
		// TODO Auto-generated method stub
		if (this.frequency == o.frequency)
			return 0;
		else if (this.frequency < o.frequency)
			return 1;
		else
			return -1;
	}

	@Override
	public String toString() {
		return "CharFreq [frequency=" + frequency + ", c=" + c + "]";
	}

}

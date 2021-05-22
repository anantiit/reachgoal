package com.reachgoal.problemsolving;

/*
 * 678 12345 given sorted rotated array find the element given.
 * bs(int[] , int i,int searchElement) 
 * 
 * int heightMountains[] = {3,0,4};
 * [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1]
 *1+1 
 *
 *area(i,j) = 
 *
 *
 *
 *
 * 
 * 
 */
public class SortedRotatedArray {
	public static int binaraySearchForAnamolyPoint(int a[], int s, int e) {
		if (s < 0 || e > a.length - 1 || s > e) {
			return -1;
		}
		int i = (s + e) / 2;
		if (i == e || a[i] > a[i + 1]) {
			return i;
		}
		if (a[0] > a[i]) {
			return binaraySearchForAnamolyPoint(a, s, i - 1);
		} else {
			return binaraySearchForAnamolyPoint(a, i + 1, e);
		}
	}

	public static int binaraySearch(int a[], int s, int e, int key) {
		if (s > e || s < 0 || e > a.length - 1) {
			return -1;
		}
		if (s == e && a[s] != key) {
			return -1;
		}
		int i = (s + e) / 2;
		if (a[i] == key) {
			return i;
		}
		if (a[i] > key) {
			return binaraySearch(a, s, i - 1, key);
		} else {
			return binaraySearch(a, i + 1, e, key);
		}
	}

	public static int searchRotatedArray(int a[], int key) {
		int s = 0;
		int e = a.length - 1;
		int midPoint = binaraySearchForAnamolyPoint(a, s, e);
		if (midPoint != -1 && a[midPoint] == key)
			return key;
		int foundIndex = -1;
		if (a[0] < key) {
			foundIndex = binaraySearch(a, s, midPoint, key);
		} else {
			foundIndex = binaraySearch(a, midPoint + 1, e, key);
		}
		return foundIndex;
	}

	public static void main(String args[]) {
		int a[] = { 1, 2, 3, 4, 5, 6, 7, 8 };
		int key = 2;
		System.out.println(searchRotatedArray(a, key));
	}

}

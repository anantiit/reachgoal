package com.csfundamentals.stack;

/*
You are given an integer array prices where prices[i] is the price of a given stock on the ith day.

On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.

Find and return the maximum profit you can achieve.

 

Example 1:

Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit is 4 + 3 = 7.
Example 2:

Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Total profit is 4.
Example 3:

Input: prices = [7,6,4,3,1]
Output: 0
Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.
*/
/*
 * Solution: we are fallowing mountain peak model here we buy at start if there is ascending order and sell only at the peak and buy again at the bottom start of the next mountain
 */
public class BuySellStock {
	public static int buySellStock(int[] a) {
		int maxProfit = 0;
		boolean baught = false;
		for (int i = 0; i < a.length - 1; i++) {
			if (a[i] < a[i + 1]) {
				if (!baught) {
					System.out.println("Buying at " + a[i]);
					baught = true;
				}
				maxProfit += a[i + 1] - a[i];
			} else if (baught) {
				System.out.println("Selling at " + a[i]);
				baught = false;
			}
		}
		if (baught) {
			System.out.println("Selling at " + a[a.length - 1]);
		}
		return maxProfit;
	}

	public static void main(String args[]) {
		int[] prices = { 1, 2, 3, 4, 5 };
		int[] prices1 = { 7, 7, 1, 5, 3, 6, 4 };
		System.out.println(buySellStock(prices1));
	}

}

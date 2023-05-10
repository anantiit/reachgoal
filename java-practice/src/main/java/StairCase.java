public class StairCase {
	static int possibleSteps(int n) {
		int[] dp = new int[n + 1];
		dp[0] = 1;
		for (int i = 1; i <= n; i++) {
			if (i - 2 >= 0)
				dp[i] = dp[i - 1] + dp[i - 2];
			else
				dp[i] = dp[i - 1];
		}
		// System.out.println(Arrays.toString(dp));
		return dp[n];
	}

	public static void main(String[] args) {
		// System.out.println(possibleSteps(5));
		int[] price = { 7, 1, 5, 3, 6, 4 };
		System.out.println(maxProfitStocks(price));
	}

	static int maxProfitStocks(int[] price) {
		int profit = 0;
		int n = price.length;
		boolean isBaught = false;
		for (int i = 0; i < n - 2; i++) {
			if (price[i] < price[i + 1]) {
				if (!isBaught) {
					System.out.println("Buying at" + price[i]);
					isBaught = true;
				}
				profit += (price[i + 1] - price[i]);
			} else {
				if (isBaught) {
					System.out.println("Selling at" + price[i]);
					isBaught = false;
				}
			}
		}
		if (isBaught) {
			System.out.println("Selling at" + price[n - 2]);
		}
		return profit;
	}
}
/*
 * 3 -> 2,1 1,2 1,1,1
 * 
 * 4 -> 2,1,1 1,2,1 1,1,1,1 2,2 1,1,2
 */

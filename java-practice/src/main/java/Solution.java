class Solution {
	public String longestPalindrome(String s) {
		if (s.length() == 1) {
			return s;
		}
		return extracted(s, 1);
	}

	private String extracted(String s, int minLength) {
		if (isPalindrome(s)) {
			return s;
		}
		String s2 = "";
		String s1 = "";
		if (s.length() > minLength)
			s1 = extracted(s.substring(0, s.length() - 1), minLength);
		if (s1.length() < minLength) {
			s2 = extracted(s.substring(1), minLength);
		}
		if (s1.length() > s2.length()) {
			return s1;
		}
		return s2;
	}

	private boolean isPalindrome(String s) {
		int left = 0;
		int right = s.length() - 1;
		while (left < right) {
			if (s.charAt(left) != s.charAt(right)) {
				return false;
			}
			left++;
			right--;
		}
		return true;
	}

	public boolean check(String s, int i, int j) {
		while (i < j) {
			if (s.charAt(i) != s.charAt(j)) {
				return false;
			}
			i++;
			j--;
		}
		return true;
	}

	public String longestPalindromeIterative(String s) {
		int x = 123;
		int n = s.length();
		for (int i = n - 1; i >= 0; i--) {
			for (int j = 0; j < i; j++) {
				if (check(s, j, i)) {
					return s.substring(j, i + 1);
				}
			}
		}
		return s.substring(0, 1);
	}

	public int reverse(int x) {
		int y = 0;
		boolean isNeg = false;
		if (x < 0) {
			isNeg = true;
			x = -x;
		}
		while (x > 0) {
			int temp = x % 10;
			x = x / 10;
			if (y > y * 10) {
				return 0;
			}
			y = y * 10 + temp;
		}
		if (isNeg) {
			return -y;
		}
		return y;
	}

	public int myAtoi(String s) {
		s = s.trim();
		boolean isNegative = false;
		int num = 0;
		int i = 0;

		if (s.length() == 0) {
			return 0;
		}

		if (s.charAt(0) == '-') {
			isNegative = true;
			i++;
		} else if (s.charAt(0) == '+') {
			i++;
		}

		for (; i < s.length(); i++) {
			char c = s.charAt(i);
			if (!Character.isDigit(c)) {
				break;
			}
			int digit = Character.getNumericValue(c);
			if (num > (Integer.MAX_VALUE - digit) / 10) {
				return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
			}
			num = num * 10 + digit;
		}

		return isNegative ? -num : num;
	}

	public static void main(String args[]) {
		Solution s = new Solution();
		System.out.println(s.longestPalindromeIterative("babad"));
		System.out.println(s.reverse(1534236469));
	}
}
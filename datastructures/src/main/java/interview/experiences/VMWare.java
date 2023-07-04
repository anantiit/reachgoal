package interview.experiences;
import java.util.Random;

public class VMWare {
	// Problem 1
	// 0101011101010 - 00000011111111

	public static void sortOs1s(String str) {
		char[] charArr = str.toCharArray();
		int onesCount = 0;
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i] == '1') {
				onesCount++;
			}
		}
		for (int i = 0; i < charArr.length; i++) {
			if (i >= onesCount) {
				charArr[i] = '1';
			} else {
				charArr[i] = '0';
			}
		}
	}

	public static void sortOs1s(char[] a) {
		int left = 0;
		int right = a.length - 1;
		while (left < right) {
			if (a[left] == '1') {
				if (a[right] == '0') {
					swap(a, left, right);
					right--;
				}
			}
			left++;
			if (a[right] == '0') {
				if (a[left] == '1') {
					swap(a, left, right);
					left++;
				}
			}
			right--;
		}
	}

	private static void swap(char[] a, int i, int j) {
		char temp = a[i];
		a[i] = a[j];
		a[j] = temp;

	}

	// Problem 2
	// Random Number Generator
	public static String otpGenerator() {
		Random r = new Random();
		StringBuffer otp = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			otp.append(r.nextInt(9));
		}
		return otp.toString();
	}

	public static String otpGenerate() {
		long time = System.nanoTime();
		String timeStr = time + "";
		int n = timeStr.length();
		String otp = timeStr.substring(n - 5, n - 1);
		return otp;
	}

	// Problem 3
	// Equilibrium point where left sum == right sum
	// [-7, 1, 5, 2, -4, 3, 0]
	public static int equilibriumPoint(int[] a) {
		int n = a.length;
		int totalSum = 0;
		for (int k = 0; k < n; k++) {
			totalSum += a[k];
		}
		int leftSum = 0;
		int i = 0;
		leftSum = leftSum + a[0];
		totalSum = totalSum - a[0];
		i++;
		while (leftSum != totalSum && i < n) {
			leftSum = leftSum + a[i];
			totalSum = totalSum - a[i];
			if (leftSum == totalSum) {
				return i;
			}
			i++;
		}
		return -i;
	}

	public static void main(String[] args) {
		int[] a = { -7, 1, 5, -4, 3, 0 };
		System.out.println(equilibriumPoint(a));
		int i = 0;
		while (i < 10) {
			System.out.println(otpGenerate());
			i++;
		}
		System.out.println("-----------------");
		i = 0;
		while (i < 10) {
			System.out.println(otpGenerator());
			i++;
		}
		String str = "0101011101010";
		char[] strArr = str.toCharArray();
		System.out.println(strArr);
		sortOs1s(strArr);
		System.out.println(strArr);
	}

}

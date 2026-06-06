
public class SimpleJavaPattern {
	public static void main(String args[]) {
		int n = 5;
		printPattern(n);
	}

	/*
	 * + ++ +++ ++++ +++++ ++++ +++ ++ +
	 */
	public static void printPattern(int n) {
		int j = 1;
		for (int i = 1; i <= 2 * n - 1; i++) {
			if (i <= n) {
				j = i;
			} else {
				j = 2 * n - i;
			}

			while (j > 0) {
				System.out.print("+");
				j--;
			}
			System.out.println();
		}
	}

}

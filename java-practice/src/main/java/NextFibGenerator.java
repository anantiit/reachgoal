import java.util.ArrayList;
import java.util.List;

public class NextFibGenerator {

	public static List<Long> fibArray = new ArrayList<Long>();

	public static long fib(int n) {
		if (n == 0 || n == 1) {
			if (fibArray.size() < 2) {
				fibArray.add(0, 1l);
				fibArray.add(1, 1l);
			}
			return 1;
		}
		if (fibArray.size() >= n + 1 && fibArray.get(n) != null) {
			return fibArray.get(n);
		}
		long fibN = fib(n - 2) + fib(n - 1);
		if (fibN < 0l) {
			fibN = -1;
		}
		fibArray.add(n, fibN);
		return fibN;
	}

	public static long nextFib(int n) {

		int l = 0;
		int r = n;
		int mid = l + (r - l) / 2;
		if (fibArray.size() <= mid || fibArray.get(mid) == null) {
			fib(n);
		}
		while (l <= r) {
			mid = l + (r - l) / 2;
			if (mid <= n && mid > 0 && fibArray.get(mid) >= n && fibArray.get(mid - 1) <= n
					&& fibArray.get(mid - 1) > 0) {
				if (fibArray.get(mid - 1) == n) {
					return fibArray.get(mid - 1);
				}
				return fibArray.get(mid);
			}
			if (fibArray.get(mid) < n) {
				l = mid + 1;
			} else {
				r = mid;
			}

		}
		return fibArray.get(mid);

	}

	public static void main(String args[]) {
		// System.out.println(nextFib(6));
		System.out.println(nextFib(35));
	}
}
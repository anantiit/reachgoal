package interview.experiences;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/*
 * 
 * Given two strings X and Y of lengths m and n respectively, find the length of the smallest string which has both, X and Y as its sub-sequences.

Input: X = abcd, Y = xycd Output: 6 xyabcd, abxycd

Input: X = efgh, Y = jghi Output: 6 efjghi

Input: X = efgha, Y = jghi Output: 6 efjghia ->  lcs+ max(x.size()-2, y.size()-2)

lcs[i][j] = lcs[i-1][j-1]+1 if(x[i])
  max(lcs[i][j-1],)

Expected Time Complexity: O(Length(X) * Length(Y)).
Expected Auxiliary Space: O(Length(X) * Length(Y)).


if there is no constraint
 */
public class FreshWorks {
	static Map<Character, HashSet<Character>> knightOnPhoneDialMoves = new HashMap<Character, HashSet<Character>>();
	static {
		knightOnPhoneDialMoves.put('0', new HashSet<>(Set.of('4', '6')));
		knightOnPhoneDialMoves.put('1', new HashSet<>(Set.of('8', '6')));
		knightOnPhoneDialMoves.put('2', new HashSet<>(Set.of('7', '9')));
		knightOnPhoneDialMoves.put('3', new HashSet<>(Set.of('8', '4')));
		knightOnPhoneDialMoves.put('4', new HashSet<>(Set.of('9', '3', '0')));
		knightOnPhoneDialMoves.put('6', new HashSet<>(Set.of('0', '1', '7')));
		knightOnPhoneDialMoves.put('7', new HashSet<>(Set.of('6', '2')));
		knightOnPhoneDialMoves.put('8', new HashSet<>(Set.of('1', '3')));
		knightOnPhoneDialMoves.put('9', new HashSet<>(Set.of('2', '4')));
	}

	public static int shortestCommonSupersequence(String X, String Y) {
		int m = X.length();
		int n = Y.length();

		// Create a 2D table to store the lengths of the shortest common supersequence
		int[][] dp = new int[m + 1][n + 1];

		// Fill the table using dynamic programming
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0 || j == 0) {
					dp[i][j] = i + j;
				} else if (X.charAt(i - 1) == Y.charAt(j - 1)) {
					dp[i][j] = 1 + dp[i - 1][j - 1];
				} else {
					// observe we are adding 1 even if there is no match as it need to contain this
					// character
					dp[i][j] = 1 + Math.min(dp[i - 1][j], dp[i][j - 1]);
				}
			}
		}

		// The length of the smallest string with X and Y as subsequences is stored at
		// dp[m][n]
		return dp[m][n];
	}

	public static int longestCommonSubSequence(String x, String y) {
		int m = x.length() + 1;
		int n = y.length() + 1;
		int[][] lcs = new int[m][n];
		for (int i = 0; i < m; i++) {
			lcs[i][0] = 0;
		}
		for (int j = 0; j < n; j++) {
			lcs[0][j] = 0;
		}
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (x.charAt(i - 1) == y.charAt(j - 1)) {
					lcs[i][j] = lcs[i - 1][j - 1] + 1;
				} else {
					lcs[i][j] = Math.max(lcs[i - 1][j], lcs[i][j - 1]);
				}
			}
		}
		return lcs[m - 1][n - 1];
	}

	public static int lengthOfStringForWhichXAndYAreSubsequences(String x, String y) {
		int lcs = longestCommonSubSequence(x, y);
		return x.length() + y.length() - lcs;
	}

	public static void main(String args[]) {
		String X = "efgha";
		String Y = "xycd";
		String X1 = "AGGTAB";
		String Y1 = "GXTXAYB";
		System.out.println(lengthOfStringForWhichXAndYAreSubsequences(X1, Y1));
		int length = shortestCommonSupersequence(X1, Y1);
		System.out.println("Length of the smallest string with X1 and Y1 as subsequences: " + length);

		// System.out.println(getUniqueNumbers('0', 2));
		System.out.println(getUniqueNumbersUsingDFS('4', 3));
	}

	/*
	 * This is your normal telephone dial pad. //1 2 3 //4 5 6 //7 8 9 // 0
	 * 
	 * write a function that returns the number of unique phone numbers that a
	 * Knight (chess piece which moves in L shape) can dial given the starting and
	 * the number of hops allowed
	 * 
	 * getUniqueNumbers(start(Int), hops(Int)) => Int[]
	 * 
	 * for example
	 * 
	 * getUniqueNumbers(0, 1) => [ 04, 06 ]
	 * 
	 * with the knight starting at 0 and allowed only one hop, it can either hop to
	 * 4 or 6 and nothing else. this is for two hops getUniqueNumbers(0, 2) => [
	 * 040, 043, 049, 060, 061, 067 ]
	 */

	public static Set<String> getUniqueNumbers(char start, int hopsCount) {
		Set<String> uniqueNumbers = new HashSet<String>();
		Queue<Character> queue = new LinkedList<Character>();
		uniqueNumbers.add(start + "");
		queue.addAll(knightOnPhoneDialMoves.get(start));
		hopsCount--;
		while (!queue.isEmpty() && hopsCount >= 0) {
			int levelSize = queue.size();
			Set<String> curSet = new HashSet<String>();
			while (levelSize > 0) {
				char nextChar = queue.poll();
				levelSize--;
				Set<Character> set = knightOnPhoneDialMoves.get(nextChar);
				curSet.addAll(uniqueNumbers.stream().filter(k -> set.contains(k.charAt(k.length() - 1)))
						.map(k -> k + nextChar).toList());
				queue.addAll(set);
			}
			hopsCount--;
			uniqueNumbers = curSet;
		}
		return uniqueNumbers;

	}

	public static Set<String> getUniqueNumbersUsingDFS(char start, int hopsCount) {
		Set<String> uniqueNumbers = new HashSet<String>();
		Stack<PhoneDialNum> stack = new Stack<PhoneDialNum>();
		stack.push(new PhoneDialNum(start, start + ""));
		while (!stack.isEmpty()) {
			PhoneDialNum phoneDialNum = stack.pop();
			if (phoneDialNum.path.length() >= hopsCount + 1) {
				uniqueNumbers.add(phoneDialNum.path);
			} else {
				Set<Character> set = knightOnPhoneDialMoves.get(phoneDialNum.getDialNum());
				Iterator<Character> itr = set.iterator();
				while (itr.hasNext()) {
					char cur = itr.next();
					stack.push(new PhoneDialNum(cur, phoneDialNum.path + cur));
				}
			}
		}
		return uniqueNumbers;
	}

}

@AllArgsConstructor
@Setter
@Getter
class PhoneDialNum {
	char dialNum;
	String path;
}
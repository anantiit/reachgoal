package interview.experiences;

import java.util.HashMap;
import java.util.Map;
/*
Given heights at which balloons are placed. 
You get to shoot an arrow that goes from (L -> R), starting at any height H. 
If height of arrow = height of balloon, then the balloon pops and the height of arrow drops by 1.
Minimum number of arrows required to pop all balloons? 

[9, 8, 7, 4, 3, 6, 5,1] 

O/P - 2
*/

public class HevoData {
	public static int popBallons(int[] height) {
		Map<Integer, Integer> lastHitMap = new HashMap<>();
		int arrowCount = 0;
		int n = height.length;
		for (int i = 0; i < n; i++) {
			int curHeight = height[i];
			if (lastHitMap.containsKey(curHeight + 1)) {
				System.out.println(lastHitMap);
				lastHitMap.computeIfPresent(curHeight + 1, (k, v) -> (v == 1) ? null : v - 1);
				lastHitMap.compute(curHeight, (k, v) -> (v == null) ? 1 : v + 1);
			} else {
				System.out.println(lastHitMap);
				arrowCount++;
				lastHitMap.compute(curHeight, (k, v) -> (v == null) ? 1 : v + 1);

			}
		}
		return arrowCount;

	}

	public static void main(String[] args) {
		int[] ballon = { 9, 8, 9, 8, 7, 7 };
		int[] ballon1 = { 3, 3, 2, 2, 1, 1 };
		System.out.println(popBallons(ballon1));
	}
}

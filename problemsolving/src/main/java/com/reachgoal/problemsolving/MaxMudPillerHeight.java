package com.reachgoal.problemsolving;

import java.util.ArrayList;
import java.util.List;

/*
 * given the 2 arrays 1. position concrete pillars and 2. the height of the corresponding pillars, mud pillars are filled to reduce the project cost. 
 * But the condition to fill the mud pillar is it should be of height maximum +1 unit of the adjacent heights.
 * given the above problem need to find the maximum size of the mud pillar filled 
 */
public class MaxMudPillerHeight {
	public static void main(String args[]) {
		List<Integer> pillarPos = new ArrayList<Integer>();
		pillarPos.add(1);
		pillarPos.add(3);
		pillarPos.add(7);
		List<Integer> pillarHeight = new ArrayList<Integer>();
		pillarHeight.add(4);
		pillarHeight.add(3);
		pillarHeight.add(7);
		System.out.println(maxMudPillar(pillarPos, pillarHeight));
	}

	public static int maxMudPillar(List<Integer> pillarPos, List<Integer> pillarHeight) {
		int maxMudPillarHeight = -1;
		for (int i = 0; i < pillarPos.size() - 1; i++) {
			int curPos = pillarPos.get(i);
			int nextPos = pillarPos.get(i + 1);
			int curHeight = pillarHeight.get(i);
			int nextHeight = pillarHeight.get(i + 1);
			int diffPosition = nextPos - curPos - 1;
			int leftHeight = curHeight;
			int rightHeight = nextHeight;
			while (diffPosition > 0) {
				if (leftHeight >= rightHeight) {
					rightHeight = ++rightHeight;
					if (maxMudPillarHeight < rightHeight) {
						maxMudPillarHeight = rightHeight;
					}
				} else {
					leftHeight = ++leftHeight;
					if (maxMudPillarHeight < leftHeight) {
						maxMudPillarHeight = leftHeight;
					}
				}
				diffPosition--;
				System.out.println("leftHeight:" + leftHeight + "rightHeight:" + rightHeight + "maxMudPillarHeight:"
						+ maxMudPillarHeight);
			}

		}
		return maxMudPillarHeight;
	}
}

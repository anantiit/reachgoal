package com.csfundamentals.algo.problemsolving;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/*

We are working on a security system for a badged-access room in our company's building.

We want to find employees who badged into our secured room unusually often. We have an unordered list of names and entry times over a single day. Access times are given as numbers up to four digits in length using 24-hour time, such as "800" or "2250".

Write a function that finds anyone who badged into the room three or more times in a one-hour period. Your function should return each of the employees who fit that criteria, plus the times that they badged in during the one-hour period. If there are multiple one-hour periods where this was true for an employee, just return the earliest one for that employee.


badge_times = [
  ["Paul",      "1355"], ["Jennifer",  "1910"], ["Jose",    "835"],
  ["Jose",       "830"], ["Paul",      "1315"], ["Chloe",     "0"],
  ["Chloe",     "1910"], ["Jose",      "1615"], ["Jose",   "1640"],
  ["Paul",      "1405"], ["Jose",       "855"], ["Jose",    "930"],
  ["Jose",       "915"], ["Jose",       "730"], ["Jose",    "940"],
  ["Jennifer",  "1335"], ["Jennifer",   "730"], ["Jose",   "1630"],
  ["Jennifer",     "5"], ["Chloe",     "1909"], ["Zhang",     "1"],
  ["Zhang",       "10"], ["Zhang",      "109"], ["Zhang",   "110"],
  ["Amos",         "1"], ["Amos",         "2"], ["Amos",    "400"],
  ["Amos",       "500"], ["Amos",       "503"], ["Amos",    "504"],
  ["Amos",       "601"], ["Amos",       "602"],
];


Expected output (in any order)
   Paul: 1315 1355 1405
   Jose: 830 835 855 915 925
   Zhang: 10 109 110
   Amos: 500 503 504

n: length of the badge records array


*/
public class WindowsWithInHour {
	public static void main(String[] argv) {

		String[][] badgeTimes = new String[][] { { "Paul", "1355" }, { "Jennifer", "1910" }, { "Jose", "835" },
				{ "Jose", "830" }, { "Paul", "1315" }, { "Chloe", "0" }, { "Chloe", "1910" }, { "Jose", "1615" },
				{ "Jose", "1640" }, { "Paul", "1405" }, { "Jose", "855" }, { "Jose", "930" }, { "Jose", "915" },
				{ "Jose", "730" }, { "Jose", "940" }, { "Jennifer", "1335" }, { "Jennifer", "730" }, { "Jose", "1630" },
				{ "Jennifer", "5" }, { "Chloe", "1909" }, { "Zhang", "1" }, { "Zhang", "10" }, { "Zhang", "109" },
				{ "Zhang", "110" }, { "Amos", "1" }, { "Amos", "0002" }, { "Amos", "400" }, { "Amos", "500" },
				{ "Amos", "503" }, { "Amos", "504" }, { "Amos", "601" }, { "Amos", "602" }, };
		findUsersWhoBadgedMore(badgeTimes);
	}

	public static void findUsersWhoBadgedMore(String[][] badgeTimes) {
		Map<String, TreeSet<Integer>> badges = new HashMap<String, TreeSet<Integer>>();
		Map<String, LinkedList<Integer>> result = new HashMap<String, LinkedList<Integer>>();
		for (int i = 0; i < badgeTimes.length; i++) {
			TreeSet<Integer> badgeTimeSet = badges.get(badgeTimes[i][0]);
			if (badgeTimeSet == null) {
				badgeTimeSet = new TreeSet<Integer>();
			}
			badgeTimeSet.add(Integer.parseInt(badgeTimes[i][1]));
			badges.put(badgeTimes[i][0], badgeTimeSet);
		}
		for (String name : badges.keySet()) {
			Set<Integer> badgeTimeSet = badges.get(name);
			LinkedList<Integer> badgesInHour = new LinkedList<Integer>();
			int timeDiff = 0;
			int prevTime = -1;
			for (Integer time : badgeTimeSet) {
				if (prevTime > 0) {
					timeDiff = timeDiff + (time - prevTime);
				}
				if (timeDiff > 100) {
					timeDiff = time - badgesInHour.removeFirst();
				}
				badgesInHour.add(time);
				prevTime = time;
			}
			if (badgesInHour.size() >= 3) {
				result.put(name, badgesInHour);
			}
		}
		System.out.println(result);
	}
}

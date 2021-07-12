package com.problemsolving.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.csfundamentals.core.TimeTime;

public class MinimumPlatformsInRailwayStation {
	public static void main(String[] args) {
		List<TimeTime> schedules = new ArrayList<TimeTime>();
		TimeTime t0 = new TimeTime(900, 910);
		TimeTime t1 = new TimeTime(940, 1200);
		TimeTime t2 = new TimeTime(950, 1120);
		TimeTime t3 = new TimeTime(1100, 1130);
		TimeTime t4 = new TimeTime(1500, 1900);
		TimeTime t5 = new TimeTime(1800, 2000);
//		Input: arr[] = {9:00, 9:40, 9:50, 11:00, 15:00, 18:00} ;
		// dep[] = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00} ;
		schedules.add(t0);
		schedules.add(t1);
		schedules.add(t2);
		schedules.add(t3);
		schedules.add(t4);
		schedules.add(t5);
		System.out.println(minimumPlatformsInRailwayStation(schedules));
	}

	public static int minimumPlatformsInRailwayStation(List<TimeTime> schedules) {
		Collections.sort(schedules, (a, b) -> a.start - b.start);
		int currentPlatformsRequired = 0;
		int maxPlatformsRequired = 0;
		List<TimeTime> haltedTrainsSchedules = new ArrayList<TimeTime>();
		for (TimeTime s : schedules) {
			System.out.println(haltedTrainsSchedules);
			Iterator<TimeTime> itr = haltedTrainsSchedules.iterator();
			while (itr.hasNext()) {
				TimeTime cur = itr.next();
				if (cur.end <= s.start) {
					itr.remove();
					currentPlatformsRequired--;
				} else {
					break;
				}
			}
			haltedTrainsSchedules.add(s);
			currentPlatformsRequired++;
			if (maxPlatformsRequired < currentPlatformsRequired) {
				maxPlatformsRequired = currentPlatformsRequired;
			}
		}
		return maxPlatformsRequired;
	}
}

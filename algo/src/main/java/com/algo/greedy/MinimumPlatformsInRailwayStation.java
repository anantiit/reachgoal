package com.algo.greedy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

import com.csfundamentals.core.TimeTime;

public class MinimumPlatformsInRailwayStation {
	public static void main(String[] args) {
		List<TimeTime> schedules = new ArrayList<TimeTime>();
		TimeTime t0 = new TimeTime(900, 940);
		TimeTime t1 = new TimeTime(940, 1200);
		TimeTime t2 = new TimeTime(950, 1120);
		TimeTime t6 = new TimeTime(960, 1120);
		TimeTime t3 = new TimeTime(1121, 1130);
		TimeTime t4 = new TimeTime(1500, 1900);
		TimeTime t5 = new TimeTime(1800, 2000);
		TimeTime t7 = new TimeTime(900, 910);
//		Input: arr[] = {9:00, 9:40, 9:50, 11:00, 15:00, 18:00} ;
		// dep[] = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00} ;
		schedules.add(t0);
		schedules.add(t1);
		schedules.add(t2);
		schedules.add(t3);
		schedules.add(t4);
		schedules.add(t5);
		schedules.add(t6);
		schedules.add(t7);

		int arr[] = { 900, 920, 940, 950, 1100, 1500, 1800 };
		int dep[] = { 950, 950, 1200, 1120, 1130, 1900, 2000 };

		int arr1[] = { 950, 920, 940, 950, 1100, 1500, 1800 };
		int dep1[] = { 950, 950, 1200, 1120, 1130, 1900, 2000 };

		int arr2[] = { 900, 950, 970 };
		int dep2[] = { 940, 960, 1200 };

		int arr3[] = { 900, 920, 930 };
		int dep3[] = { 940, 960, 1200 };
		int arr4[] = { 900, 940, 950, 1121, 1500, 1800, 960, 1121, 900 };
		int dep4[] = { 940, 1200, 1120, 1130, 1900, 2000, 1120, 1129, 910 };
		System.out.println(minimumPlatformsInRailwayStation(schedules));
		// System.out.println(minimumPlatformsInRailwayStation1(schedules));
	}

	public static int minimumPlatformsInRailwayStation(List<TimeTime> schedules) {
		Collections.sort(schedules, (a, b) -> a.start - b.start);
		int maxPlatformsRequired = 1;
		TimeTime firstSchedule = schedules.get(0);
		PriorityQueue<TimeTime> minHeap = new PriorityQueue<TimeTime>((a, b) -> a.end - b.end);
		minHeap.add(firstSchedule);
		int n = schedules.size();
		for (int i = 1; i < n; i++) {
			TimeTime currSchedule = schedules.get(i);
			TimeTime trainWithMinDeptTime = minHeap.peek();
			if (currSchedule.start >= trainWithMinDeptTime.end) {
				minHeap.poll();
				maxPlatformsRequired--;
			}
			minHeap.add(currSchedule);
			maxPlatformsRequired++;
			System.out.println(minHeap);
		}
		return maxPlatformsRequired;
	}
}
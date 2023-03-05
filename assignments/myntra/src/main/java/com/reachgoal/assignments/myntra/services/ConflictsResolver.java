package com.reachgoal.assignments.myntra.services;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.reachgoal.assignments.myntra.entities.ExecutiveSlot;

public class ConflictsResolver {

	public static boolean isConflicting(ExecutiveSlot slot1, ExecutiveSlot slot2) {
		int t0 = slot1.getStartTime();
		int t1 = slot1.getEndTime();

		if (slot2.getStartTime() <= t0 && slot2.getEndTime() > t0) {
			return true;
		}
		if (slot2.getStartTime() <= t1 && slot2.getEndTime() > t1) {
			return true;
		}
		if (slot2.getStartTime() >= t0 && slot2.getEndTime() <= t1) {
			return true;
		}
		return false;
	}

	private static Set<ExecutiveSlot> resolveConflicts(List<ExecutiveSlot> allExecutiveSlots) {
		Queue<ExecutiveSlot> maxHeap = populateMaxHeapOfExecutiveSlots(allExecutiveSlots);
		Set<ExecutiveSlot> resolvedSlots = new HashSet<ExecutiveSlot>();
		Iterator<ExecutiveSlot> iterator = maxHeap.iterator();
		while (iterator.hasNext()) {
			ExecutiveSlot nextExecSlot = iterator.next();
			Iterator<ExecutiveSlot> iterator2 = resolvedSlots.iterator();
			while (iterator2.hasNext()) {
				if (isConflicting(nextExecSlot, iterator2.next())) {
					break;
				}
			}
			if (!iterator2.hasNext()) {
				resolvedSlots.add(nextExecSlot);
			}
		}
		return resolvedSlots;
	}

	public static Queue<ExecutiveSlot> populateMaxHeapOfExecutiveSlots(List<ExecutiveSlot> allExecutiveSlots) {
		ResolutionComparator resolutionComparator = new ResolutionComparator();
		Queue<ExecutiveSlot> maxHeap = new PriorityQueue<ExecutiveSlot>(allExecutiveSlots.size(), resolutionComparator);
		for (ExecutiveSlot e : allExecutiveSlots) {
			maxHeap.add(e);
		}
		return maxHeap;
	}

}

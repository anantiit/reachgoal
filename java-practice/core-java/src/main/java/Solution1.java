import java.util.PriorityQueue;

/*
 * Given arrival and departure times of all trains that reach a railway station, the task is to find the minimum number of platforms
 *  required for the railway station so that no train waits.
 
Input:
arr[] = {9:00, 9:40, 9:50, 11:00, 15:00, 18:00}
dep[] = {9:10, 12:00, 11:20, 11:30, 19:00, 20:00}


Approach:

t1
t2
t3


 */

public class Solution1 {
	public static int minPlatformsRequired(int[] arr, int[] dep) {
		PriorityQueue<TimeTime> minHeap = new PriorityQueue<TimeTime>(
				(a, b) -> (a.end == b.end) ? a.start - b.start : a.end - b.end);
		minHeap.add(new TimeTime(arr[0], dep[0]));
		int minPlatformsReq = 1;
		for (int i = 1; i < arr.length; i++) {
			TimeTime prev = minHeap.peek();
			if (prev.end < arr[i]) {
				minHeap.poll();
				minPlatformsReq--;
			}
			minHeap.add(new TimeTime(arr[i], dep[i]));
			minPlatformsReq++;
			System.out.println(minHeap);
		}
		return minPlatformsReq;
	}

	public static void main(String args[]) {
		int arr[] = { 900, 920, 940, 950, 1100, 1500, 1800 };
		int dep[] = { 950, 950, 1200, 1120, 1130, 1900, 2000 };

		int arr1[] = { 950, 920, 940, 950, 1100, 1500, 1800 };
		int dep1[] = { 950, 950, 1200, 1120, 1130, 1900, 2000 };

		int arr2[] = { 900, 950, 970 };
		int dep2[] = { 940, 960, 1200 };

		int arr3[] = { 900, 920, 930 };
		int dep3[] = { 940, 960, 1200 };

		System.out.println(minPlatformsRequired(arr3, dep3));
	}

}

class TimeTime {
	int start;
	int end;

	public TimeTime(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public String toString() {
		return "TimeTime [start=" + start + ", end=" + end + "]";
	}

}

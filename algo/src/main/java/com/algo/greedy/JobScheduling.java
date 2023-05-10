package com.algo.greedy;

import java.util.ArrayList;
import java.util.Collections;

// Program to find the maximum profit
// job sequence from a given array
// of jobs with deadlines and profits

//Extend this if the number of workers are more which means at any point of time there can more number if Jobs which is equal to number of workers
public class JobScheduling {

	// Function to schedule the jobs take 2
	// arguments arraylist and no of jobs to schedule
	void printJobScheduling(ArrayList<Job> arr, int t) {
		// Length of array
		int n = arr.size();

		// Sort all jobs according to
		// decreasing order of profit
		Collections.sort(arr, (a, b) -> b.profit - a.profit);

		// To store result (Sequence of jobs)
		char result[] = new char[t];

		// Iterate through all given jobs
		for (int i = 0; i < n; i++) {
			// Find a free slot for this job
			// (Note that we start from the
			// last possible slot)
			for (int j = Math.min(t - 1, arr.get(i).deadline - 1); j >= 0; j--) {

				// Free slot found
				if (result[j] == '\u0000') {
					result[j] = arr.get(i).id;
					break;
				}
			}
		}

		// Print the sequence
		for (char jb : result) {
			System.out.print(jb + " ");
		}
		System.out.println();
	}

	// Driver code
	public static void main(String args[]) {
		ArrayList<Job> arr = new ArrayList<Job>();

		arr.add(new Job('a', 1, 2));
		arr.add(new Job('b', 2, 19));
//		arr.add(new Job('c', 2, 27));
//		arr.add(new Job('d', 1, 5));
//		arr.add(new Job('f', 1, 30));
//		arr.add(new Job('e', 3, 15));
//		arr.add(new Job('g', 4, 20));

		// Function call
		System.out.println("Following is maximum " + "profit sequence of jobs");

		JobScheduling jobS = new JobScheduling();

		// Calling function
		jobS.printJobScheduling(arr, 2);
	}
}

class Job {
	// Each job has a unique-id,
	// profit and deadline
	char id;
	int deadline, profit;

	// Constructors
	public Job() {
	}

	public Job(char id, int deadline, int profit) {
		this.id = id;
		this.deadline = deadline;
		this.profit = profit;
	}
}

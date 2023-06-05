package com.problemsolving.zfsreplicationschedule;

/*
 * @Problem Statement Online scheduling: To Device a closed, state less, online
 * scheduling algorithm for scheduling replication tasks over a specified time
 * frame in a best effort uniform distribution under the following conditions:
 * 1. Dynamically jobs get added to the same time frame 2. At any point of time
 * the allotted schedule is the best schedule that is possible with in that time
 * frame.
 * 
 * Replication tasks are done bwn primary and secondary env so that once the
 * primary is down the secondary/standby is ready with the latest data (one day
 * old if the given time frame is 24 hours).
 * 
 * 
 */
public class ZFSReplicationSchedule {
	public static int DEFAULT_TIME_FRAME = 12;// this should be the one which should communicated to client in SLA
												// service level agreement

	public static double zfsReplicationTime(int jobOrClientNumber) {
		String binaryRep = Integer.toBinaryString(jobOrClientNumber - 1);// jobOrClientNumber-1 as the schedule starts
																			// from 0 we are subtracting 1 from the
																			// actual job number
		System.out.println(binaryRep);
		double scheduleTime = 0d;
		for (int i = binaryRep.length() - 1; i >= 0; i--) {
			int positionOfBit = binaryRep.length() - i; // Note positionOfBit runs from 1 to binaryRep.length()
			if (binaryRep.charAt(i) == '1') {
				scheduleTime = scheduleTime + DEFAULT_TIME_FRAME / Math.pow(2, positionOfBit);
			}
		}
		return scheduleTime;
	}

	public static void main(String args[]) {
		System.out.println(zfsReplicationTime(1));
	}
}
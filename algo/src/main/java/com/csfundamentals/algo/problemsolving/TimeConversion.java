package com.csfundamentals.algo.problemsolving;

public class TimeConversion {
	public static void main(String[] args) {
		String istDateString = "2022-12-31 11:59:01 PM IST";
		String pstDateString = convertToPST(istDateString);
		System.out.println("PST Date: " + pstDateString);
	}

	public static String convertToPST(String istDateString) {
		int year = Integer.parseInt(istDateString.substring(0, 4));
		int month = Integer.parseInt(istDateString.substring(5, 7));
		int day = Integer.parseInt(istDateString.substring(8, 10));
		int hour = Integer.parseInt(istDateString.substring(11, 13));
		int minute = Integer.parseInt(istDateString.substring(14, 16));
		int second = Integer.parseInt(istDateString.substring(17, 19));

		// Assuming "IST" represents Indian Standard Time
		int istOffset = 5 * 60 * 60 * 1000; // IST offset in milliseconds (5 hours ahead of UTC)
		int pstOffset = -8 * 60 * 60 * 1000; // PST offset in milliseconds (8 hours behind UTC)

		long istTimestamp = getTimestamp(year, month, day, hour, minute, second);
		System.out.println("Epoch:" + (istTimestamp - istOffset));
		long pstTimestamp = istTimestamp - istOffset + pstOffset;

		return formatPSTDate(pstTimestamp);
	}

	public static long getTimestamp(int year, int month, int day, int hour, int minute, int second) {
		// Calculating the timestamp manually
		long timestamp = 0;
		timestamp += (((long) year - 1970) * 365 + countLeapYears(1970, year)) * 24 * 60 * 60 * 1000;
		timestamp += ((long) month - 1) * getDaysInMonth(year, month);
		// 30 * 24 * 60 * 60 * 1000; // Assuming 30 days per month
		timestamp += ((long) day - 1) * 24 * 60 * 60 * 1000;
		timestamp += (long) hour * 60 * 60 * 1000;
		timestamp += (long) minute * 60 * 1000;
		timestamp += (long) second * 1000;
		return timestamp;
	}

	public static int countLeapYears(int startYear, int endYear) {
		int count = 0;
		for (int year = startYear; year < endYear; year++) {
			if (isLeapYear(year)) {
				count++;
			}
		}
		return count;
	}

	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}

	public static String formatPSTDate(long pstTimestamp) {
		// int pstOffset = -8 * 60 * 60 * 1000; // PST offset in milliseconds (8 hours
		// behind UTC)

		// long pstTime = pstTimestamp + pstOffset;

		int year = 1970;
		int month = 1;
		int day = 1;
		int hour = 0;
		int minute = 0;
		int second = 0;

		while (pstTimestamp >= 24 * 60 * 60 * 1000) {
			pstTimestamp -= 24 * 60 * 60 * 1000;
			day++;
		}
		while (day > getDaysInMonth(year, month)) {
			day -= getDaysInMonth(year, month);
			month++;

			if (month > 12) {
				month = 1;
				year++;
			}
		}
		while (pstTimestamp >= 1000) {
			pstTimestamp -= 1000;
			second++;
			if (second > 60) {
				second -= 60;
				minute++;
				if (minute > 60) {
					minute -= 60;
					hour++;
				}

			}
		}

		String pstDateString = String.format("%04d-%02d-%02d %02d:%02d:%02d %s PST", year, month, day,
				(hour > 13) ? hour - 12 : hour, minute, second, (hour > 13) ? "PM" : "AM");
		return pstDateString;
	}

	public static int getDaysInMonth(int year, int month) {
		switch (month) {
		case 2:
			return isLeapYear(year) ? 29 : 28;
		case 4:
		case 6:
		case 9:
		case 11:
			return 30;
		default:
			return 31;
		}
	}
}

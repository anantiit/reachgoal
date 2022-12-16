package com.csfundamentals.algo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Bill for credit card : due date 
 * currentdate-duedate
 * 
 * 
 * 
 * 
 */
public class Test6 {

	public static String DATE_FORMAT = "dd-MM-yyyy";
	public static String TIME_ZOME = "America/NewYork";
	public static int MILLIS_TO_SEC = 1000;

	public static int differenceFromDueDate(String date) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			Date dueDate = dateFormat.parse(date);
			// final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(TIME_ZOME));
			// cal.setTime(date);
			dueDate.getTime();
			long timeDiff = System.currentTimeMillis() / MILLIS_TO_SEC - dueDate.getTime() / MILLIS_TO_SEC;
			System.out.println(timeDiff);
			int daysDiff = (int) (timeDiff / 86400);
			return Math.abs(daysDiff);
		} catch (ParseException pe) {
			System.out.println("Error parsing date" + date + pe.getMessage());
			return 0;
		}
	}

	public static void main(String args[]) {
		System.out.println(differenceFromDueDate("29-06-2021"));
	}

}

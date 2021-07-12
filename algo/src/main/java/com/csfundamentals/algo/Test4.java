package com.csfundamentals.algo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test4 {
	public static int solution(int A, int B) {
		// write your code in Java SE 8
		int start = (int) Math.sqrt((double) A);
		int end = (int) Math.sqrt((double) B) + 1;
		int count = 0;
		for (int i = start; i <= end; i++) {
			int temp = i * i + 1;
			if (temp >= A && temp <= B) {
				count++;
			}
		}
		return count;
	}

	public static int MAX_SIZE = 14 * 2 ^ 20;
	public static int MSIZE = 2 ^ 20;
	public static int KSIZE = 2 ^ 10;
	public static int GSIZE = 2 ^ 30;
	public static final String DEFAULT_DATE_FORMAT = "yyyy-mm-dd";

	public String solution(String S) {
		// write your code in Java SE 8
		String[] lines = S.split(System.getProperty("line.separator"));
		int minimumLength = Integer.MAX_VALUE;
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].endsWith("~")) {
				String[] fileDetails = lines[i].split(" ");

				String fileSizeStr = fileDetails[1];
				String fileName = fileDetails[3];
				String fileDate = fileDetails[2];

				fileSizeStr = "715K";
				fileDate = "2009-09-23";
				// 1990-01-31
				fileName = "system.zip~";
				System.out.println(fileSizeStr + " " + fileDate + " " + fileName);
				if (null != fileName && fileName.length() > 0) {
					int endIndex = fileName.lastIndexOf("\\.");
					if (endIndex != -1) {
						fileName = fileName.substring(0, endIndex); // not forgot to put check if(endIndex != -1)
					}
				}
				System.out.println(fileName);
				if (minimumLength > fileName.length() + 1
						&& convertFileSizeToIntegerAndCheckifLess(fileSizeStr, MAX_SIZE) && checkDateAbove(fileDate)) {
					minimumLength = fileName.length() + 1;
				}
			}
		}
		// System.out.println("lines size"+lines.length+":"+Arrays.toString(lines));
		if (minimumLength == Integer.MAX_VALUE) {
			return "NO FILES";
		}

		return minimumLength + "";
	}

	public static boolean convertFileSizeToIntegerAndCheckifLess(String fileSizeStr, int maxSize) {
		System.out.println("file size" + fileSizeStr);
		int fileSize = Integer.MAX_VALUE;
		if (fileSizeStr.endsWith("0") || fileSizeStr.endsWith("1") || fileSizeStr.endsWith("2")
				|| fileSizeStr.endsWith("3") || fileSizeStr.endsWith("4") || fileSizeStr.endsWith("5")
				|| fileSizeStr.endsWith("6") || fileSizeStr.endsWith("7") || fileSizeStr.endsWith("8")
				|| fileSizeStr.endsWith("9")) {
			fileSize = Integer.parseInt(fileSizeStr);
		} else if (fileSizeStr.endsWith("K")) {
			fileSize = Integer.parseInt(fileSizeStr.split("K")[0]);
			fileSize = fileSize * KSIZE;

		} else if (fileSizeStr.endsWith("M")) {
			fileSize = Integer.parseInt(fileSizeStr.split("M")[0]);
			fileSize = fileSize * MSIZE;
		} else if (fileSizeStr.endsWith("G")) {
			fileSize = Integer.parseInt(fileSizeStr.split("G")[0]);
			fileSize = fileSize * GSIZE;
			// integer roll over
			if (fileSize < 0) {
				return false;
			}
		}
		if (fileSize <= maxSize) {
			return true;
		}
		return false;
	}

	private static int getEpochFromDate(String dateStr) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		try {
			Date date = dateFormat.parse(dateStr);
			return (int) (date.getTime() / 1000l);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void main(String args[]) {
		// solution(6, 20);
		System.out.println(getEpochFromDate("1990-01-31"));
	}
}

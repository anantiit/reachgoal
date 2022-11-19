package com.algo.naidu.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;

public class DateUtils {
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final long MILLI_SECONDS_IN_DAY = 86400 * 1000l;

	private static long getEpochFromDateWithTimeZone(String date, String format, String timeZone) {
		if (StringUtils.isBlank(format)) {
			format = DEFAULT_DATE_FORMAT;
		}
		DateFormat sourceFormat = new SimpleDateFormat(format);
		Date dateinGivenTimeZone = null;
		try {
			TimeZone tz = null;
			if (StringUtils.isBlank(timeZone)) {
				System.out.println("invalid timezone  {} using utc instead" + timeZone);
				tz = TimeZone.getTimeZone("UTC");
			}
			tz = TimeZone.getTimeZone(timeZone);
			sourceFormat.setTimeZone(tz);

			dateinGivenTimeZone = sourceFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long epochTime = dateinGivenTimeZone.getTime();
		System.out.println(dateinGivenTimeZone.getTime());
		return epochTime;
	}

	public static Date getDateInGMT(String date, String format, String timeZone) throws ParseException {
		long epochTime = getEpochFromDateWithTimeZone(date, format, timeZone);
		System.out.println("Epoch Time:" + epochTime);
		LocalDateTime dateInGMT = Instant.ofEpochMilli(epochTime).atZone(ZoneId.of("GMT")).toLocalDateTime();
		Date out = Date.from(dateInGMT.atZone(ZoneId.of("GMT")).toInstant());
		System.out.println("Date:" + out);
		return null;
	}

	public static Date getNextDayDateInGMT(String date, String format, String timeZone) throws ParseException {
		long epochTime = getEpochFromDateWithTimeZone(date, format, timeZone);
		epochTime = epochTime + MILLI_SECONDS_IN_DAY;
		return null;
	}

	public static void main(String[] args) throws ParseException {
		System.out.println(getDateInGMT("2022-01-21", null, "Asia/Kolkata"));
		System.out.println(getNextDayDateInGMT("2022-01-21", null, "Asia/Kolkata"));
	}

}

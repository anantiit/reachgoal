package com.reachgoal.core_utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils {

	public static final int SECONDS_IN_DAY = 86400;
	public static final int SECONDS_IN_HOUR = 3600;
	public static final int HOURS_IN_DAY = 24;
	public static final long TO_LONG = 1000L;
	public static final long SEC_TO_NANO = 1000L * 1000L * 1000L;
	public static final String TIME_ZONE = "Etc/UTC";
	public static final String UTC_TIMEZONE = "UTC";
	public static final String GMT_TIMEZONE = "GMT";
	public static final int MINUTES_IN_HOUR = 60;
	public static final int SECONDS_IN_MINUTE = 60;
	public static final int DAYS_IN_YEAR = 365;
	public static final int SECS_IN_YEAR = DAYS_IN_YEAR * HOURS_IN_DAY * MINUTES_IN_HOUR * SECONDS_IN_MINUTE;
	public static final int WEEKS_IN_YEAR = 52;
	// For YYYY-MM-DD
	private static final String DATE_REGEX = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

	private DateUtils() {
	}

	public static int daysBetween(final int start, final int end, final String timezone) {
		return Days.daysBetween(new DateTime(start * TO_LONG, DateTimeZone.forID(timezone)),
				new DateTime(end * TO_LONG, DateTimeZone.forID(timezone))).getDays();
	}

	public static int getDayStartTs(final long ts, final String tz) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
		// cal.setTime(date);
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int millisToSec(final long ts) {
		return (int) (ts / TO_LONG);
	}

	public static int minsToSec(final int minSinceEpoch) {
		return minSinceEpoch * MINUTES_IN_HOUR;
	}

	public static long secToMillis(final int seconds) {
		return TO_LONG * seconds;
	}

	public static long secToNano(final int seconds) {
		return SEC_TO_NANO * seconds;
	}

	public static TimeTime getNWeekBackBand(final int secondsFromEpoch, final String tz, final int weeksBack) {
		final DateTime dateTime = new DateTime(secondsFromEpoch * TO_LONG, DateTimeZone.forID(tz));
		return new TimeTime(millisToSec(dateTime.minusWeeks(weeksBack).weekOfWeekyear().roundFloorCopy().getMillis()),
				millisToSec(dateTime.weekOfWeekyear().roundFloorCopy().getMillis()));
	}

	public static TimeTime getNDaysBackBand(final int secondsFromEpoch, final String tz, final int daysBack) {
		final DateTime dateTime = new DateTime(secondsFromEpoch * TO_LONG, DateTimeZone.forID(tz));
		return new TimeTime(millisToSec(dateTime.minusDays(daysBack).dayOfMonth().roundFloorCopy().getMillis()),
				millisToSec(dateTime.dayOfMonth().roundFloorCopy().getMillis()));
	}

	public static int getDayEndTs(final long ts, final String tz) {
		return getDayEndTs(ts, TimeZone.getTimeZone(tz));

	}

	public static int getDayEndTs(final long ts, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.HOUR_OF_DAY, HOURS_IN_DAY - 1);
		cal.set(Calendar.MINUTE, MINUTES_IN_HOUR - 1);
		cal.set(Calendar.SECOND, SECONDS_IN_MINUTE - 1);
		cal.set(Calendar.MILLISECOND, (int) (TO_LONG - 1));
		return (int) (cal.getTimeInMillis() / TO_LONG);

	}

	public static Calendar getDayEndCl(final long ts, final String tz) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.HOUR_OF_DAY, HOURS_IN_DAY - 1);
		cal.set(Calendar.MINUTE, MINUTES_IN_HOUR - 1);
		cal.set(Calendar.SECOND, SECONDS_IN_MINUTE - 1);
		cal.set(Calendar.MILLISECOND, (int) (TO_LONG - 1));
		return cal;
	}

	public static Collection<Integer> getMonthlyDates(final int year, final int month, final int dayOfMonth,
			final int numMonths) {
		LocalDate date = new LocalDate(year, month, dayOfMonth);
		final List<Integer> list = new ArrayList<>(numMonths);
		for (int i = 0; i < numMonths; i++) {
			list.add(DateUtils.millisToSec(date.toDateTimeAtStartOfDay().getMillis()));
			date = date.plusMonths(1);
		}
		return list;
	}

	public static int getCurrentTs(final String tz) {
		final Date date = new Date();
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
		cal.setTimeInMillis(date.getTime());
		return (int) (cal.getTimeInMillis() / TO_LONG);

	}

	public static int getHourOfDay(final int ts, final String tz) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
		cal.setTimeInMillis(ts * TO_LONG);
		return cal.get(Calendar.HOUR_OF_DAY);
	}

	public static String convertDateToStr(final int ts, final String tz, final String format, final Locale locale) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz), locale);
		cal.setTimeInMillis(ts * TO_LONG);
		return DateFormatUtils.format(cal, format, locale);
	}

	public static int numDaysInMonth(final long ts, final String tz) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
		cal.setTimeInMillis(ts * TO_LONG);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public static int getHourStartTs(final int ts, final String tz) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getHourEndTs(final int ts, final String tz) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(tz));
		cal.setTimeInMillis(ts * TO_LONG);
		if (cal.get(Calendar.MINUTE) != 0) {
			cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
		}
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getNextHourStartTs(final int ts, final String tz) {
		return getNextHourStartTs(ts, TimeZone.getTimeZone(tz));
	}

	public static int getNextHourStartTs(final int ts, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 1);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getDayStartTs(final int ts, final String tz) {
		return getDayStartTs(ts, TimeZone.getTimeZone(tz));
	}

	public static int getDayStartTs(final int ts, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getNextDayStartTs(final int ts, final String tz) {
		return getNextDayStartTs(ts, TimeZone.getTimeZone(tz));
	}

	public static int getNextDayStartTs(final int ts, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getNextDayEndTs(final int ts, final String tz) {
		return getNextDayEndTs(ts, TimeZone.getTimeZone(tz));
	}

	public static int getNextDayEndTs(final int ts, final TimeZone tz) {
		final Calendar cal = Calendar.getInstance(tz);
		cal.setTimeInMillis(ts * TO_LONG);
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 2);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getPreviousDayStart(final int timestamp, final String timezone) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getStartOfMonth(final int timestamp, final String timezone) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getEndOfMonth(final int timestamp, final String timezone) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, HOURS_IN_DAY - 1);
		cal.set(Calendar.MINUTE, MINUTES_IN_HOUR - 1);
		cal.set(Calendar.SECOND, SECONDS_IN_MINUTE - 1);
		cal.set(Calendar.MILLISECOND, (int) (TO_LONG - 1));
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getNextMonthStart(final int timestamp, final String timezone) {
		return getNextMonthStart(timestamp, TimeZone.getTimeZone(timezone));
	}

	public static int getNextMonthStart(final int timestamp, final TimeZone timezone) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getStartOfYear(final int timestamp, final String timezone) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static TimeZone getDefaultTimeZone() {
		return TimeZone.getTimeZone(GMT_TIMEZONE);
	}

	public static int getNumberOfDays(final int start, final int end, final String timezone) {
		int startTs = getDayStartTs(start, timezone);
		int daysCount = 0;
		while (startTs < end) {
			daysCount++;
			startTs = getNextDayStartTs(startTs, timezone);
		}
		return daysCount;
	}

	public static Set<Integer> getStartDayTsBetweenDays(final int start, final int end, final String timezone) {
		final Set<Integer> timestamps = new HashSet<>();
		if (end < start) {
			return timestamps;
		}
		int startTs = getDayStartTs(start, timezone);
		while (startTs < end) {
			startTs = getNextDayStartTs(startTs, timezone);
			timestamps.add(startTs);
		}
		return timestamps;
	}

	public static int getNextYearStartTS(final int timeStamp, final String timeZone) {
		return getNextYearStartTS(timeStamp, TimeZone.getTimeZone(timeZone));
	}

	public static int getNextYearStartTS(final int timeStamp, final TimeZone timeZone) {
		final Calendar cal = Calendar.getInstance(timeZone);
		cal.setTimeInMillis(timeStamp * TO_LONG);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) + 1);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getTimestampFromDate(final String date, final TimeZone timezone, final String format) {
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(format)
				.withZone(DateTimeZone.forTimeZone(timezone));
		return (int) (formatter.parseMillis(date) / TO_LONG);
	}

	public static int shiftTimeZone(final int timestampInSec, final TimeZone fromTimezone, final TimeZone toTimezone) {
		final Calendar calFrom = Calendar.getInstance(fromTimezone);
		final Calendar calTo = Calendar.getInstance(toTimezone);
		calFrom.setTimeInMillis(timestampInSec * TO_LONG);
		calTo.set(Calendar.YEAR, calFrom.get(Calendar.YEAR));
		calTo.set(Calendar.MONTH, calFrom.get(Calendar.MONTH));
		calTo.set(Calendar.DAY_OF_MONTH, calFrom.get(Calendar.DAY_OF_MONTH));
		calTo.set(Calendar.HOUR_OF_DAY, calFrom.get(Calendar.HOUR_OF_DAY));
		calTo.set(Calendar.MINUTE, calFrom.get(Calendar.MINUTE));
		calTo.set(Calendar.SECOND, calFrom.get(Calendar.SECOND));
		calTo.set(Calendar.MILLISECOND, calFrom.get(Calendar.MILLISECOND));
		return (int) (calTo.getTimeInMillis() / TO_LONG);
	}

	public static int getPreviousYearTimestamp(final int timestamp, final TimeZone timezone) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getPreviousDayTimestamp(final int timestamp, final TimeZone timezone) {
		return getPreviousNDaysTimestamp(timestamp, timezone, 1);
	}

	public static int getPreviousDayTimestamp(final int timestamp, final String timezone) {
		return getPreviousDayTimestamp(timestamp, TimeZone.getTimeZone(timezone));
	}

	public static int getPreviousNDaysTimestamp(final int timestamp, final TimeZone timezone, final int days) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - days);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getPreviousNMonthsTimestamp(final int timestamp, final TimeZone timezone, final int months) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - months);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static int getPreviousNDaysTimestamp(final int timestamp, final String timezone, final int days) {
		return getPreviousNDaysTimestamp(timestamp, TimeZone.getTimeZone(timezone), days);
	}

	public static int getPreviousNMonthsTimestamp(final int timestamp, final String timezone, final int months) {
		return getPreviousNMonthsTimestamp(timestamp, TimeZone.getTimeZone(timezone), months);
	}

	public static int getMonthFromTimestamp(final int timestamp, final String timeZone) {
		return getMonthFromTimestamp(timestamp, TimeZone.getTimeZone(timeZone));
	}

	public static int getMonthFromTimestamp(final int timestamp, final TimeZone timeZone) {
		final Calendar cal = Calendar.getInstance(timeZone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		return cal.get(Calendar.MONTH);
	}

	public static int getDayInMonthFromTimestamp(final int timestamp, final TimeZone timeZone) {
		final Calendar cal = Calendar.getInstance(timeZone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	public static int getYearFromTimestamp(final int timestamp, final TimeZone timeZone) {
		final Calendar cal = Calendar.getInstance(timeZone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		return cal.get(Calendar.YEAR);
	}

	public static int roundToNearestDayStart(final int timestamp, final TimeZone timezone) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		if (cal.get(Calendar.HOUR_OF_DAY) < 12) {
			return getDayStartTs(timestamp, timezone.getID());
		} else {
			return getNextDayStartTs(timestamp, timezone.getID());
		}
	}

	public static boolean isSameDay(final int firstTimestamp, final int secondTimestamp, final TimeZone timezone) {
		final Calendar calFirst = Calendar.getInstance(timezone);
		calFirst.setTimeInMillis(firstTimestamp * TO_LONG);
		final Calendar calSecond = Calendar.getInstance(timezone);
		calSecond.setTimeInMillis(secondTimestamp * TO_LONG);
		return calFirst.get(Calendar.DAY_OF_YEAR) == calSecond.get(Calendar.DAY_OF_YEAR)
				&& calFirst.get(Calendar.YEAR) == calSecond.get(Calendar.YEAR);

	}

	public static int getMidnightTime(final int year, final int month, final int day, final TimeZone timezone) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.set(year, month, day, 0, 0, 0);
		return millisToSec(cal.getTimeInMillis());
	}

	public static String getDayOfWeek(final int timestamp, final String timeZone, final Locale locale) {
		return getDayOfWeek(timestamp, TimeZone.getTimeZone(timeZone), locale);
	}

	public static String getDayOfWeek(final int timestamp, final String timeZone) {
		return getDayOfWeek(timestamp, TimeZone.getTimeZone(timeZone));
	}

	public static String getDayOfWeek(final int timestamp, final TimeZone timeZone, final Locale locale) {
		final Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timestamp * TO_LONG);
		final SimpleDateFormat format = new SimpleDateFormat("EEEE", locale);
		format.setCalendar(calendar);
		return format.format(calendar.getTime());
	}

	public static int getDayOfWeekValue(final int timestamp, final String timeZone) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		calendar.setTimeInMillis(timestamp * TO_LONG);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static String getDayOfWeek(final int timestamp, final TimeZone timeZone) {
		final Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timestamp * TO_LONG);
		final SimpleDateFormat format = new SimpleDateFormat("EEEE");
		format.setCalendar(calendar);
		return format.format(calendar.getTime());
	}

	public static DateTime getDateTime(final long timeStampInMiliSeconds, final String timeZone) {
		DateTime dateTime = new DateTime(timeStampInMiliSeconds);
		dateTime = dateTime.toDateTime(DateTimeZone.forID(timeZone));
		return dateTime;
	}

	public static int getEpochDay(final long eventTimeInSeconds) {
		if (eventTimeInSeconds < 0) {
			return -1;
		}
		final Days days = Days.daysBetween(new DateTime(0, DateTimeZone.UTC),
				new DateTime(eventTimeInSeconds * TO_LONG, DateTimeZone.UTC));
		return days.getDays();
	}

	public static int getHoursInDay(final int timestamp, final String timeZone) {
		final Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		calendar.setTimeInMillis(timestamp * TO_LONG);
		calendar.add(Calendar.DATE, 1);
		final int nextDaySameTimestamp = (int) (calendar.getTimeInMillis() / TO_LONG);
		return (nextDaySameTimestamp - timestamp) / SECONDS_IN_HOUR;
	}

	/**
	 * Null-safe implementation of {@link java.util.Calendar#getTime()}
	 *
	 * @param calendar
	 * @return
	 */
	public static Date getTimeSafe(final Calendar calendar) {
		if (calendar == null) {
			return null;
		} else {
			return calendar.getTime();
		}
	}

	// Method to parse date string in yyyy-MM-dd format to Date.
	public static Date parseDate(final String date, final TimeZone timeZone) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat.YEAR_MONTH_DAY.getFormat());
		dateFormat.setTimeZone(timeZone);
		try {
			if (!date.matches(DATE_REGEX)) {
				throw new RuntimeException("Given date is " + date + " whereas expected date format is "
						+ DateFormat.YEAR_MONTH_DAY.getFormat());
			}
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			throw new RuntimeException("Given date is " + date + " whereas expected date format is "
					+ DateFormat.YEAR_MONTH_DAY.getFormat(), e);
		}
	}

	// Method to parse date string to Date when DateFormat is passed as
	// parameter
	public static Date parseDate(final String date, final TimeZone timeZone, final DateFormat format) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat(format.getFormat());
		dateFormat.setTimeZone(timeZone);
		try {
			return dateFormat.parse(date);
		} catch (final ParseException e) {
			throw new RuntimeException("Error parsing date: " + date + " with format: " + format.getFormat(), e);
		}
	}

	public static int getNextNDaysTimestamp(final int timestamp, final String timezone, final int days) {
		return getNextNDaysTimestamp(timestamp, TimeZone.getTimeZone(timezone), days);
	}

	public static int getNextNDaysTimestamp(final int timestamp, final TimeZone timezone, final int days) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.setTimeInMillis(timestamp * TO_LONG);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + days);
		return (int) (cal.getTimeInMillis() / TO_LONG);
	}

	public static boolean isWeekend(final Calendar cal) {
		final int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY;
	}

	public static void setToDayStart(final Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
	}

	/**
	 * This method return the no of intersecting days between two given pair of
	 * timestamps
	 *
	 * @param timeStampInputSt
	 * @param timeStampInputEnd
	 * @param timeStampOutputSt
	 * @param timeStampOutputEnd
	 * @param timezone
	 * @return
	 */
	public static int getIntersectingDays(final int timeStampInputSt, final int timeStampInputEnd,
			final int timeStampOutputSt, final int timeStampOutputEnd, final String timezone) {
		int startTs = getDayStartTs(timeStampInputSt, timezone);
		final int endTs = getDayStartTs(timeStampInputEnd, timezone);
		final int outputStartTs = getDayStartTs(timeStampOutputSt, timezone);
		final int outputEndTs = getDayStartTs(timeStampOutputEnd, timezone);
		int noOfDays = 0;
		while (startTs <= endTs) {
			if (startTs >= outputStartTs && startTs <= outputEndTs) {
				noOfDays++;
			}
			startTs = getNextDayStartTs(startTs, timezone);
		}

		return noOfDays;

	}

	/**
	 * This method returns the n months old start timestamp.
	 *
	 * @param months
	 * @return
	 */
	public static Date getNMonthsOldStart(final int months) {
		final Calendar cal = Calendar.getInstance(getDefaultTimeZone());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - months);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Return date in given format with date suffix.
	 * 
	 * Supported only for locale en_US.
	 * 
	 * Example : Return 13th November instead of 13 November.
	 * 
	 * @param timestamp
	 * @param timeZone
	 * @param formatString
	 * @return
	 */
	public static String getDateInGivenFormatWithDateSuffix(final int timestamp, final TimeZone timeZone,
			final DateFormat formatString, final Locale userLocale) {
		if (!userLocale.equals(Locale.US)) {
			throw new UnsupportedOperationException(
					"user locale" + userLocale + "is not supported to get date with suffix.");
		}
		final Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timestamp * TO_LONG);
		final String suffix = getDateSuffix(calendar.get(Calendar.DAY_OF_MONTH));
		final SimpleDateFormat format = new SimpleDateFormat(
				formatString.getFormat().replace(DateFormat.DATE_SUFFIX, suffix));
		format.setCalendar(calendar);
		return format.format(calendar.getTime());
	}

	private static String getDateSuffix(final int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}
		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public static String getDateInGivenFormat(final int timestamp, final TimeZone timeZone,
			final DateFormat formatString) {
		final Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timestamp * TO_LONG);
		final SimpleDateFormat format = new SimpleDateFormat(formatString.getFormat());
		format.setCalendar(calendar);
		return format.format(calendar.getTime());
	}

	public static String getDateInGivenFormatFromEpochLong(final long timestamp, final TimeZone timeZone,
			final DateFormat formatString) {
		final Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timestamp);
		final SimpleDateFormat format = new SimpleDateFormat(formatString.getFormat());
		format.setCalendar(calendar);
		return format.format(calendar.getTime());
	}

	public static String getDateInGivenFormat(final int timestamp, final TimeZone timeZone,
			final DateFormat formatString, final Locale locale) {
		if (locale == null) {
			return getDateInGivenFormat(timestamp, timeZone, formatString);
		}
		final Calendar calendar = Calendar.getInstance(timeZone);
		calendar.setTimeInMillis(timestamp * TO_LONG);
		final SimpleDateFormat format = new SimpleDateFormat(formatString.getFormat(), locale);
		format.setCalendar(calendar);
		return format.format(calendar.getTime());
	}

	/**
	 * This method returns previous year epoch for the same date and month. input
	 * should be date with out year in the format dd-mm
	 * 
	 * @param dateWithOutYear (dateformat dd-mm)
	 * @param timeZone
	 * @return
	 */
	public static int getPreviousYearTimeStampWithGivenFormat(final String dateFarmat, final String dateWithOutYear,
			final String timeZone) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFarmat);
		final TimeZone timeZoneObj = TimeZone.getTimeZone(timeZone);
		simpleDateFormat.setTimeZone(timeZoneObj);
		final Date date = simpleDateFormat.parse(dateWithOutYear);
		final Calendar cal = Calendar.getInstance(timeZoneObj);
		final int currentYear = cal.get(Calendar.YEAR);
		cal.setTime(date);
		cal.set(Calendar.YEAR, currentYear);
		cal.add(Calendar.YEAR, -1);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * This method returns previous year epoch timestamp for the given time. input
	 * should be timeStamp in epoch
	 * 
	 * @param timestamp
	 * @param timeZone
	 * @return
	 */
	public static int getPreviousYearTimeStampWithGivenTimeStamp(final int timestamp, final String timeZone) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		cal.setTimeInMillis(timestamp * DateUtils.TO_LONG);
		cal.add(Calendar.YEAR, -1);
		return (int) (cal.getTimeInMillis() / DateUtils.TO_LONG);
	}

	/**
	 * This method returns current year epoch for the same date and month. input
	 * should be date with out year in the format dd-mm
	 * 
	 * @param dateWithOutYear (dateformat dd-mm)
	 * @param timeZone
	 * @return
	 */
	public static int getCurrentYearTimeStampWithGivenFormat(final String dateFarmat, final String dateWithOutYear,
			final String timeZone) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFarmat);
		final TimeZone timeZoneObj = TimeZone.getTimeZone(timeZone);
		simpleDateFormat.setTimeZone(timeZoneObj);
		final Date date = simpleDateFormat.parse(dateWithOutYear);
		final Calendar cal = Calendar.getInstance(timeZoneObj);
		final int currentYear = cal.get(Calendar.YEAR);
		cal.setTime(date);
		cal.set(Calendar.YEAR, currentYear);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * This method returns last year start and end epoch time for the given start
	 * and end dates in the given dateFormat.Method used for generating seasonal
	 * events like pdf reports , email ..etc where we need previous year season
	 * duration based on current season provided in dateFormat to generate events.
	 * 
	 * @param dateFormat
	 * @param endDateWithOutYear   (dateformat MM-dd)
	 * @param startDateWithOutYear (dateformat MM-dd)
	 * @param timeZone
	 * @return
	 */
	public static TimeTime getPreviousYearTimeTimeForGivenStartAndEndTime(final String dateFormat,
			final String endDateWithOutYear, final String startDateWithOutYear, final String timeZone)
			throws ParseException {
		int startTime = 0;
		int endTime = 0;
		endTime = DateUtils.getCurrentYearTimeStampWithGivenFormat(dateFormat, endDateWithOutYear, timeZone);
		endTime = DateUtils.getDayEndTs(endTime, timeZone);
		while (endTime > now()) {
			endTime = DateUtils.getPreviousYearTimeStampWithGivenTimeStamp(endTime, timeZone);
		}
		startTime = DateUtils.getCurrentYearTimeStampWithGivenFormat(dateFormat, startDateWithOutYear, timeZone);
		startTime = DateUtils.getDayStartTs(startTime, timeZone);
		while (startTime > endTime) {
			startTime = DateUtils.getPreviousYearTimeStampWithGivenTimeStamp(startTime, timeZone);
		}

		return new TimeTime(startTime, endTime);
	}

	public static final int now() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * This method returns time months before or time months after. months before:
	 * yyyy-(mm - monthsToPad)-01 00:00:00 months after: yyyy-(mm +
	 * monthsToPad)-(last day of month) 23:59:59 e.g. 1: timestamp: 1577836800
	 * (January 1, 2020 12:00:00 AM) timezone: GMT monthsToPad: 4 padMonthsBefore:
	 * true return: 1588291200 (May 1, 2020 12:00:00 AM) e.g. 2: timestamp:
	 * 1588291200 (May 1, 2020 12:00:00 AM) timezone: GMT monthsToPad: 4
	 * padMonthsBefore: false return: 1577836799 (December 31, 2019 11:59:59 PM)
	 * 
	 * @param timestamp       datetime to start from
	 * @param timezone        string format (e.g. UTC)
	 * @param monthsToPad
	 * @param padMonthsBefore pass true if pad months before timestamp. false
	 *                        otherwise
	 * @return epoch time in seconds
	 */
	public static int getDatetimeWithMonthsPadding(final int timestamp, final String timezone, final int monthsToPad,
			final boolean padMonthsBefore) {
		final Calendar datetimeToPad = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		datetimeToPad.setTimeInMillis(secToMillis(timestamp));
		datetimeToPad.add(Calendar.MONTH, (padMonthsBefore ? (monthsToPad * -1) : monthsToPad));
		datetimeToPad.set(Calendar.DAY_OF_MONTH,
				(padMonthsBefore ? 1 : datetimeToPad.getActualMaximum(Calendar.DAY_OF_MONTH)));
		return padMonthsBefore ? getDayStartTs(millisToSec(datetimeToPad.getTimeInMillis()), timezone)
				: getDayEndTs(millisToSec(datetimeToPad.getTimeInMillis()), timezone);
	}

	/**
	 * This method returns the number of months between 2 dates inclusive of the
	 * month the end date is in. e.g. start: April 1 2020 end: June 15 2020 - it
	 * would be 3 months (Apr, May, Jun; start: April 1 2020 end: July 1 2020 - it
	 * would be 5 months (Apr, May, Jun, Jul); start: Jan 1 2019 end: Jan 1 2020 -
	 * it would be 13 months (Jan - Dec 2019 + Jan 2020); internally start will be
	 * padded to yyyy/mm/01 00:00:00 and end will be padded yyyy/mm/<last day>
	 * 23:59:59 before comparison is done using joda monthsBetween
	 * 
	 * @param start
	 * @param end
	 * @param timezone
	 * @return
	 */
	public static int getMonthsBetweenWithPadding(final int start, final int end, final String timezone) {
		return Months.monthsBetween(getDateTime((getStartOfMonth(start, timezone) * TO_LONG), timezone),
				getDateTime((getNextMonthStart(end, timezone) * TO_LONG), timezone)).getMonths();
	}

	/**
	 * The method fetches any previous week Duration TimeTime w.r.t given
	 * firstDayOfWeek and prevWeekNumber(how many weeks to go back).TimeTime
	 * contains start of first day and day start of last day of respective previous
	 * Week.
	 * 
	 * @param firstDayOfWeek
	 * @param prevWeekNumber
	 * @param timeZone
	 * @return
	 */

	public static TimeTime getPrevWeekTimeTimeWithGivenFirstDayOfWeek(final DayOfWeek firstDayOfWeek,
			final int prevWeekNumber, final String timeZone) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.DAY_OF_WEEK, firstDayOfWeek.getValue() % 7 + 1);
		if (now() < (cal.getTimeInMillis() / 1000)) {
			cal.add(Calendar.DATE, -7);
		}
		cal.add(Calendar.DATE, -7 * prevWeekNumber);
		final int firstDateOfPreviousWeek = (int) (cal.getTimeInMillis() / 1000);
		cal.add(Calendar.DATE, +6);
		final int endDateOfPreviousWeek = (int) (cal.getTimeInMillis() / 1000);
		return new TimeTime(firstDateOfPreviousWeek, getDayEndTs(endDateOfPreviousWeek, timeZone));
	}

	/**
	 * This method checks for the DayOfWeek enum
	 * 
	 * @param dayName
	 * @return
	 */
	public static DayOfWeek getDayOfTheWeek(final String dayName) {
		for (final DayOfWeek dayOfWeek : DayOfWeek.values()) {
			if (dayOfWeek.name().equalsIgnoreCase(dayName)) {
				return dayOfWeek;
			}
		}
		return null;
	}

	/**
	 * this method maps the days of the week from java.util.Calendar.DAY_OF_WEEK to
	 * java.time.DayOfWeek. this is required as in case of
	 * java.util.Calendar.DAY_OF_WEEK the week starts from sunday and in case of
	 * java.time.DayOfWeek the week starts from monday.
	 *
	 * @param dayOfWeek
	 * @return
	 */
	public static DayOfWeek mapDayOfTheWeek(final int dayOfWeek) {
		switch (dayOfWeek) {
		case Calendar.SUNDAY:
			return DayOfWeek.SUNDAY;
		case Calendar.MONDAY:
			return DayOfWeek.MONDAY;
		case Calendar.TUESDAY:
			return DayOfWeek.TUESDAY;
		case Calendar.WEDNESDAY:
			return DayOfWeek.WEDNESDAY;
		case Calendar.THURSDAY:
			return DayOfWeek.THURSDAY;
		case Calendar.FRIDAY:
			return DayOfWeek.FRIDAY;
		case Calendar.SATURDAY:
			return DayOfWeek.SATURDAY;
		default:
			return null;
		}
	}

	/**
	 * this method checks if the given time of the day represented by the hour and
	 * the minute lies within the passed in start and end hour and minutes. the
	 * check considers the range [startTime, endTime) ie endTime not inclusive.
	 * 
	 * @param hourOfTheDayToBeChecked
	 * @param minuteOfTheHourToBeChecked
	 * @param startHour
	 * @param startMinute
	 * @param endHour
	 * @param endMinute
	 * @return
	 * @throws ParseException
	 */
	public static boolean isGivenHourAndMinuteBetweenStartAndEndHourAndMinute(final int hourOfTheDayToBeChecked,
			final int minuteOfTheHourToBeChecked, final Integer startHour, final Integer startMinute,
			final Integer endHour, final Integer endMinute) throws ParseException {
		if (startHour == null || startMinute == null || endHour == null || endMinute == null) {
			return false;
		}
		final SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm");
		final Date date = parseFormat.parse(hourOfTheDayToBeChecked + ":" + minuteOfTheHourToBeChecked);
		final Date startDate = parseFormat.parse(startHour + ":" + startMinute);
		final Date endDate = parseFormat.parse(endHour + ":" + endMinute);
		return date.compareTo(startDate) >= 0 && date.compareTo(endDate) < 0;
	}

	/**
	 * This method checks if the time interval represented by the starthour,
	 * startminute and the endhour,endminute is valid or not. for example an
	 * interval starting at 10:00am and ending at 11:00am is valid, whereas an
	 * interval starting at 10:00am and ending at 09:00am is invalid, as the start
	 * is less than the end, and these intervals are supposed to be on the same day.
	 * 
	 * @param startHour
	 * @param endHour
	 * @param startMinute
	 * @param endMinute
	 * @return
	 */
	public static boolean isTimeIntervalBetweenGivenHoursAndMinutesValidWithinADay(final Integer startHour,
			final Integer endHour, final Integer startMinute, final Integer endMinute) {
		if (startHour == null || startMinute == null || endHour == null || endMinute == null) {
			return false;
		}
		if (startHour == endHour && startMinute >= endMinute) {
			return false;
		}
		return true;
	}

	/**
	 * This method converts the timestamp in one timezone to one in a different
	 * timezone
	 * 
	 * @param timestamp
	 * @param fromTimeZone
	 * @param toTimeZone
	 * @return
	 */
	public static int convertTimestampToDifferentTimezone(final int timestamp, final TimeZone fromTimeZone,
			final TimeZone toTimeZone) {
		final long timestampInMillis = timestamp * TO_LONG;
		final Calendar calendar = Calendar.getInstance(fromTimeZone);
		calendar.setTimeInMillis(timestampInMillis);
		calendar.add(Calendar.MILLISECOND, fromTimeZone.getOffset(timestampInMillis));
		calendar.add(Calendar.MILLISECOND, toTimeZone.getOffset(timestampInMillis) * -1);
		return getEpochSecondsInt(calendar);
	}

	public static int getEpochSecondsInt(final Calendar cal) {
		return (int) (cal.getTimeInMillis() / 1000);
	}

	public static int getCurrentMonthStart(final int year, final int month, final TimeZone timezone) {
		final Calendar cal = Calendar.getInstance(timezone);
		cal.set(year, month - 1, cal.getActualMinimum(Calendar.DAY_OF_MONTH), 0, 0, 0);
		return millisToSec(cal.getTimeInMillis());
	}

	public static Calendar getCalendarInstance(final long epochTimeInMillis, final String timezone) {
		final Calendar cal = Calendar.getInstance(TimeZone.getTimeZone(timezone));
		cal.setTimeInMillis(epochTimeInMillis);
		return cal;
	}

}

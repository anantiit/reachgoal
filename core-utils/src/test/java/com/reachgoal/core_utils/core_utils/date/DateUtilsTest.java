package com.reachgoal.core_utils.core_utils.date;

import java.text.ParseException;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.reachgoal.core_utils.date.DateFormat;
import com.reachgoal.core_utils.date.DateUtils;
import com.reachgoal.core_utils.date.TimeTime;

import junit.framework.Assert;

public class DateUtilsTest {

	private static final int MAR_15_2016_LA = 1458025200;
	private static final int MAR_20_2016_LA = 1458457200;
	private static final int APR_1_2016_LA = 1459494000;
	private static final int MAY_1_2016_LA = 1462086000;
	private static final int JUN_1_2016_LA = 1464764400;
	private static final int JUN_10_2016_LA = 1465542000;
	private static final String LA_TIMEZONE = "America/Los_Angeles";

	@Test
	public void testGetPreviousNDaysTimestamp() {

		Assert.assertEquals(DateUtils.getPreviousNDaysTimestamp(JUN_1_2016_LA, LA_TIMEZONE, -9), JUN_10_2016_LA);
		Assert.assertEquals(DateUtils.getPreviousNMonthsTimestamp(JUN_1_2016_LA, LA_TIMEZONE, 2), APR_1_2016_LA);
	}

	@Test
	public void testGetPreviousNMonthsTimestamp() {
		Assert.assertEquals(DateUtils.getPreviousNMonthsTimestamp(JUN_1_2016_LA, LA_TIMEZONE, 2), APR_1_2016_LA);
		Assert.assertEquals(DateUtils.getPreviousNMonthsTimestamp(JUN_1_2016_LA, LA_TIMEZONE, 1), MAY_1_2016_LA);
	}

	@Test
	public void testGetNumberOfDaysNonDST() {
		final int numberOfDays = DateUtils.getNumberOfDays(1443672000, 1446350400, "America/Toronto");
		Assert.assertEquals(31, numberOfDays);
	}

	@Test
	public void testSecToMillis() {
		final long millis = DateUtils.secToMillis(1443672000);
		Assert.assertEquals(1443672000000l, millis);
	}

	@Test
	public void testSecToNanos() {
		final long nanos = DateUtils.secToNano(1443672000);
		Assert.assertEquals(1443672000 * DateUtils.SEC_TO_NANO, nanos);
	}

	@Test
	public void testGetNumberOfDaysDST() {
		final int numberOfDays = DateUtils.getNumberOfDays(1446350400, 1448942400, "America/Toronto");
		Assert.assertEquals(30, numberOfDays);
	}

	@Test
	public void testPreviousDay() {
		final TimeZone tz1 = TimeZone.getTimeZone("GMT");
		final TimeZone tz2 = TimeZone.getTimeZone("America/New_York");

		final int timestampDST = 1457936665;
		final int timestampDST1 = DateUtils.getPreviousDayTimestamp(timestampDST, tz1);
		final int timestampDST2 = DateUtils.getPreviousDayTimestamp(timestampDST, tz2);
		Assert.assertEquals(timestampDST1, 1457850265);
		Assert.assertEquals(timestampDST2, 1457853865);

		final int timestampNoDST = 1457588915;
		final int timestampNoDST1 = DateUtils.getPreviousDayTimestamp(timestampNoDST, tz1);
		final int timestampNoDST2 = DateUtils.getPreviousDayTimestamp(timestampNoDST, tz2);
		Assert.assertEquals(timestampNoDST1, 1457502515);
		Assert.assertEquals(timestampNoDST2, 1457502515);
	}

	@Test
	public void testGetTimestampFromDate() {
		final String format = "yyyy-MM-dd";
		final String date1 = "2015-11-05";
		final TimeZone timezone1 = TimeZone.getTimeZone("IST");
		final int timestamp1 = DateUtils.getTimestampFromDate(date1, timezone1, format);
		Assert.assertEquals(1446661800, timestamp1);

		final String date2 = "2016-01-20";
		final TimeZone timezone2 = TimeZone.getTimeZone("America/Los_Angeles");
		final int timestamp2 = DateUtils.getTimestampFromDate(date2, timezone2, format);
		Assert.assertEquals(1453276800, timestamp2);

		final String date3 = "2016-03-20";
		final int timestamp3 = DateUtils.getTimestampFromDate(date3, timezone2, format);
		Assert.assertEquals(1458457200, timestamp3);

		final String date4 = "2016-12-31";
		final int timestamp4 = DateUtils.getTimestampFromDate(date4, timezone2, format);
		Assert.assertEquals(1483171200, timestamp4);
	}

	@Test
	public void testShiftTimeZone() {
		final TimeZone from = TimeZone.getTimeZone("IST");
		final TimeZone to = TimeZone.getTimeZone("America/Los_Angeles");
		final int time = 1456012800;
		final int convertedTime = DateUtils.shiftTimeZone(time, from, to);
		Assert.assertEquals(1456012800 + 48600, convertedTime);

		final int timeDST = 1458432000;
		final int convertedTimeDST = DateUtils.shiftTimeZone(timeDST, from, to);
		Assert.assertEquals(1458432000 + 45000, convertedTimeDST);
	}

	@Test
	public void testRoundToCurrentDay() {
		final int dayStart = DateUtils.roundToNearestDayStart(1446177600 + 3600,
				TimeZone.getTimeZone("America/New_York"));
		Assert.assertEquals(1446177600, dayStart);
	}

	@Test
	public void testRoundToNextDay() {
		final int dayStart = DateUtils.roundToNearestDayStart(1446177600 + 22 * 3600,
				TimeZone.getTimeZone("America/New_York"));
		Assert.assertEquals(1446177600 + 86400, dayStart);
	}

	@Test
	public void testRoundToCurrentDaySameValue() {
		final int dayStart = DateUtils.roundToNearestDayStart(1446177600, TimeZone.getTimeZone("America/New_York"));
		Assert.assertEquals(1446177600, dayStart);
	}

	@Test
	public void testMonthlyDates() {
		final Collection<Integer> dates = DateUtils.getMonthlyDates(2015, 1, 1, 24);
		Assert.assertEquals(24, dates.size());
		final Iterator<Integer> iterator = dates.iterator();

		Integer prevTime = 0;
		while (iterator.hasNext()) {
			if (prevTime == 0) {
				prevTime = iterator.next();
			} else {
				final Integer time = iterator.next();
				Assert.assertTrue(time - prevTime > 27 * 86400);
				Assert.assertTrue(time - prevTime < 32 * 86400);
				prevTime = time;
			}
		}
	}

	@Test
	public void testIsSameDay() {
		Assert.assertTrue(DateUtils.isSameDay(1462443528, 1462486728, TimeZone.getTimeZone("GMT")));
		Assert.assertTrue(DateUtils.isSameDay(1462443528, 1462470528, TimeZone.getTimeZone("IST")));
		Assert.assertFalse(DateUtils.isSameDay(1462386600, 1462473000, TimeZone.getTimeZone("IST")));
		Assert.assertTrue(DateUtils.isSameDay(1462386600, 1462472999, TimeZone.getTimeZone("IST")));
	}

	@Test
	public void testMidnightTime() {
		Assert.assertEquals(1075593600, DateUtils.getMidnightTime(2004, 1, 1, TimeZone.getTimeZone("GMT")));
		Assert.assertEquals(1075622400,
				DateUtils.getMidnightTime(2004, 1, 1, TimeZone.getTimeZone("America/Los_Angeles")));
	}

	@Test
	public void getNextHourBeginTimestamp() {
		Assert.assertEquals(1468225800, DateUtils.getNextHourStartTs(1468222449, "IST"));
		Assert.assertEquals(1468224000, DateUtils.getNextHourStartTs(1468222449, "GMT"));
	}

	@Test
	public void testGetDayOfWeek() {
		Assert.assertEquals(DateUtils.getDayOfWeek(1467121929, "IST"), "Tuesday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "GMT"), "Saturday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "Australia/Hobart"), "Saturday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "America/Phoenix"), "Friday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "Atlantic/Bermuda"), "Friday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "GMT", Locale.US), "Saturday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "GMT", Locale.UK), "Saturday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "GMT", Locale.CANADA), "Saturday");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "GMT", Locale.FRANCE), "samedi");
		Assert.assertEquals(DateUtils.getDayOfWeek(1461369600, "GMT", Locale.GERMANY), "Samstag");
	}

	@Test
	public void testGetDateTime() {
		DateTime dateTime = new DateTime(1467121929000l);
		dateTime = dateTime.toDateTime(DateTimeZone.forID("GMT"));
		Assert.assertEquals(DateUtils.getDateTime(1467121929000l, "GMT"), dateTime);
	}

	@Test
	public void testGetHourStartTS() {

		final TimeZone timezone = TimeZone.getTimeZone("IST");
		int timestamp = DateUtils.getHourStartTs(1446662000, timezone.getID());
		Assert.assertEquals(1446661800, timestamp);

		timestamp = DateUtils.getNextHourStartTs(1446662000, timezone.getID());
		Assert.assertEquals(1446665400, timestamp);
	}

	@Test
	public void testGetDayStartTS() {

		final TimeZone timezone = TimeZone.getTimeZone("IST");
		final int timestamp = DateUtils.getDayStartTs(1446662000, timezone.getID());
		Assert.assertEquals(1446661800, timestamp);
	}

	@Test
	public void testGetYearFromTS() {

		final TimeZone timezone = TimeZone.getTimeZone("IST");
		final int year = DateUtils.getYearFromTimestamp(1446662000, timezone);
		Assert.assertEquals(2015, year);
	}

	@Test
	public void testGetNextDayEndTs() {
		Assert.assertEquals(1472860800, DateUtils.getNextDayEndTs(1472688000, "GMT"));
		Assert.assertEquals(1472860800, DateUtils.getNextDayEndTs(1472688001, "GMT"));
	}

	@Test
	public void testGetEpochDay() {
		Assert.assertEquals(0, DateUtils.getEpochDay(1));
		Assert.assertEquals(1, DateUtils.getEpochDay(99400));
		Assert.assertEquals(2, DateUtils.getEpochDay(2 * 99400));
	}

	@Test
	public void testGetHoursInDay() {
		Assert.assertEquals(DateUtils.getHoursInDay(1459602000, "Australia/Melbourne"), 25);
		Assert.assertEquals(DateUtils.getHoursInDay(1459778400, "Australia/Melbourne"), 24);
		Assert.assertEquals(DateUtils.getHoursInDay(1475330400, "Australia/Melbourne"), 23);
	}

	@Test
	public void testNextYear() {
		Assert.assertEquals(DateUtils.getNextYearStartTS(1459602000, "Australia/Melbourne"), 1483189200);
	}

	@Test
	public void testNextMonth() {
		Assert.assertEquals(DateUtils.getNextMonthStart(1459602000, "Australia/Melbourne"), 1462024800);
	}

	@Test
	public void testDateUtilsMethods() {
		Assert.assertEquals(1504224000, DateUtils.getStartOfMonth(1504711442, "GMT"));
		Assert.assertEquals(1506815999, DateUtils.getEndOfMonth(1504711442, "GMT"));
		Assert.assertEquals(1506816000, DateUtils.getNextMonthStart(1504711442, "GMT"));
		Assert.assertEquals(1483228800, DateUtils.getStartOfYear(1504711442, "GMT"));
		Assert.assertEquals(6, DateUtils.getStartDayTsBetweenDays(1504279495, 1504711442, "GMT").size());
		Assert.assertEquals(1514764800, DateUtils.getNextYearStartTS(1504711442, "GMT"));
		Assert.assertEquals(1473175442, DateUtils.getPreviousYearTimestamp(1504711442, TimeZone.getTimeZone("GMT")));
		Assert.assertEquals(6, DateUtils.getDayInMonthFromTimestamp(1504711442, TimeZone.getTimeZone("GMT")));
		Assert.assertEquals(1512259200, DateUtils.getPreviousDayStart(1512411285, "GMT"));
		Assert.assertEquals(31, DateUtils.numDaysInMonth(1512411285, "GMT"));
	}

	@Test(expected = RuntimeException.class)
	public void testWrongDataFormat() {
		final String wrongDateFormat = "01-05-2018";

		DateUtils.parseDate(wrongDateFormat, TimeZone.getTimeZone("UTC"));
	}

	@Test
	public void testParseDate() {
		final String dateFormat = "2018-05-01";

		final Date date = DateUtils.parseDate(dateFormat, TimeZone.getTimeZone("UTC"));
		Assert.assertEquals(1525132800000l, date.getTime());
	}

	@Test
	public void testParseDateFormat() {
		final String dateFormat = "2018-05-01";

		final Date date = DateUtils.parseDate(dateFormat, TimeZone.getTimeZone("UTC"), DateFormat.YEAR_MONTH_DAY);
		Assert.assertEquals(1525132800000l, date.getTime());
	}

	@Test
	public void testGetNextNDaysTimestamp() {
		final int date = DateUtils.getNextNDaysTimestamp(1459602000, "GMT", 2);
		Assert.assertTrue(date == 1459774800);
	}

	@Test
	public void testSetToDayStart() {
		final TimeZone timezone = TimeZone.getTimeZone("IST");
		final Calendar cal = Calendar.getInstance(timezone);
		cal.setTimeInMillis(1542842160000l);
		DateUtils.setToDayStart(cal);
		Assert.assertEquals(1542825000000l, cal.getTimeInMillis());
	}

	@Test
	public void testGetNMonthsOld() {
		final Calendar cal = Calendar.getInstance(DateUtils.getDefaultTimeZone());
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 2);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Assert.assertEquals(cal.getTime(), DateUtils.getNMonthsOldStart(2));
	}

	@Test
	public void testGetPreviousYearTimeStampWithGivenFormat() {
		try {
			final int currentTimestamp = DateUtils.now();
			DateTime dateTime = new DateTime(currentTimestamp * 1000l, DateTimeZone.forID("Europe/Bratislava"));
			final int prevYear = dateTime.getYear() - 1;
			dateTime = dateTime.withDate(prevYear, 9, 9).withTime(0, 0, 0, 0);
			Assert.assertEquals(dateTime.getMillis() / 1000,

					DateUtils.getPreviousYearTimeStampWithGivenFormat("dd-MM", "09-09", "Europe/Bratislava"));
		} catch (final ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetCurrentYearTimeStampWithGivenFormat() {
		try {
			final String dateWithOutYear = "09-09";
			final int currentTimestamp = DateUtils.now();
			DateTime dateTime = new DateTime(currentTimestamp * 1000l, DateTimeZone.forID("Europe/Bratislava"));
			final int currentYear = dateTime.getYear();
			dateTime = dateTime.withDate(currentYear, 9, 9).withTime(0, 0, 0, 0);
			Assert.assertEquals(dateTime.getMillis() / 1000,
					DateUtils.getCurrentYearTimeStampWithGivenFormat("dd-MM", dateWithOutYear, "Europe/Bratislava"));

		} catch (final ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDateFormatWithSuffix() {
		Assert.assertEquals("Friday, Dec 20th", DateUtils.getDateInGivenFormatWithDateSuffix(1576823836,
				TimeZone.getTimeZone("UTC"), DateFormat.WEEKDAY_MONTH_DAY_SPACE_SUFFIX, Locale.US));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testUnsupportedDateFormatWithSuffix() {
		DateUtils.getDateInGivenFormatWithDateSuffix(1576823836, TimeZone.getTimeZone("UTC"),
				DateFormat.WEEKDAY_MONTH_DAY_SPACE_SUFFIX, Locale.CANADA);
	}

	@Test
	public void testGetDatetimeWithMonthsPadding() {
		Assert.assertEquals(1577836800, DateUtils.getDatetimeWithMonthsPadding(1586379201, "UTC", 3, true));
		Assert.assertEquals(1596239999, DateUtils.getDatetimeWithMonthsPadding(1586379201, "UTC", 3, false));
	}

	@Test
	public void testGetMonthsBetweenWithPadding() {
		// April 15, 2020 3:10:00 AM to June 15, 2020 12:00:00 AM
		Assert.assertEquals(3, DateUtils.getMonthsBetweenWithPadding(1586945400, 1592204400, "America/Los_Angeles"));
		// April 15, 2020 3:10:00 AM to July 31, 2020 3:10:00 AM
		Assert.assertEquals(4, DateUtils.getMonthsBetweenWithPadding(1586945400, 1596190200, "America/Los_Angeles"));
		// July 31, 2020 3:10:00 AM to January 31, 2021 3:10:00 AM
		Assert.assertEquals(7, DateUtils.getMonthsBetweenWithPadding(1596190200, 1612091400, "America/Los_Angeles"));
		// January 1, 2019 12:00:00 AM to January 31, 2021 3:10:00 AM
		Assert.assertEquals(13, DateUtils.getMonthsBetweenWithPadding(1546329600, 1577865600, "America/Los_Angeles"));
	}

	@Test
	public void testGetDateInGivenFormat() {
		Assert.assertEquals("15 April 2020", DateUtils.getDateInGivenFormat(1586945400, TimeZone.getTimeZone("UTC"),
				DateFormat.DAY_FULL_MONTH_IN_WORDS_YEAR_SPACE));
		Assert.assertEquals("15 aprile 2020", DateUtils.getDateInGivenFormat(1586945400, TimeZone.getTimeZone("UTC"),
				DateFormat.DAY_FULL_MONTH_IN_WORDS_YEAR_SPACE, new Locale("it", "IT")));
	}

	@Test
	public void TestGetDateInGivenFormatFromEpochLong() {
		Assert.assertEquals("09/20/2020 22:41:46", DateUtils.getDateInGivenFormatFromEpochLong(1600656106173L,
				TimeZone.getTimeZone("America/New_York"), DateFormat.MONTH_DAY_YEAR_TIME_SLASH));
	}

	@Test
	public void testMapDayOfTheWeek() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		Assert.assertEquals(DayOfWeek.SUNDAY, DateUtils.mapDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK)));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		Assert.assertEquals(DayOfWeek.MONDAY, DateUtils.mapDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK)));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
		Assert.assertEquals(DayOfWeek.TUESDAY, DateUtils.mapDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK)));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
		Assert.assertEquals(DayOfWeek.WEDNESDAY, DateUtils.mapDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK)));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
		Assert.assertEquals(DayOfWeek.THURSDAY, DateUtils.mapDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK)));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		Assert.assertEquals(DayOfWeek.FRIDAY, DateUtils.mapDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK)));
		calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		Assert.assertEquals(DayOfWeek.SATURDAY, DateUtils.mapDayOfTheWeek(calendar.get(Calendar.DAY_OF_WEEK)));
	}

	@Test
	public void testIsGivenHourAndMinuteBetweenStartAndEndHourAndMinute() throws ParseException {
		Assert.assertEquals(true, DateUtils.isGivenHourAndMinuteBetweenStartAndEndHourAndMinute(10, 0, 10, 0, 11, 0));
		Assert.assertEquals(false, DateUtils.isGivenHourAndMinuteBetweenStartAndEndHourAndMinute(10, 0, 9, 0, 10, 0));
		Assert.assertEquals(false, DateUtils.isGivenHourAndMinuteBetweenStartAndEndHourAndMinute(10, 0, 9, 0, 9, 59));
	}

	@Test
	public void testIsTimeIntervalBetweenGivenHoursAndMinutesValid() {
		Assert.assertEquals(false, DateUtils.isTimeIntervalBetweenGivenHoursAndMinutesValidWithinADay(0, 0, 0, 0));
		Assert.assertEquals(true, DateUtils.isTimeIntervalBetweenGivenHoursAndMinutesValidWithinADay(0, 0, 1, 2));
		Assert.assertEquals(false, DateUtils.isTimeIntervalBetweenGivenHoursAndMinutesValidWithinADay(0, 0, 2, 1));
	}

	@Test
	public void testConvertTimestampToDifferentTimezone() {
		// During DST
		Assert.assertEquals(1620100000 + 5 * 3600, DateUtils.convertTimestampToDifferentTimezone(1620100000,
				TimeZone.getTimeZone("UTC"), TimeZone.getTimeZone("CST6CDT")));
		Assert.assertEquals(1614840000 + 6 * 3600, DateUtils.convertTimestampToDifferentTimezone(1614840000,
				TimeZone.getTimeZone("UTC"), TimeZone.getTimeZone("CST6CDT")));
	}

	@Test
	public void testGetPrevWeekTimeTimeWithGivenFirstDayOfWeek() {
		final TimeTime lastWeek = DateUtils
				.getPrevWeekTimeTimeWithGivenFirstDayOfWeek(DateUtils.getDayOfTheWeek("MONDAY"), 1, "UTC");
		final TimeTime priorWeek = DateUtils
				.getPrevWeekTimeTimeWithGivenFirstDayOfWeek(DateUtils.getDayOfTheWeek("MONDAY"), 1, "UTC");
		Assert.assertNotNull(lastWeek);
		Assert.assertNotNull(priorWeek);

	}

	@Test
	public void testGetPreviousYearTimeStampWithGivenTimeStamp() {
		final Integer lastYear = DateUtils.getPreviousYearTimeStampWithGivenTimeStamp(DateUtils.now(), "UTC");
		Assert.assertNotNull(lastYear);

	}

	@Test
	public void testGetCurrentMonthStart() {
		final int timestamp = DateUtils.getCurrentMonthStart(2021, 7, TimeZone.getTimeZone("UTC"));
		Assert.assertEquals(1625097600, timestamp);
	}

	@Test
	public void testGetCalendarInstance() {
		final int time = DateUtils.getCurrentMonthStart(2021, 7, TimeZone.getTimeZone("UTC"));
		Assert.assertTrue(DateUtils.getCalendarInstance(time * 1000, "UTC").getTimeInMillis() > 0);
	}

}

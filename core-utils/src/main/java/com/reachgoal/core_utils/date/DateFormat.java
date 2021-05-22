package com.reachgoal.core_utils.date;

public enum DateFormat {

	YEAR_MONTH_DAY("yyyy-MM-dd"), YEAR_MONTH_DAY_SHORT("yyyy-M-d"), YEAR_MONTH_DAY_WITH_UNDERSCORE("yyyy_MM_dd"),
	YEAR_MONTH("yyyy-MM"), MONTH_DAY("MM-dd"), MONTH_NAME("MMMM"), DAY_MONTH_SLASH("dd/MM"), MONTH_DAY_SLASH("MM/dd"),
	MONTH_DAY_YEAR_SLASH("MM/dd/yy"), MONTH_DAY_4DIGIT_YEAR_SLASH("MM/dd/yyyy"), MONTH_YEAR("MMMM yyyy"),
	DAY_MONTH_YEAR_SLASH("dd/MM/yy"), MONTH_DAY_YEAR_TIME_SLASH("MM/dd/yyyy HH:mm:ss"),
	MONTH_DAY_YEAR_TIME("MMddyyyyHHmm"), DAY_MONTH_IN_WORDS_SPACE("d MMM"), DAY_MONTH_IN_WORDS_YEAR_SPACE("d MMM yyyy"),
	DAY_WEEK_DAY_MONTH_IN_WORDS_SPACE("EEEE d MMM"), DAY_WEEK_MONTH_IN_WORDS_DAY_SPACE("EEEE MMM d"),
	DATE_TIME("yyyy-MM-dd HH:mm:ss"), DAY_MONTH_YEAR("ddMMyyyy"), MONTH_IN_WORDS_SPACE_DAY_COMMA_YEAR("MMM d, yyyy"),
	MONTH_DAY_SPACE("MMM dd"), MONTH_YEAR_SPACE("MMM yyyy"), HOUR_24_FORMAT("HH"), HOUR_12_FORMAT("hh a"),
	HOUR_24_MINUTE_FORMAT("HH:mm"), HOUR_12_MINUTE_FORMAT("hh:mm a"), MONTH_IN_WORDS_DAY_SPACE("MMM d"),
	DAY_FULL_MONTH_IN_WORDS_YEAR_SPACE("dd MMMM yyyy"),
	// MONTH_DAY_SPACE_SUFFIX is Supported only for Locale en_US
	WEEKDAY_MONTH_DAY_SPACE_SUFFIX("EEEE, MMM d" + DateFormat.DATE_SUFFIX_AS_STRING),
	WEEKDAY_MONTH_DAY_SPACE("EEEE, MMM d"), MONTH_SHORT("MMM"),
	FULL_MONTH_IN_WORDS_SPACE_DAY_COMMA_YEAR("MMMM d, yyyy"), WEEKDAY_SHORT("EEE"), WEEKDAY_FULL("EEEE"),
	SHORT_DATE_ONLY("d"), MONTH_DAY_YEAR_SLASH_SHORT("M/d/yyyy");

	public static final String DATE_SUFFIX = "${suffix}";
	public static final String DATE_SUFFIX_AS_STRING = "'" + DATE_SUFFIX + "'";
	private String format;

	public String getFormat() {
		return format;
	}

	private DateFormat(final String format) {
		this.format = format;
	}

}

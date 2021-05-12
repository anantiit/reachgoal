package com.csfundamentals.algo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TimeZone;
import java.util.TreeMap;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.web.util.UriComponentsBuilder;

import com.sun.jersey.api.uri.UriTemplate;

//import com.netflix.astyanax.serializers.AnnotatedCompositeSerializer;

public class Test {
	private static final char MAX_ASCII_PRINTABLE_CHAR = '\u007f';
	private static final String UNICODE_FORMAT = "\\u%04x";
	protected static final String LINK_TYPE_PARAM = "linktype";
	private static final String DEFAULT_DATE_FORMAT1 = "MMddyyyy";
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	private static final String DUKE_TIMEZONE = "America/New_York";
	// private final Logger logger =
	// LoggerFactory.getLogger(MonthlySummaryEmailContentBuilder.class);

	private static void modifyValues(Double a, Double b) {
		a = 0.9d;
		b = new Double(3.4d);
	}

	static String isValid(String s) {
		char[] charArr = s.toCharArray();
		int[] charCountArr = new int[26];
		for (char c : charArr) {
			charCountArr[(int) c - 99]++;
		}
		int counter = 0;
		for (int i = 0; i < 26; i++) {
			if (charCountArr[i] > 1) {
				counter++;
			}
			if (counter > 2) {
				return "NO";
			}
		}
		return "YES";

	}

	public static void main(String[] args) throws Exception {

		System.out.println((int) 'a');
		// CSVReader csvReader = new CSVReader(new FileReader(new
		// File("/Users/aashish/Downloads/userList1.csv")));
		// List<String[]> csv = csvReader.readAll();
		//
		// for (String[] row : csv) {
		// String email = row[0];
		// String zip = row[1];
		// String country = row[2];
		// // sendPost2("http://eonpatapi.bidgely.com/v2.0/users", email, zip,
		// // country, email.split("@")[0].split("\\+")[1]);
		//
		// }
		PriorityQueue<String> pq = new PriorityQueue<>();

		pq.add("Geeks");
		pq.add("For");
		pq.add("Geeks");

		Iterator iterator = pq.iterator();

		while (iterator.hasNext()) {
			System.out.print(iterator.next() + " ");
//		//final HK rowKey = new HK(UUID.fromString("154ce6a8-3b5a-492e-b78a-5876b7924fe0"), 1);
//		System.out.println(rowKey.hashCode());
//		for (int i = 0; i < 10; ++i) {
//			System.out.println(i);
//		}
//		System.out.println(MeasurementType.GAS.getPublicName().equals("Gas"));
			int[] arr = { 1, 4, 3, 2, 5, 6, 9 };
			int K = 12;
			findIfAPairExistInArrayWhichSumToK(arr, K);
			String s = "###ab#b";
			String t = "aab##b###abb";
			t.toCharArray();
			if (checkStringEqualGivenBackspaces(s, t)) {
				System.out.println("\ntrue");
			} else {
				System.out.println("\nfalse");
			}
			Hex h = new Hex();
			String hexcodestr = "44495341424c4544";
			char[] hexcode = hexcodestr.toCharArray();
			System.out.println(h.toString());
			Double a = 0.7d;
			Double b = 0.8d;
			System.out.println("a " + a + "b " + b);
			modifyValues(a, b);
			System.out.println("a " + a + "b " + b);

			System.out.println("a " + a + "b " + b);

//		final ZipKey zipKey = new ZipKey("US", "6907");
//
//		final ZipKey zk2 = new ZipKey("US", "6907");
//		// System.out.println(Hex.bytesToHex(ZipKey.SERIALIZER.toBytes(zk2));
//
//		System.out.println(zipKey.hashCode());

			// final ColumnFamilyQuery<ZipKey, String> nq =
			// keyspace.prepareQuery(ZipColumnFamilies.CF_ZIPCODE_DATA);

			long epochTime = 1600656106173L;
			// final String eventDate =
			// DateUtils.getDateInGivenFormatFromEpochLong(epochTime,
			// TimeZone.getTimeZone(DUKE_TIMEZONE),
			// DateFormat.MONTH_DAY_YEAR_TIME_SLASH);
			// System.out.println(eventDate);

			System.out.println("\\'" + "OPT_IN" + "\\'");
			String startTime = "2020-09-22";
			final TimeZone utilityInvoiceTimeZone = TimeZone.getTimeZone("");
			// int startTimeEpoch = DateUtils.getTimestampFromDate(startTime,
			// utilityInvoiceTimeZone, DEFAULT_DATE_FORMAT);
			// System.out.println("startTimeEpoch" +
			// startTimeEpoch);
			List<Integer> decompressRLElist = new ArrayList<Integer>();
			Double newNumber = 0d;
			Double oldNumber = 100d;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));
			newNumber = 100d;
			oldNumber = 150d;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));
			newNumber = 150d;
			oldNumber = 100d;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));
			newNumber = null;
			oldNumber = 100d;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));
			newNumber = 100d;
			oldNumber = null;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));
			newNumber = null;
			oldNumber = null;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));
			newNumber = 200d;
			oldNumber = 100d;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));
			newNumber = 100d;
			oldNumber = 200d;
			System.out.println(calculateRoundedPercentageOfDifference(newNumber, oldNumber));

			System.out.println(AccountStatusDescription.valueOf("ACTIVE"));
			System.out.println(AccountStatusDescription.valueOf("active1"));
			if (getPreviousYearTimestampWithGivenFormat("dd-MM", "09-09",
					"Europe/Bratislava") == getPreviousYearSameDateEpoch("09-09", "Europe/Bratislava")) {
				System.out.println("oohoo1");
			}
			if (getCurrentYearDateEpoch("09-09", "Europe/Bratislava") == getCurrentYearTimestampWithGivenFormat("dd-MM",
					"09-09", "Europe/Bratislava")) {
				System.out.println("oohoo2");
			}
			System.out.println(getPreviousYearTimestampWithGivenFormat("dd-MM", "32-09", "Europe/Bratislava"));
			System.out.println(getPreviousYearSameDateEpoch("32-09", "Europe/Bratislava"));
			System.out.println(getCurrentYearTimestampWithGivenFormat("dd-MM", "06-12", "Europe/Bratislava"));
			System.out.println(getCurrentYearDateEpoch("06-12", "Europe/Bratislava"));

			String subject = "Domácnosti ako Vaša spotrebujú v zime na kúrenie o %s %% menejelektrickej energie.";
			String subject1 = "Pripravte sa na komfortnú zimu. Pripravte sa, aby ste zimu prežili komfortne.";
			if (!subject.contains("%s")) {
				System.out.println("Does not Contains variable");
			} else {
				System.out.println("Contains variable");
			}
			addTrackingParams(
					"https://www1.elektrozoom.sk/form/?utm_source=email&amp;utm_campaign=bidgely&amp;linktype=afeaf&amp;code=NBgfolLlIFYmoaabxhQgjzr4;iC4fqfY$E5ShArSr;PhDAlK2tHWlclEEjVcFIAFr$");

			// System.out.println("Last year epoch for the same date: " +
			// getPreviousYearTimestamp(date, timezone));
			System.out.println(getUnicodeRepresentation(";"));
			System.out.println(addTrackingParams(
					"https://www1.elektrozoom.sk/form/?utm_source=email&amp;utm_campaign=bidgely&amp;linktype=afeaf&amp;code=NBgfolLlIFYmoaabxhQgjzr4;iC4fqfY$E5ShArSr;PhDAlK2tHWlclEEjVcFIAFr$"));
			replaceURIVariables();

			Map<Integer, Integer> testMap = new HashMap<Integer, Integer>();
			for (Entry<Integer, Integer> entry : testMap.entrySet()) {
				System.out.println("printing");
				System.out.println(entry);
			}
			A ref = new A(5);

			System.out.println(ref.val);
			ref = new A(6);
			System.out.println(ref.val);
			changeRef(ref);
			System.out.println(ref.val);

			String hybridConfig = "true";
			if (true || false || false && false) {

				System.out.println("Going in..");
			}
			String url = "http://eonpatapi.bidgely.com/meta/users/" + "14f3fcf9-31c7-4fba-9faa-be5168243ca5"
					+ "/homes/1/modified";
			getCall(url);

		}
	}

	private static Integer calculateRoundedPercentageOfDifference(Double newNumber, Double oldNumber) {
		if (newNumber != null && newNumber != 0 && oldNumber != null && oldNumber != 0) {
			if (oldNumber > 0) {
				return (int) (((newNumber - oldNumber) * 100) / oldNumber);
			} else if (oldNumber < 0) {
				return -(int) (((newNumber - oldNumber) * 100) / oldNumber);
			} else {
				return (int) Math.round(newNumber);
			}
		}
		return null;
	}

	private static boolean checkStringEqualGivenBackspaces(String s, String t) {
		Stack<Character> stack = new Stack<Character>();
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) != '#') {
				stack.push(s.charAt(i));
			} else if (!stack.isEmpty()) {
				stack.pop();
			}
		}
		String finalS1 = stack.toString();
		stack.clear();
		for (int i = 0; i < t.length(); i++) {
			if (t.charAt(i) != '#') {
				stack.push(t.charAt(i));
			} else if (!stack.isEmpty()) {
				stack.pop();
			}
		}
		String finalT1 = stack.toString();
		return finalT1.equals(finalS1);
	}

	private static boolean findIfAPairExistInArrayWhichSumToK(int[] a, int K) {
		Arrays.sort(a);
		for (int i = 0; i < a.length; i++) {
			if (Arrays.binarySearch(a, K - a[i]) > 0) {
				System.out.println(a[i] + "," + (K - a[i]));
				return true;
			}
		}
		return false;
	}
	// public static int getPreviousYearTimestamp(int timestamp, TimeZone
	// timezone) {
	// final Calendar cal = Calendar.getInstance(timezone);
	// cal.setTimeInMillis(timestamp * TO_LONG);
	// cal.set(Calendar.YEAR, cal.get(Calendar.YEAR) - 1);
	// return (int) (cal.getTimeInMillis() / TO_LONG);
	// }

	private static int getPreviousYearTimestampWithGivenFormat(String dateFarmat, String dateWithOutYear,
			String timeZone) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFarmat);
		TimeZone timeZoneObj = TimeZone.getTimeZone(timeZone);
		simpleDateFormat.setTimeZone(timeZoneObj);
		final Date date = simpleDateFormat.parse(dateWithOutYear);
		final Calendar cal = Calendar.getInstance(timeZoneObj);
		final int currentYear = cal.get(Calendar.YEAR);
		cal.setTime(date);
		cal.set(Calendar.YEAR, currentYear);
		cal.add(Calendar.YEAR, -1);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	private static int getCurrentYearTimestampWithGivenFormat(String dateFarmat, String dateWithOutYear,
			String timeZone) throws ParseException {
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFarmat);
		TimeZone timeZoneObj = TimeZone.getTimeZone(timeZone);
		simpleDateFormat.setTimeZone(timeZoneObj);
		final Date date = simpleDateFormat.parse(dateWithOutYear);
		final Calendar cal = Calendar.getInstance(timeZoneObj);
		final int currentYear = cal.get(Calendar.YEAR);
		cal.setTime(date);
		cal.set(Calendar.YEAR, currentYear);
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * This method returns previous year epoch for the same date and month. input
	 * should be date with out year in the format dd-mm
	 * 
	 * @param dateWithOutYear (dateformat dd-mm)
	 * @param timeZone
	 * @return
	 */
	public static int getPreviousYearSameDateEpoch(String dateWithOutYear, String timeZone) {
		TimeZone timeZoneObj = TimeZone.getTimeZone(timeZone);
		final Calendar cal = Calendar.getInstance(timeZoneObj);
		int digyear = cal.get(Calendar.YEAR) - 1;
		String yrStr = Integer.toString(digyear);
		String dateWithYear = dateWithOutYear + "-" + yrStr;
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT)
				.withZone(DateTimeZone.forTimeZone(timeZoneObj));
		cal.setTimeInMillis(formatter.parseMillis(dateWithYear));
		return (int) (cal.getTimeInMillis() / 1000);
	}

	/**
	 * This method returns current year epoch for the same date and month. input
	 * should be date with out year in the format dd-mm
	 * 
	 * @param dateWithOutYear (dateformat dd-mm)
	 * @param timeZone
	 * @return
	 */
	public static int getCurrentYearDateEpoch(String dateWithOutYear, String timeZone) {
		TimeZone timeZoneObj = TimeZone.getTimeZone(timeZone);
		final Calendar cal = Calendar.getInstance(timeZoneObj);
		int digyear = cal.get(Calendar.YEAR);
		String yrStr = Integer.toString(digyear);
		String dateWithYear = dateWithOutYear + "-" + yrStr;
		final DateTimeFormatter formatter = DateTimeFormat.forPattern(DEFAULT_DATE_FORMAT)
				.withZone(DateTimeZone.forTimeZone(timeZoneObj));
		cal.setTimeInMillis(formatter.parseMillis(dateWithYear));
		return (int) (cal.getTimeInMillis() / 1000);
	}

	private static void regexReplacement() {
		String INACTIVE_TOKEN_PART = ":inactive";
		String master2 = "20012:10000135826-24ZVS0000341837NID05:24ZVS0000341837NID05";
		if (!master2.endsWith("(" + INACTIVE_TOKEN_PART + "[0-9]*)$")) {
			// Only append INACTIVE_TOKEN_PART if it is not already appended
			System.out.println("ends with inactive");
		} else {
			System.out.println("ends with inactive");
		}
		String regexTarget = "(" + INACTIVE_TOKEN_PART + "[0-9]*)$";
		String processed2 = master2.replaceAll(regexTarget, "");
		System.out.println(processed2);
	}

	public static void getApplianceEnergyBreakDownMapAsString() {
		Map<String, Double> applianceEnergyBreakDownMap = new TreeMap<String, Double>();
		applianceEnergyBreakDownMap.put("Heating", 26.0);
		applianceEnergyBreakDownMap.put("Cooking", 21.0);
		applianceEnergyBreakDownMap.put("Entertaintment", 18.0);
		applianceEnergyBreakDownMap.put("Laundry", 13.0);
		applianceEnergyBreakDownMap.put("Always On", 11.0);
		applianceEnergyBreakDownMap.put("Lightning", 8.0);
		applianceEnergyBreakDownMap.put("Others", 3.0);
		StringBuilder applianceEnergyBreakDownMapAsString = new StringBuilder();
		// [["Heating", 26], ["Cooking",21], ["Entertaintment",18],
		// ["Laundry",13],["Always On", 11], ["Lightning", 8], ["Others", 3]];
		applianceEnergyBreakDownMapAsString.append("[");
		Iterator<Entry<String, Double>> itr = applianceEnergyBreakDownMap.entrySet().iterator();
		Entry<String, Double> appEntry = null;
		while (itr.hasNext()) {
			appEntry = itr.next();
			applianceEnergyBreakDownMapAsString.append("[\"" + appEntry.getKey() + "\", ");
			applianceEnergyBreakDownMapAsString.append(appEntry.getValue() + "]");
			if (itr.hasNext()) {
				applianceEnergyBreakDownMapAsString.append(",");
			}
		}
		applianceEnergyBreakDownMapAsString.append("];");
		System.out.println(applianceEnergyBreakDownMapAsString.toString());
	}

	public static int getPreviousYearTimestamp(String dateWithOutYear, TimeZone timezone) {
		final Calendar cal = Calendar.getInstance(timezone);
		int digyear = cal.get(Calendar.YEAR) - 1;
		String yearStr = Integer.toString(digyear);
		String dateWithYear = dateWithOutYear + "-" + yearStr;
		final DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MM-yyyy")
				.withZone(DateTimeZone.forTimeZone(timezone));
		cal.setTimeInMillis(formatter.parseMillis(dateWithYear));
		return (int) (cal.getTimeInMillis() / 1000);
	}

	public static String addTrackingParams(String url) {
		if ((url == null) || (url.isEmpty())) {
			return null;
		}
		UriComponentsBuilder uri = null;
		uri = UriComponentsBuilder.fromHttpUrl(url);
		uri.queryParam(LINK_TYPE_PARAM, "MONTHLY_SUMMARY");
		final String trackedUrl = uri.build().toString();
		return trackedUrl;
	}

	private static String getUnicodeRepresentation(String text) {
		if (text == null) {
			return text;
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (char ch : text.toCharArray()) {
			stringBuilder.append(convertNonAsciiCharToUnicode(ch));
		}
		return stringBuilder.toString();
	}

	private static String convertNonAsciiCharToUnicode(char character) {
		return String.format(UNICODE_FORMAT, (int) character);

	}

	public static void replaceURIVariables() {
		String template = "http://example.com/name/{name}/age/{age}?code={code}";
		UriTemplate uriTemplate = new UriTemplate(template);
		String uri = "http://example.com/name/Bob/age/47?code=x";
		Map<String, String> parameters = new HashMap<String, String>();

		// Not this method returns false if the URI doesn't match, ignored
		// for the purposes of the this blog.
		uriTemplate.match(uri, parameters);
		System.out.println(parameters);
		parameters.put("name", "Arnold");
		parameters.put("age", "110");
		System.out.println(uriTemplate.createURI(parameters));
	}

	public static void changeRef(A ref) {
		// System.out.println(ref.val);
		ref.val = 7;
		// System.out.println(ref);
	}

	private static void getCall(String url) throws ClientProtocolException, IOException {
		//
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		request.addHeader("content-type", "application/json");
		request.addHeader("Authorization", "Bearer 09af7d48-1ee8-4190-8dff-207dab41415a");
		HttpResponse response = httpClient.execute(request);
		System.out.println(response.getStatusLine());

	}

	private static void sendPost2(String url, String email, String zip, String country, String partnerUserId)
			throws Exception {
		System.out.println(url);
		HttpClient httpClient = HttpClientBuilder.create().build();
		try {
			HttpPut request = new HttpPut(url);
			StringEntity params = new StringEntity("{\"userName\":\"" + email + "\",\"email\":\"" + email
					+ "\",\"password\": \"bidgely\",\"pilotId\":20020,\"partnerUserId\":\"" + partnerUserId
					+ "\",\"homeAccounts\":{\"postalCode\":\"" + zip + "\",\"countryCode\":\"" + country + "\"}}");
			System.out.println("{\"userName\":\"" + email + "\",\"email\":\"" + email
					+ "\",\"password\": \"bidgely\",\"pilotId\":20020,\"partnerUserId\":\"" + partnerUserId
					+ "\",\"homeAccounts\":{\"postalCode\":\"" + zip + "\",\"countryCode\":\"" + country + "\"}}");
			request.addHeader("content-type", "application/json");
			request.addHeader("Authorization", "Bearer 09af7d48-1ee8-4190-8dff-207dab41415a");
			request.setEntity(params);
			HttpResponse response = httpClient.execute(request);
			System.out.println(response.getStatusLine());

		} catch (Exception ex) {
			ex.printStackTrace();

		} finally {
		}
	}

	private enum AccountStatusDescription {
		ACTIVE, INACTIVE;

	}
}

class A {
	int val;

	public A(int val) {
		this.val = val;
	}
}

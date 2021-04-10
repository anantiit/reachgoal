package com.reachgoal.assignments.stockexchange;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.reachgoal.core_utils.Daemonizer;

public class StockExchange extends Daemonizer {

	private static String filePath;
	private static String DELIMETER = " ";
	public static final Set<String> companyCodes = new HashSet<String>();
	private static final String TIMEZONE = "Asia/Kolkata";
	private static final String DATE_FORMAT = "dd-mm-yyyy";
	private static final String DATE_TIME_FORMAT = "dd-mm-yyyy HH:mm";
	private static final long MILLI_SEC_IN_SEC = 1000L;
	private static final String BUY_ORDER = "buy";
	private static final int ORDER_INPUT_LENGTH = 6;

	private static final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
	private static final SimpleDateFormat dateTimeFormatter = new SimpleDateFormat(DATE_TIME_FORMAT);

	static {
		dateFormatter.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
		dateTimeFormatter.setTimeZone(TimeZone.getTimeZone(TIMEZONE));
	}

	public static void main(String[] args) throws ParseException {
		StockExchange stockExchange = new StockExchange();
		if (("-f").equalsIgnoreCase(args[0]) && args.length >= 2) {
			filePath = args[1];
		}
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] orderDetails = line.split(DELIMETER);
				Order order = stockExchange.validateAndGetOrder(orderDetails);
				OrderStatus orderStatus = stockExchange.processOrder(order);
			}
		} catch (final FileNotFoundException e) {
			System.out.println(filePath + " file not found. Hence exiting..");
		} catch (final IOException e) {
			System.out.println(filePath + " could not open file, Hence exiting..");
		}
	}

	private OrderStatus processOrder(Order order) {
		OrderStatus orderStatus = OrderStatus.OPEN;
		return orderStatus;
	}

	protected void runDaemons() throws ParseException {

		List<String> tasks = new ArrayList<>();
		if (!tasks.isEmpty()) {
			final List<String> taskArgs = Arrays.asList(cmdLine.getOptionValues(EMAILERS));
			logger.info("Intitializing EmailerDaemon with emailers specified in arguments: " + taskArgs);
			if (countForAll != null) {
				setTasks(getTasks(taskArgs, countForAll, context));
			} else {
				setTasks(getTasks(taskArgs, counts, context));
			}
		} else {
			logger.info("Intitializing EmailerDaemon with tasks: " + this.tasks);
			if (countForAll != null) {
				setTasks(getTasks(this.tasks, Integer.valueOf(countForAll)));
			} else {
				setTasks(this.tasks);
			}
		}

		setExecutor(Executors.newFixedThreadPool(getTasks().size()));

		try {
			init();
		} catch (final Throwable e) {
			logger.error("Error while running emailer daemon", e);
			throw e;
		}
	}

	private Order validateAndGetOrder(String[] orderDetails) throws ParseException {
		Order order = null;
		if (orderDetails.length == ORDER_INPUT_LENGTH) {
			String number = orderDetails[0];
			String time = orderDetails[1];
			String stockName = orderDetails[2];
			String orderType = orderDetails[3];
			String price = orderDetails[4];
			String quantity = orderDetails[5];

			int orderNum = Integer.parseInt(number.split("#")[1]);
			Date date = new Date();
			String todayDate = dateFormatter.format(date);
			String dateTime = todayDate + time;
			Date dateTimeObj = dateTimeFormatter.parse(dateTime);
			int epoch = (int) (dateTimeObj.getTime() / MILLI_SEC_IN_SEC);
			double priceValue = Double.parseDouble(price);
			int orderQuantity = Integer.parseInt(quantity);
			order = new Order(orderNum, epoch, stockName, BUY_ORDER.equalsIgnoreCase(orderType), priceValue,
					orderQuantity, OrderStatus.OPEN);
		}
		return order;
	}

	private void run() throws InterruptedException {
		final ExecutorService executorService = Executors.newFixedThreadPool(10, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				final Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
	}

}

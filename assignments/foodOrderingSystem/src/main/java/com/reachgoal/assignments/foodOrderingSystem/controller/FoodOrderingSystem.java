package com.reachgoal.assignments.foodOrderingSystem.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.reachgoal.assignments.foodOrderingSystem.entities.Item;
import com.reachgoal.assignments.foodOrderingSystem.entities.MenuItem;
import com.reachgoal.assignments.foodOrderingSystem.entities.Order;
import com.reachgoal.assignments.foodOrderingSystem.services.OrderExecutor;
import com.reachgoal.core_utils.ThreadExecutor;

public class FoodOrderingSystem {
	private static String filePath;
	public static String TEST_DIRECTORY = "src/main/resources/";
	public static Integer DEFAULT_THREAD_COUNT = 50;
	private static String DELIMETER = " ";

	// public static ConcurrentLinkedQueue<Order> orderQueue = new
	// ConcurrentLinkedQueue<Order>();

	public static void main(String[] args) throws InterruptedException {

		if (("-f").equalsIgnoreCase(args[0]) && args.length >= 2) {
			filePath = args[1];
		}
		final FoodOrderingSystem foodOrderSystem = new FoodOrderingSystem();
		MenuItem mItem1 = new MenuItem("A2B", "pongal", 1000, 23d);
		MenuItem mItem2 = new MenuItem("A2B", "upma", 1000, 23d);
		MenuItem mItem3 = new MenuItem("A2B", "idli", 1000, 23d);
		MenuItem mItem4 = new MenuItem("A2B1", "pongal", 5, 10d);

		foodOrderSystem.createRestaurent();
		foodOrderSystem.createUser();
		foodOrderSystem.runFoodOrderingSystem();
	}

	private void createUser() {
		// TODO Auto-generated method stub

	}

	private void createRestaurent(String restName, Set<MenuItem> menu) {

	}

	public Order validateAndGetOrder(String[] orderDetails) {
		String orderId = orderDetails[0];
		if ((orderDetails.length - 1) % 2 != 0) {
			System.out.println("Invalid order");
		}
		Map<Item, Integer> itemQuantityMap = new HashMap<Item, Integer>();
		for (int i = 1; i < orderDetails.length; i = i + 2) {
			itemQuantityMap.put(new Item(orderDetails[i]), Integer.parseInt(orderDetails[i + 1]));
		}
		return new Order(orderId, itemQuantityMap);
	}

	private void runFoodOrderingSystem() throws InterruptedException {
		final ExecutorService executorService = Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				final Thread t = Executors.defaultThreadFactory().newThread(r);
				t.setDaemon(true);
				return t;
			}
		});
		final ThreadExecutor orderExecutor = new ThreadExecutor(executorService);
		try (BufferedReader reader = new BufferedReader(new FileReader(TEST_DIRECTORY + filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				final String[] orderDetails = line.split(DELIMETER);
				Order order = validateAndGetOrder(orderDetails);
				processOrder(order, orderExecutor);
			}

		} catch (final FileNotFoundException e) {
			System.out.println(filePath + " file not found. Hence exiting..");
		} catch (final IOException e) {
			System.out.println(filePath + " could not open file, Hence exiting..");
		}
	}

	public void processOrder(Order order, ThreadExecutor orderExecutor) {

		submitTask(orderExecutor, order);

	}

	private void submitTask(ThreadExecutor orderExecuter, Order order) {
		try {
			if (order != null) {
				orderExecuter.submitTask(new OrderExecutor(order));
			} else {
				System.out.println("order content is empty");
			}
		} catch (final Exception e) {
			System.out.println("Unexpected exception while submitting task " + e);
		}
	}

}

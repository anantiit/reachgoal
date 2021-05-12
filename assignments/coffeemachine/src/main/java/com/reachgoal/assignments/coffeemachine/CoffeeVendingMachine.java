package com.reachgoal.assignments.coffeemachine;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.google.gson.Gson;
import com.reachgoal.assignments.coffeemachine.data.Machine;
import com.reachgoal.assignments.coffeemachine.data.MachineWrapper;
import com.reachgoal.core_utils.ThreadExecutor;

public class CoffeeVendingMachine {
	private static String filePath;
	public static String TEST_DIRECTORY = "src/main/resources/";

	public static void main(String[] args) {

		if (("-f").equalsIgnoreCase(args[0]) && args.length >= 2) {
			filePath = args[1];
		}
		try (FileReader reader = new FileReader(TEST_DIRECTORY + filePath)) {
			Gson gson = new Gson();
			MachineWrapper machine = gson.fromJson(reader, MachineWrapper.class);
			final CoffeeVendingMachine coffeeMachine = new CoffeeVendingMachine();
			coffeeMachine.runCoffeeVedingMachine(machine.getMachine());
		} catch (final FileNotFoundException e) {
			System.out.println(filePath + " file not found. Hence exiting..");
		} catch (final IOException e) {
			System.out.println(filePath + " could not open file, Hence exiting..");
		} catch (InterruptedException e) {
			System.out.println("Coffee machine outlet thread is interrrupted..");
		}

	}

	private void runCoffeeVedingMachine(Machine machine) throws InterruptedException {
		final ExecutorService executorService = Executors.newFixedThreadPool(machine.getOutlets().getOutLetCount(),
				new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						final Thread t = Executors.defaultThreadFactory().newThread(r);
						t.setDaemon(true);
						return t;
					}
				});
		final ThreadExecutor outLetExecutor = new ThreadExecutor(executorService);
		try {
			if (machine.getBeverages() != null && !machine.getBeverages().isEmpty()) {
				Iterator<Entry<String, HashMap<String, Integer>>> beverageEntryIterator = machine.getBeverages()
						.entrySet().iterator();
				while (beverageEntryIterator.hasNext()) {
					Entry<String, HashMap<String, Integer>> beverageEntry = beverageEntryIterator.next();
					submitTask(outLetExecutor, beverageEntry.getKey(), beverageEntry.getValue(), machine);
				}
			}
		} finally {
			// System.out.println("shutting down executorService");
			outLetExecutor.shutdown();
		}
	}

	private void submitTask(ThreadExecutor outLetExecutor, String beverageName, Map<String, Integer> beverage,
			Machine machine) {
		try {
			if (beverage != null && !beverage.isEmpty()) {
				outLetExecutor.submitTask(new Outlet(beverageName, beverage, machine));
			} else {
				System.out.println("beverage is empty for " + beverageName);
			}
		} catch (final Exception e) {
			System.out.println("Unexpected exception while submitting task " + e);
		}
	}

}

class Outlet implements Runnable {
	String beverageName;
	Map<String, Integer> beverage;
	Machine machine;

	public Outlet(String beverageName, Map<String, Integer> beverage, Machine machine) {
		super();
		this.beverageName = beverageName;
		this.beverage = beverage;
		this.machine = machine;
	}

	boolean validateBeverage() {
		ConcurrentHashMap<String, Integer> availableIngrediants = machine.getTotalIngrediantsMap();
		Iterator<Entry<String, Integer>> beverageIngrediantEntries = beverage.entrySet().iterator();
		while (beverageIngrediantEntries.hasNext()) {
			Entry<String, Integer> beverageIngrediantEntry = beverageIngrediantEntries.next();
			Integer availableQuantity = availableIngrediants.get(beverageIngrediantEntry.getKey());
			if ((availableQuantity == null && beverageIngrediantEntry.getValue() != null
					&& beverageIngrediantEntry.getValue() > 0)
					|| (beverageIngrediantEntry.getValue() != null
							&& availableQuantity < beverageIngrediantEntry.getValue())) {
				System.out.println(beverageName + " cannot be prepared because item " + beverageIngrediantEntry.getKey()
						+ " is not available");
				return false;
			}
		}
		return true;
	}

	void serveBeverage() {
		ConcurrentHashMap<String, Integer> availableIngrediants = machine.getTotalIngrediantsMap();
		Iterator<Entry<String, Integer>> beverageIngrediantEntries = beverage.entrySet().iterator();
		while (beverageIngrediantEntries.hasNext()) {
			Entry<String, Integer> beverageIngrediantEntry = beverageIngrediantEntries.next();
			int availableQuantity = availableIngrediants.get(beverageIngrediantEntry.getKey());
			if (beverageIngrediantEntry.getValue() != null
					&& availableQuantity - beverageIngrediantEntry.getValue() > 0) {
				availableIngrediants.put(beverageIngrediantEntry.getKey(),
						availableQuantity - beverageIngrediantEntry.getValue());
			}
		}
		System.out.println(beverageName + " is prepared");
	}

	@Override
	public void run() {
		try {
			synchronized (Machine.class) {
				if (validateBeverage()) {
					serveBeverage();
				}
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

}

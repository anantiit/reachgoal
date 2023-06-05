package com.csfundamentals.algo.problemsolving;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// Main class should be named 'Solution' and should not be public.
public class PaymentSettlement {

	public static Map<String, Map<String, Double>> dailyTransactionAggregator(List<Transaction> transactions) {

		// create map to hold daily aggregates
		Map<String, Map<String, Double>> dailyAggregates = new HashMap<>();

		// iterate over transactions and aggregate by day, payer, and payee
		for (Transaction transaction : transactions) {
			String date = transaction.getDate();
			String payer = transaction.getPayer();
			String payee = transaction.getPayee();
			Double amount = transaction.getAmount();

			Map<String, Double> payerAggregates = dailyAggregates.get(date);
			if (payerAggregates == null) {
				payerAggregates = new HashMap<>();
				dailyAggregates.put(date, payerAggregates);
			}
			Double newValue = payerAggregates.computeIfPresent(payer + "-" + payee, (k, v) -> v + amount);
			if (newValue == null) {
				Double reverse = payerAggregates.computeIfPresent(payee + "-" + payer, (k, v) -> v - amount);
				if (reverse == null) {
					payerAggregates.put(payer + "-" + payee, amount);
				}
			}
		}
		return dailyAggregates;
	}

	public static Map<String, HashMap<String, Double>> settlement(List<Transaction> transactions) {
		Map<String, HashMap<String, Double>> settlement = new HashMap<String, HashMap<String, Double>>();
		for (Transaction t : transactions) {
			HashMap<String, Double> tMap = settlement.get(t.payer);
			if (tMap == null) {
				tMap = new HashMap<String, Double>();
			}
			Double prevAmount = tMap.getOrDefault(t.payee, 0d);
			Double newAmount = prevAmount + t.amount;
			tMap.put(t.payee, newAmount);
			settlement.put(t.payer, tMap);
		}
		mergeMap(settlement);
		return settlement;
	}

	public static void mergeMap(Map<String, HashMap<String, Double>> settlement) {
		Iterator<String> itr1 = settlement.keySet().iterator();
		while (itr1.hasNext()) {
			String payer = itr1.next();
			HashMap<String, Double> tMap = settlement.get(payer);
			Iterator<Entry<String, Double>> itr = tMap.entrySet().iterator();
			while (itr.hasNext()) {
				Entry<String, Double> e = itr.next();
				String payee = e.getKey();
				Double reverseTransaction = settlement.get(payee).get(payer);
				if (reverseTransaction != null) {
					if (reverseTransaction > e.getValue()) {
						settlement.get(payee).put(payer, reverseTransaction - e.getValue());
						itr.remove();
					} else {
						e.setValue(e.getValue() - reverseTransaction);
						settlement.get(payee).remove(payer);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("BoA", 132d, "Chase"));
		transactions.add(new Transaction("BoA", 827d, "Chase"));
		transactions.add(new Transaction("Wells Fargo", 1000d, "BoA"));
		transactions.add(new Transaction("BoA", 585d, "Chase"));
		transactions.add(new Transaction("Chase", 877d, "Wells Fargo"));
		transactions.add(new Transaction("Wells Fargo", 157d, "Chase"));
		transactions.add(new Transaction("Wells Fargo", 904d, "Chase"));
		transactions.add(new Transaction("Chase", 548d, "Wells Fargo"));
		transactions.add(new Transaction("Chase", 976d, "BoA"));
		transactions.add(new Transaction("BoA", 872d, "Wells Fargo"));
		transactions.add(new Transaction("Wells Fargo", 571d, "Chase"));
		System.out.println(settlement(transactions));
		System.out.println(settlement2(transactions));

		// create sample transaction data
		List<Transaction> transactions1 = new ArrayList<>();
		transactions1.add(new Transaction("Alice", "Bob", 50.00, "2023-04-17"));
		transactions1.add(new Transaction("Bob", "Alice", 25.00, "2023-04-17"));
		transactions1.add(new Transaction("Charlie", "Alice", 75.00, "2023-04-17"));
		transactions1.add(new Transaction("Alice", "Bob", 100.00, "2023-04-16"));
		transactions1.add(new Transaction("Bob", "Alice", 125.00, "2023-04-16"));
		System.out.println(dailyTransactionAggregator(transactions1));
	}

	private static Map<String, HashMap<String, Double>> settlement2(List<Transaction> transactions) {
		Map<String, HashMap<String, Double>> transactionsMap = new HashMap<String, HashMap<String, Double>>();
		HashMap<String, Double> payerMap = null;
		HashMap<String, Double> payeeMap = null;
		for (Transaction transaction : transactions) {
			payerMap = null;
			payeeMap = null;
			if (transactionsMap.containsKey(transaction.payer) && transactionsMap.containsKey(transaction.payee)) {
				payerMap = transactionsMap.get(transaction.payer);
				Double v1 = payerMap.computeIfPresent(transaction.payee, (k, v) -> v + transaction.amount);
				if (v1 == null) {
					payeeMap = transactionsMap.get(transaction.payee);
					Double v2 = payeeMap.computeIfPresent(transaction.payer, (k, v) -> v - transaction.amount);
					if (v2 == null)
						payerMap.put(transaction.payee, transaction.amount);
				}
			} else if (transactionsMap.containsKey(transaction.payer)) {
				payerMap = transactionsMap.get(transaction.payer);
				Double v1 = payerMap.computeIfPresent(transaction.payee, (k, v) -> v + transaction.amount);
				if (v1 == null) {
					payerMap.put(transaction.payee, transaction.amount);
				}
			} else if (transactionsMap.containsKey(transaction.payee)) {
				payeeMap = transactionsMap.get(transaction.payee);
				Double v1 = payeeMap.computeIfPresent(transaction.payer, (k, v) -> v - transaction.amount);
				if (v1 == null) {
					payeeMap.put(transaction.payer, -transaction.amount);
				}
			} else {
				payerMap = new HashMap<String, Double>();
				payerMap.put(transaction.payee, transaction.amount);
				transactionsMap.put(transaction.payer, payerMap);
			}
		}
		return transactionsMap;
	}

}

class Transaction {
	String payer;
	String payee;
	protected Double amount;
	String date;

	public Transaction(String payer, String payee, Double amount, String date) {
		this.payer = payer;
		this.payee = payee;
		this.amount = amount;
		this.date = date;
	}

	public Transaction(String payer, Double amount, String payee) {
		this.payer = payer;
		this.payee = payee;
		this.amount = amount;
	}

	public String getPayer() {
		return payer;
	}

	public String getPayee() {
		return payee;
	}

	public Double getAmount() {
		return amount;
	}

	public String getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Transaction [payer=" + payer + ", payee=" + payee + ", amount=" + amount + "]";
	}

}

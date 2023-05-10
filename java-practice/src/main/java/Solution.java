import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

// Main class should be named 'Solution' and should not be public.
class Solution {
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
		transactions.add(new Transaction("Wells Fargo", 751d, "BoA"));
		transactions.add(new Transaction("BoA", 585d, "Chase"));
		transactions.add(new Transaction("Chase", 877d, "Wells Fargo"));
		transactions.add(new Transaction("Wells Fargo", 157d, "Chase"));
		transactions.add(new Transaction("Wells Fargo", 904d, "Chase"));
		transactions.add(new Transaction("Chase", 548d, "Wells Fargo"));
		transactions.add(new Transaction("Chase", 976d, "BoA"));
		transactions.add(new Transaction("BoA", 872d, "Wells Fargo"));
		transactions.add(new Transaction("Wells Fargo", 571d, "Chase"));
		System.out.println(settlement(transactions));
	}

}

class Transaction {
	String payee;
	String payer;
	Double amount;
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
}

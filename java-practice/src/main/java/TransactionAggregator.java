import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionAggregator {

	public static void main(String[] args) {

		// create sample transaction data
		List<Transaction> transactions = new ArrayList<>();
		transactions.add(new Transaction("Alice", "Bob", 50.00, "2023-04-17"));
		transactions.add(new Transaction("Bob", "Alice", 25.00, "2023-04-17"));
		transactions.add(new Transaction("Charlie", "Alice", 75.00, "2023-04-17"));
		transactions.add(new Transaction("Alice", "Bob", 100.00, "2023-04-16"));
		transactions.add(new Transaction("Bob", "Alice", 75.00, "2023-04-16"));

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

			Double currentAmount = payerAggregates.get(payer + "-" + payee);
			if (currentAmount == null) {
				currentAmount = 0.0;
			}
			payerAggregates.put(payer + "-" + payee, currentAmount + amount);
		}

		// print results
		for (Map.Entry<String, Map<String, Double>> dailyAggregate : dailyAggregates.entrySet()) {
			System.out.println("Daily aggregates for " + dailyAggregate.getKey() + ":");
			for (Map.Entry<String, Double> payerAggregate : dailyAggregate.getValue().entrySet()) {
				System.out.println(payerAggregate.getKey() + ": " + payerAggregate.getValue());
			}
			System.out.println();
		}
	}
}

package lld.splitwise.balance;

import java.util.HashMap;
import java.util.Map;

import lld.splitwise.account.User;
import lld.splitwise.expense.ExpenseSplit;

public class AccountController {
	static final Map<String, Account> userFinalBalanceMap = new HashMap<String, Account>();
	static final Map<String, HashMap<String, Double>> payerPayeeMap = new HashMap<String, HashMap<String, Double>>();
	static final Map<String, HashMap<String, Double>> payeePayerMap = new HashMap<String, HashMap<String, Double>>();

	public void updateUserBalance(ExpenseSplit expenseSplit, User payer, double expenseAmount) {
		String userId = expenseSplit.getUser().getUserId();
		double balanceAmount = -expenseSplit.getUserShare();
		double amountPaid = 0;
		if (payer.getUserId().equals(expenseSplit.getUser().getUserId())) {
			balanceAmount = expenseAmount + balanceAmount;
			amountPaid = expenseAmount;
		}
		double expenditure = expenseSplit.getUserShare();
		Account userBalance = userFinalBalanceMap.getOrDefault(userId, new Account(expenseSplit.getUser()));
		userBalance.totalAmountPaid = userBalance.totalAmountPaid + amountPaid;
		userBalance.totalExpenditure = userBalance.totalExpenditure + expenditure;
		userBalance.totalBalanceAmount = userBalance.totalBalanceAmount + balanceAmount;

		userFinalBalanceMap.put(userId, userBalance);
		updateUserBalanceSplit(expenseSplit, payer, expenseAmount);
	}

	public void updateUserBalanceSplit(ExpenseSplit expenseSplit, User payer, double expenseAmount) {
		String payeeId = expenseSplit.getUser().getUserId();
		String payerId = payer.getUserId();
		if (payerId.equals(payeeId)) {
			return;
		}
		HashMap<String, Double> payerMap = payeePayerMap.getOrDefault(payeeId, new HashMap<String, Double>());
		HashMap<String, Double> payeeMap = payerPayeeMap.getOrDefault(payerId, new HashMap<String, Double>());
		Double payeePreviouseBalance = payeeMap.getOrDefault(payeeId, 0d);
		payeeMap.put(payeeId, payeePreviouseBalance + expenseSplit.getUserShare());
		Double payerPreviouseBalance = payerMap.getOrDefault(payerId, 0d);
		payerMap.put(payerId, payerPreviouseBalance - expenseSplit.getUserShare());
		payeePayerMap.putIfAbsent(payeeId, payerMap);
		payerPayeeMap.putIfAbsent(payerId, payeeMap);

	}

	public void getAllUserBalances() {
		for (String userId : userFinalBalanceMap.keySet()) {
			System.out.println("----user:" + userId + "----");
			System.out.println(userFinalBalanceMap.get(userId));
			System.out.println("Amounts user to get back");
			System.out.println(payerPayeeMap.get(userId));
			System.out.println("Amounts he owe");
			System.out.println(payeePayerMap.get(userId));
		}

	}

	public void getUserBalance(String userId) {
		// TODO Auto-generated method stub

	}
}

package lld.splitwise.split;

import java.util.ArrayList;
import java.util.List;

import lld.splitwise.account.User;
import lld.splitwise.expense.ExpenseSplit;

public class UnEqualSplit implements Split {

	@Override
	public List<ExpenseSplit> validateAndGetTheShares(double amount, List<User> users, List<Double> unEqualShares) {
		List<ExpenseSplit> result = new ArrayList<ExpenseSplit>();
		int n = users.size();
		for (int i = 0; i < n; i++) {
			result.add(new ExpenseSplit(amount / unEqualShares.get(i), users.get(i)));
		}
		return result;

	}

}

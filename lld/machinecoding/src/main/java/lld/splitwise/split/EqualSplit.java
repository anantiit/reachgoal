package lld.splitwise.split;

import java.util.ArrayList;
import java.util.List;

import lld.splitwise.account.User;
import lld.splitwise.expense.ExpenseSplit;

public class EqualSplit implements Split {
	@Override
	public List<ExpenseSplit> validateAndGetTheShares(double amount, List<User> users,
			List<Double> percentagesOrShares) {
		List<ExpenseSplit> result = new ArrayList<ExpenseSplit>();
		int n = users.size();
		double equalAmount = amount / n;
		for (User user : users) {
			result.add(new ExpenseSplit(equalAmount, user));
		}
		return result;
	}

}

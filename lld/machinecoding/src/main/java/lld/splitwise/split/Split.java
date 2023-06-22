package lld.splitwise.split;

import java.util.List;

import lld.splitwise.account.User;
import lld.splitwise.expense.ExpenseSplit;

public interface Split {

	public List<ExpenseSplit> validateAndGetTheShares(double amount, List<User> users,
			List<Double> percentagesOrShares);
}

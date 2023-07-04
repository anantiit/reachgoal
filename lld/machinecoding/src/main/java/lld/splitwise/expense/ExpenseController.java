package lld.splitwise.expense;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import lld.splitwise.account.User;
import lld.splitwise.balance.AccountController;
import lld.splitwise.split.Split;
import lld.splitwise.split.SplitFactory;
import lld.splitwise.split.SplitType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ExpenseController {
	static Random random = new Random();
	AccountController userBalanceController;
	static Map<String, Expense> expenses = new HashMap<String, Expense>();

	public void addExpense(User payer, double expenseAmount, SplitType splitType, ExpenseType expenseType,
			List<User> users, List<Double> percentageOrShares) {
		String expenseID = "e" + random.nextInt();
		Split splitAlg = SplitFactory.getSplitInstance(splitType);
		List<ExpenseSplit> splits = null;
		splits = splitAlg.validateAndGetTheShares(expenseAmount, users, percentageOrShares);
		Expense expense = new Expense(expenseID, expenseAmount, LocalDate.now(), payer, splits, ExpenseType.PAIR);
		saveOrModifyExpense(expense);
		for (ExpenseSplit expenseSplit : splits) {
			userBalanceController.updateUserBalance(expenseSplit, payer, expenseAmount);
		}
	}

	private void saveOrModifyExpense(Expense expense) {
		expenses.put(expense.getExpenseId(), expense);
	}

	public void addGroupExpense(User payer, User friend, double amount, SplitType splitType, List<User> users,
			List<Double> percentageOrShares) {

	}

}

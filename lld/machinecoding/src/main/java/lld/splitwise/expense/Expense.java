package lld.splitwise.expense;

import java.time.LocalDate;
import java.util.List;

import lld.splitwise.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Expense {
	String expenseId;
	double amount;
	LocalDate date;
	User payer;
	List<ExpenseSplit> splits;
	ExpenseType expenseType;

}

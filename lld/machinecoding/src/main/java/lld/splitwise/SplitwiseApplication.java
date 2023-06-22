package lld.splitwise;

import java.util.ArrayList;
import java.util.List;

import lld.splitwise.account.AccountController;
import lld.splitwise.account.Group;
import lld.splitwise.account.User;
import lld.splitwise.balance.UserBalanceController;
import lld.splitwise.expense.ExpenseSplitController;
import lld.splitwise.expense.ExpenseType;
import lld.splitwise.split.SplitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class SplitwiseApplication {
	UserBalanceController userBalanceController;
	ExpenseSplitController expenseSplitController;
	AccountController accountController;
	UserBalanceController userBalanceSheetController;

	public static void main(String[] args) {
		UserBalanceController userBalanceController = new UserBalanceController();
		SplitwiseApplication splitwiseApplication = new SplitwiseApplication(userBalanceController,
				new ExpenseSplitController(userBalanceController), new AccountController(),
				new UserBalanceController());
		ExpenseSplitController expenseSplitController = splitwiseApplication.getExpenseSplitController();
		AccountController accountController = splitwiseApplication.getAccountController();
		UserBalanceController userBalanceSheetController = splitwiseApplication.getUserBalanceSheetController();
		SplitwiseManager.initializeSplitWise(accountController);
		User u1 = accountController.getUser("u1");
		User u2 = accountController.getUser("u2");
		User u3 = accountController.getUser("u3");
		User u4 = accountController.getUser("u4");
		Group g1 = accountController.getGroup("g1");
		// public void addFriendExpense(User payer, double expenseAmount,
		// SplitType splitType, List<User> users, List<Double> percentageOrShares) {
		expenseSplitController.addExpense(u1, 200d, SplitType.EQUAL, ExpenseType.PAIR,
				new ArrayList<User>(List.of(u1, u2)), null);
		List<User> groupMembers = new ArrayList<User>();
		groupMembers.addAll(g1.getMembers());
		userBalanceSheetController.getAllUserBalances();
		expenseSplitController.addExpense(u2, 1000d, SplitType.EQUAL, ExpenseType.GROUP, groupMembers, null);
		userBalanceSheetController.getAllUserBalances();
		// userBalanceSheetController.getUserBalance("u1");

	}

}

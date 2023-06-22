package lld.splitwise.balance;

import lld.splitwise.account.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BalanceSplit {
	User payer;
	User payee;
	// Expense expense;
	// this is need only if we want to maintain for each
	// transaction what is the how much payee owes to payer
	double amount; // payee owes this amount to payer for the expense mentioned

}

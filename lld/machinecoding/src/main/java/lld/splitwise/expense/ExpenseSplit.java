package lld.splitwise.expense;

import lld.splitwise.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ExpenseSplit {
	double userShare;
	User user;

}
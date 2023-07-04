package lld.splitwise.balance;

import lld.splitwise.account.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Account {
	Account(User user) {
		this.user = user;
	}

	User user;
	double totalBalanceAmount;
	double totalAmountPaid;
	double totalExpenditure;
}

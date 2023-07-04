package digitalwallet.wallet;

import digitalwallet.user.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Wallet {
	User user;
	Double amount;
}

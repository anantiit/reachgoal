package digitalwallet.wallet;

import digitalwallet.Constants;
import digitalwallet.exceptions.TransferMinimumLimitException;
import digitalwallet.user.UserService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WalletService {
	UserService userService;
	WalletRepository walletRepository;
	private static Object lock = new Object();

	public boolean createWallet(String name, String emailId, String phoneNum) {
		String walletId = WalletIdGenerator.generateId().toString();
		userService.createUser(name, emailId, phoneNum, walletId);
		userService.createWallet(user);
		return true;
	}

	public boolean transferMoney(int sender, int reciever, double amount) {
		if (amount < Constants.MINIMUM_TRANSFER_AMOUNT) {
			throw new TransferMinimumLimitException();
		}
		synchronized (lock) {
			walletRepository.deleteMoneyToWallet(sender, amount);
			walletRepository.addMoneyToWallet(reciever, amount);
		}
		return true;
	}

	private boolean validateUserInput(String name, String emailId, String phoneNum) {
		return true;

	}

}

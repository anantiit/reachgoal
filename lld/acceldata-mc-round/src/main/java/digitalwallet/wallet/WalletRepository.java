package digitalwallet.wallet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import digitalwallet.exceptions.WalletOrUserNotExistException;

public class WalletRepository {
	public static final Map<Integer, Wallet> wallets = new ConcurrentHashMap<>();

	public boolean addMoneyToWallet(Integer walletId, Double amount) {
		Wallet wallet = wallets.get(walletId);
		if (wallet != null) {
			wallet.setAmount(wallet.getAmount() + amount);
		} else {
			throw new WalletOrUserNotExistException("user does not exist");
		}
		return true;
	}

	public boolean deleteMoneyToWallet(Integer walletId, Double amount) {

		Wallet wallet = wallets.get(walletId);
		if (wallet != null) {
			wallet.setAmount(wallet.getAmount() + amount);
		} else {
			throw new WalletOrUserNotExistException("user does not exist");
		}
		return true;

	}
}

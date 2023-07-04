package digitalwallet;

import java.util.Scanner;

import digitalwallet.user.UserService;
import digitalwallet.wallet.WalletRepository;
import digitalwallet.wallet.WalletService;

/*
 * 
 * You are supposed to create a digital wallet system that allows people to transfer amounts between their wallets.
The wallet uses its own currency known as (A₹).
The account balance cannot drop below A₹ 0.00.
The smallest amount that can be transferred between wallets is 0.0001.



The user should be presented with options for each action. And the options are as follows:
Create Wallet – This option should create a wallet for the user.
Transfer Amount – This option should enable the transfer of funds from one account to the other.
Account Statement – This option should display the account statement for the specified user.
Overview – This option should display all the account numbers currently in the system. Additionally, it should show the current balances for these accounts.
Exit – The system should exit.

 */
public class DigitalWalletMain {
	private static WalletService walletService;
	private static WalletRepository walletRepository;
	private static UserService userService;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		userService = new UserService();
		walletRepository = new WalletRepository();
		walletService = new WalletService(userService, walletRepository);
		while (true) {
			// CREATE_WALLET(1), TRANSFER_AMOUNT(2), ACCOUNT_STMT(3), OVERVIEW(4), EXIT(5);
			OperationType type = OperationType.getOperationById(sc.nextInt());
			switch (type) {
			case CREATE_WALLET:
				System.out.println("Enter user email");
				String emailId = sc.next();
				walletService.createWallet(emailId, emailId, "1424223");
			case TRANSFER_AMOUNT:
				System.out.println("Transfer amount");
				Integer sender = sc.nextInt();
				Integer reciever = sc.nextInt();
				Double amount = sc.nextDouble();
				walletService.transferMoney(sender, reciever, amount);
			case ACCOUNT_STMT:
			case OVERVIEW:
			case EXIT:
			default:
				System.out.println(
						"Allowed operations are only CREATE_WALLET(1), TRANSFER_AMOUNT(2), ACCOUNT_STMT(3), OVERVIEW(4), EXIT(5) ");

			}

		}
	}

}

package digitalwallet;

public enum OperationType {
	/*
	 * Create Wallet – This option should create a wallet for the user. Transfer
	 * Amount – This option should enable the transfer of funds from one account to
	 * the other. Account Statement – This option should display the account
	 * statement for the specified user. Overview – This option should display all
	 * the account numbers currently in the system. Additionally, it should show the
	 * current balances for these accounts. Exit – The system should exit.
	 */

	CREATE_WALLET(1), TRANSFER_AMOUNT(2), ACCOUNT_STMT(3), OVERVIEW(4), EXIT(5);

	int value;

	OperationType(int i) {
		this.value = i;
	}

	public static OperationType getOperationById(int i) {
		for (OperationType type : OperationType.values()) {
			if (type.value == i) {
				return type;
			}
		}
		return null;
	}
}

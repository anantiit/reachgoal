package digitalwallet.exceptions;

public class WalletOrUserNotExistException extends RuntimeException {

	String message;

	public WalletOrUserNotExistException(String message) {
		super(message);
	}
}
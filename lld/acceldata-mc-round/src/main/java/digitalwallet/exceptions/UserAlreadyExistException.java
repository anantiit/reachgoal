package digitalwallet.exceptions;

public class UserAlreadyExistException extends RuntimeException {
	String message;

	public UserAlreadyExistException(String message) {
		super(message);
	}
}

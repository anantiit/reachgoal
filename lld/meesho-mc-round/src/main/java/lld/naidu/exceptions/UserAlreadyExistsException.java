package lld.naidu.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
	String msg;

	public UserAlreadyExistsException(String msg) {
		super(msg);
	}
}

package lld.naidu.exceptions;

public class ReservationLimitExceededException extends RuntimeException {
	String msg;

	public ReservationLimitExceededException(String msg) {
		super(msg);

	}
}

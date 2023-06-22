package lld.checkrandom;

import org.mindrot.jbcrypt.BCrypt;

public class PinVerifier {
	public static void main(String[] args) {
		String enteredPin = "1234"; // Replace with the entered PIN
		String enteredPin1 = "1235";
		String storedSaltedHash = "$2a$10$Gp1AM2rj2pNqZvUhiw/z6uE13C/b/T8YWvroXsr56Qs1q4B1s.DZG"; // Replace with the
																									// stored salted
																									// hash

		// Verify the PIN
		boolean isMatch = BCrypt.checkpw(enteredPin, storedSaltedHash);

		// Print the result
		if (isMatch) {
			System.out.println("PIN is valid");
		} else {
			System.out.println("PIN is invalid");
		}

		isMatch = BCrypt.checkpw(enteredPin1, storedSaltedHash);

		// Print the result
		if (isMatch) {
			System.out.println("PIN is valid");
		} else {
			System.out.println("PIN is invalid");
		}
	}
}

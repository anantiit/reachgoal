package lld.checkrandom;
import org.mindrot.jbcrypt.BCrypt;

public class PinHasher {
	public static void main(String[] args) {
		String pin = "1234"; // Replace with the actual PIN

		// Generate a random salt
		String salt = BCrypt.gensalt();

		// Hash the PIN with the salt
		String hashedPin = BCrypt.hashpw(pin, salt);

		// Print the salted hash
		System.out.println("Salted Hash: " + hashedPin);
	}
}

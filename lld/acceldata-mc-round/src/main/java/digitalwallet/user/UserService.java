package digitalwallet.user;

import java.util.concurrent.atomic.AtomicInteger;

public class UserService {
	UserRepository userRepository;
	public static AtomicInteger userCount;

	public void createUser(String name, String emailId, String phoneNum, String walletId) {

		User user = new User(userCount.incrementAndGet(), name, emailId, phoneNum, walletId);
		userRepository.addUser(user);
	}

}

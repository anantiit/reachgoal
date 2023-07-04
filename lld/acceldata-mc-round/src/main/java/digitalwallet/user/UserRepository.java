package digitalwallet.user;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import digitalwallet.exceptions.UserAlreadyExistException;

public class UserRepository {
	public static final Map<String, User> users = new ConcurrentHashMap<>();

	public void addUser(User user) {
		User user1 = users.putIfAbsent(user.emailId, user);
		if (user1 != null) {
			throw new UserAlreadyExistException("user with id exists:" + user1.emailId);
		}

	}
}

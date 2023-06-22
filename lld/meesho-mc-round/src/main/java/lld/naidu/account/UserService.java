package lld.naidu.account;

import java.util.HashSet;
import java.util.Set;

import lld.naidu.exceptions.UserAlreadyExistsException;

public class UserService {

	static volatile Set<User> users = new HashSet<User>();

	public void createUser(User user) {
		if (users.contains(user)) {
			throw new UserAlreadyExistsException("user with same email id already created" + user.getEmailId());
		}
		users.add(user);
	}

//TODO update , get users
}

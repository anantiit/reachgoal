package lld.splitwise;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lld.splitwise.account.AccountController;
import lld.splitwise.account.Group;
import lld.splitwise.account.User;

public class SplitwiseManager {
	public static void initializeSplitWise(AccountController accountController) {
		createUsers(accountController);
		createGroups(accountController);
		addFriends(accountController);
	}

	private static void addFriends(AccountController accountController) {
		accountController.addFriend("u1", "u2");
		accountController.addFriend("u1", "u2");
		accountController.addFriend("u1", "u3");

	}

	private static void createGroups(AccountController accountController) {
		Set<User> members = new HashSet<User>();
		members.addAll(List.of(accountController.getUser("u1")));
		members.addAll(List.of(accountController.getUser("u2")));
		members.addAll(List.of(accountController.getUser("u3")));
		members.addAll(List.of(accountController.getUser("u4")));

		accountController.addOrUpdateGroup(new Group("g1", "g1", members));

	}

	private static void createUsers(AccountController accountController) {

		accountController.addOrUpdateUser(new User("u1", "u1", "u1@gmail.com", "12344311434"));
		accountController.addOrUpdateUser(new User("u2", "u2", "u2@gmail.com", "22344311434"));
		accountController.addOrUpdateUser(new User("u3", "u3", "u3@gmail.com", "32344311434"));
		accountController.addOrUpdateUser(new User("u4", "u4", "u4@gmail.com", "42344311434"));

	}

}

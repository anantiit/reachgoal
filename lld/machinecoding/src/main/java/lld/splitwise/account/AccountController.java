package lld.splitwise.account;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AccountController {
	private static Map<String, User> users = new HashMap<String, User>();
	private static Map<String, Group> groups = new HashMap<String, Group>();
	private static Map<String, Set<String>> friendsList = new HashMap<String, Set<String>>();

	public User addOrUpdateUser(User user) {
		return users.put(user.getUserId(), user);
	}

	public User getUser(String userId) {
		return users.get(userId);
	}

	public Group addOrUpdateGroup(Group group) {
		return groups.put(group.getId(), group);
	}

	public Group getGroup(String groupId) {
		return groups.get(groupId);
	}

	public void addFriend(String userId1, String userId2) {
		friendsList.computeIfAbsent(userId1, key -> new HashSet<>()).add(userId2);
	}

	public void addUserToGroup() {
		// TODO
	}
}

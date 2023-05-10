package com.course.naidu.restfulwebservices.services.dao;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.course.naidu.restfulwebservices.exceptions.InvalidInputException;
import com.course.naidu.restfulwebservices.exceptions.UserNotFoundException;
import com.course.naidu.restfulwebservices.model.User;

@Service
public class UserDao {
	private static Map<Integer, User> usersdb = new HashMap<Integer, User>();
	static {
		usersdb.put(1, new User(1, "Naresh", LocalDate.now().minusYears(30)));
		usersdb.put(2, new User(2, "Suresh", LocalDate.now().minusYears(20)));
		usersdb.put(3, new User(3, "Ramesh", LocalDate.now().minusYears(21).minusDays(58)));
	}

	public List<User> getAllUsers() {
		return usersdb.values().stream().toList();
	}

	public User findUser(Integer id) throws UserNotFoundException {
		User user = usersdb.get(id);
		if (user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		return user;
	}

	public User createUser(User user) {
		if (user == null) {
			throw new InvalidInputException();
		}
		int id = usersdb.size() + 1;
		usersdb.put(id, user);
		user.setId(id);
		return findUser(id);

	}

	public User deleteUser(Integer id) {
		User user = usersdb.get(id);
		if (user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		return usersdb.remove(id);
	}

	public User updateUser(User user) {
		if (user == null) {
			throw new UserNotFoundException("id:" + user.getId());
		}
		return usersdb.put(user.getId(), user);
	}
}
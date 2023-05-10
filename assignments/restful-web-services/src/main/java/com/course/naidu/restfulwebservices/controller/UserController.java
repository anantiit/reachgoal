package com.course.naidu.restfulwebservices.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.course.naidu.restfulwebservices.model.User;
import com.course.naidu.restfulwebservices.services.dao.UserDao;

import jakarta.validation.Valid;

@RestController
public class UserController {
	UserDao userDao;

	public UserController(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@GetMapping(path = "/users")
	public List<User> getAllUsers() {
		return userDao.getAllUsers();
	}

	@GetMapping(path = "/users/{userid}", produces = "application/json")
	public User getUser(@PathVariable Integer userid) {
		return userDao.findUser(userid);
	}

	@PostMapping(path = "/users", produces = "application/json", consumes = "application/json")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User u1 = userDao.createUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u1.getId())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping(path = "/users", produces = "application/json", consumes = "application/json")
	public User updateUser(@RequestBody User user) {
		return userDao.updateUser(user);
	}

	@DeleteMapping(path = "/users/{userid}", produces = "application/json")
	public User deleteUser(@PathVariable Integer userid) {
		return userDao.deleteUser(userid);
	}
}

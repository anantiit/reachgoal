package org.javasavvy.tutorial.services;


import java.util.Date;

import org.javasavvy.tutorial.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional(propagation=Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

	public User getUser(long userId) {
		User user = new User();
		user.setCreateDate(new Date());
		user.setUpdatedDate(new Date());
		user.setEmail("jayaram@gmail.com");
		user.setFirstName("javasavvy");
		user.setLastName("exmp");
		user.setSex("M");
		return user;
	}

}

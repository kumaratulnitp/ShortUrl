package com.personal.MyUrlShortener.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.personal.MyUrlShortener.pojo.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserDao {
	
	private AtomicInteger currentAvailableUserId = new AtomicInteger(0);
	
	private Map<String, User> users = new ConcurrentHashMap<String, User>();
	
	public void createUser(String userName, String userEmail) {
		User user = User.builder()
				.email(userEmail)
				.id(currentAvailableUserId.getAndIncrement())
				.name(userName)
				.build();
		
		setUser(user);
	}
	
	public User getUser(String email) {
		User user = users.get(email);
		if (user == null ) {
			createUser(null, email);
		}
		return users.get(email);
	}
	
	public void setUser(User user) {
		if (user == null) 
			return;
		users.put(user.getEmail(), user);
		log.info("users {}", users);
	}
}

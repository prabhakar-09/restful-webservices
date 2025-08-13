package com.spring.rest.webservices.restful_webservices.user;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

//@ComponentScan
@Component
public class UserDaoService {
	
	// Static list for now
	private static List<Users> users = new ArrayList<>();
	
	private static int userCount = 0;
	
//	Creating hardcoded test data
//	since it is static whenever we restart the server the data will be 
//	reset and only below records will be available
	static {
		users.add(new Users(++userCount, "Piku", LocalDate.now().minusYears(25)));
		users.add(new Users(++userCount, "miku", LocalDate.now().minusYears(20)));
		users.add(new Users(++userCount, "niku", LocalDate.now().minusYears(15)));
	}
	
//	getting all hardcoded user data
	public List<Users> findAllUsers(){
		return users;
	}
	
//	Create user
	public Users createUser(Users user) {
		user.setId(++userCount);
		users.add(user);
		
		return user;
	}
	
//	Getting user by ID
	public Users findUserById(int id) {
		
		Predicate<? super Users> predicate = user->user.getId().equals(id);
		return users.stream()
				.filter(predicate)
				.findFirst().orElse(null); // filter returns the list back so we get the first one from it
	}
	
	public void deleteById(int id) {
		Predicate<? super Users> predicate = user->user.getId().equals(id);
		users.removeIf(predicate);
	}

}

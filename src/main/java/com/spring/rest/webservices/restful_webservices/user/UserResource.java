package com.spring.rest.webservices.restful_webservices.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

@RestController
public class UserResource {
	
//	@Autowired
//	private UserDaoService userDaoService; Failed to compile 
	
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service = service;
	}
	

//	Getting details of all users
	@GetMapping("/users")
	public List<Users> getAllUsers(){
		return service.findAllUsers();
		
	}
	
//	Get By ID
	@GetMapping("/users/{id}")
	public Users getUserById(@PathVariable int id) {
		Users userData = service.findUserById(id);
		if(userData == null) {
			throw new UserNotFoundException("ID: " +id);
		}
		
		return userData;
		
	}
	
//	Create User
	@PostMapping("/users/create")
	public ResponseEntity<Users> createUser(@Valid  @RequestBody Users user) {
		Users savedUser = service.createUser(user);
		
//		returning the created user location uri
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//						.path("/{id}")
//						.buildAndExpand(savedUser.getId()).toUri();
//		return ResponseEntity.created(location).build();  It fails because the url for get is different 
		
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			    .path("/users/{id}")
			    .buildAndExpand(savedUser.getId())
			    .toUri();
		
		return ResponseEntity.created(location).build();

		
	}
	
	@DeleteMapping("/users/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		service.deleteById(id);
	}
	
	
	
}

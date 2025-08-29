package com.spring.rest.webservices.restful_webservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.rest.webservices.restful_webservices.jpa.PostRepository;
import com.spring.rest.webservices.restful_webservices.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {
	
//	@Autowired
//	private UserDaoService userDaoService; Failed to compile 
	
//	private UserDaoService service;
	
	private UserRepository userRepository;
	
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository repository, PostRepository postRepository) {
//		this.service = service;
		this.userRepository = repository;
		this.postRepository = postRepository;
	}
	

//	Getting details of all users
	@GetMapping("/jpa/users")
	public List<Users> getAllUsers(){
		return userRepository.findAll();
		
	}
	
//	Get By ID
//	@GetMapping("/users/{id}")
//	public Users getUserById(@PathVariable int id) {
//		Users userData = service.findUserById(id);
//		if(userData == null) {
//			throw new UserNotFoundException("ID: " +id);
//		}
//		
//		return userData;
//		
//	}
	
//	HATEOAS Impl
//	Using Entity Model
//	Using WebMvcLinkBuilder
	@GetMapping("/jpa/users/{id}")
	public EntityModel<Users> getUserById(@PathVariable int id) {
		Optional<Users> userData = userRepository.findById(id);
		if(userData.isEmpty()) {
			throw new UserNotFoundException("ID: " +id);
		}
		
		EntityModel<Users> entityModel = EntityModel.of(userData.get());
		
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
		
		entityModel.add(link.withRel("all-users"));
		
//		return userData;
		return entityModel;
	}
	
//	Create User
	@PostMapping("/jpa/users/create")
	public ResponseEntity<Users> createUser(@Valid  @RequestBody Users user) {
		Users savedUser = userRepository.save(user);
		
//		returning the created user location uri
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//						.path("/{id}")
//						.buildAndExpand(savedUser.getId()).toUri();
//		return ResponseEntity.created(location).build();  It fails because the url for get is different 
		
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
			    .path("/jpa/users/{id}")
			    .buildAndExpand(savedUser.getId())
			    .toUri();
		
		return ResponseEntity.created(location).build();

		
	}
	
	@DeleteMapping("/jpa/users/delete/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepository.deleteById(id);
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrievePostsForAUser(@PathVariable int id) {
		Optional<Users> user = userRepository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:"+id);
		}
		
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post) {
		Optional<Users> user = userRepository.findById(id);
		
		if(user.isEmpty())
			throw new UserNotFoundException("id:"+id);
		
		post.setUsers(user.get());
		
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path(null)
				.buildAndExpand(savedPost.getId())
				.toUri();   

		return ResponseEntity.created(location).build();

	}
	
	
	
}

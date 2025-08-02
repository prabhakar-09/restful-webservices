package com.spring.rest.webservices.restful_webservices.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// REST API
@RestController()
public class HelloWorldController {

//	@RequestMapping(method = RequestMethod.GET,path = "/hello-world") // One way of doing it
//	@RequestMapping("/hello-world") one more way
	@GetMapping(path="/hello-world")
	public String returnHello() {
		return "Hello World Get Mapping Works!!";
	}
	
	@GetMapping(path="/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello world bean!");
		
	}
}

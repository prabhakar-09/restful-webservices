package com.spring.rest.webservices.restful_webservices.security;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfiguration {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
//		All request to be authenticated
		http.authorizeHttpRequests(
				
				auth -> auth.anyRequest().authenticated()
				
				);
		
//		If request is not authenticated, a web page is shown 
		http.httpBasic(withDefaults());
		
//		CSRF -> POST, PUT etc.. disable
		http.csrf().disable();
		
		return http.build();
	}

}

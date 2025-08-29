package com.spring.rest.webservices.restful_webservices.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.rest.webservices.restful_webservices.user.Users;

public interface UserRepository extends JpaRepository<Users, Integer>{

}

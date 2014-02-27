package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.Users;

public interface UserRepository extends JpaRepository<Users, String> {
	
	Users findByUsername(String username);

}

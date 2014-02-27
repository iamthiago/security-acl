package com.security.test.security;

import static org.hamcrest.Matchers.isA;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.security.model.Authorities;
import com.security.model.Users;
import com.security.repository.UserRepository;
import com.security.test.util.AbstractWebTest;

/**
 * @author Thiago
 * 
 * Execute essa classe para criação de usuarios de teste para dev.
 *
 */

@Transactional
public class UserTest extends AbstractWebTest {
	
	@Autowired private UserRepository userRepository;
	
	@Before
	public void setup() {
		
		List<Users> users = userRepository.findAll();
		if (users.isEmpty()) {
			Users u1 = new Users();
			u1.setUsername("admin");
			u1.setPassword("admin");
			u1.setEnabled(true);
			
			Authorities a1 = new Authorities();
			a1.setAuthority("ROLE_ADMIN");
			a1.setUsername(u1);
			
			u1.setAuthorities(Arrays.asList(a1));
			
			Users u2 = new Users();
			u2.setUsername("user");
			u2.setPassword("user");
			u2.setEnabled(true);
			
			Authorities a2 = new Authorities();
			a2.setAuthority("ROLE_USER");
			a2.setUsername(u2);
			
			u2.setAuthorities(Arrays.asList(a2));
			
			userRepository.save(Arrays.asList(u1, u2));
		}
	}
	
	@Test
	public void checkUser() {
		assertThat(userRepository.findByUsername("admin"), isA(Users.class));
	}
	
	@After
	public void tierDown() {
		
	}
}
package br.com.cleartech.bda.web.test.security;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import br.com.cleartech.bda.web.security.service.UserGroupManager;
import br.com.cleartech.bda.web.test.util.AbstractSecurityTest;

/**
 * @author Thiago
 * 
 */
public class UserTest extends AbstractSecurityTest {
	
	/**
	 * 
	 */
	private static final String USER_NAME = "user";
	private static final String ROLE_USER = "ROLE_USER";

	@Autowired private UserGroupManager userGroupManager;
	@Autowired private JdbcUserDetailsManager jdbcUserDetailsManager;
	
	private UserDetails user = null;
	
	@Before
	public void setup() {
		userGroupManager.createUserWithAuthoriy(USER_NAME, ROLE_USER);
		user = jdbcUserDetailsManager.loadUserByUsername(USER_NAME);
		userGroupManager.setAuthentication(USER_NAME);
	}
	
	@Test
	public void checkUser() {
		assertThat(user, is(notNullValue()));
		assertThat(user, isA(UserDetails.class));
		GrantedAuthority roleUser = new SimpleGrantedAuthority(ROLE_USER);
		assertThat(user.getAuthorities().contains(roleUser), is(true));
	}
	
	@After
	public void tierDown() {
		jdbcUserDetailsManager.deleteUser(USER_NAME);
	}
}
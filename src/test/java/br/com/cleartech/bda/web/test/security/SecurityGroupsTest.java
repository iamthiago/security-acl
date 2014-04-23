/**
 * 
 */
package br.com.cleartech.bda.web.test.security;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import br.com.cleartech.bda.web.security.service.UserGroupManager;
import br.com.cleartech.bda.web.test.util.AbstractSecurityTest;

/**
 * @author Thiago
 *
 */
public class SecurityGroupsTest extends AbstractSecurityTest {
	
	@Autowired private UserGroupManager userGroupManager;
	@Autowired private SecurityTestService securityTestService;
	@Autowired private JdbcUserDetailsManager jdbcUserDetailsManager;
	
	private static final String TEST_GROUP = "testGroup";
	private static final String USER_USER = "user";
	
	private UserDetails user = null;
	private List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
	
	@Rule public ExpectedException expectedException = ExpectedException.none();
	
	@Before
	public void setup() {
		authorities.add(new SimpleGrantedAuthority("ROLE_GRUPO"));
		jdbcUserDetailsManager.createGroup(TEST_GROUP, authorities);
		userGroupManager.createUserWithAuthoriy(USER_USER, "ROLE_USER");
		user = jdbcUserDetailsManager.loadUserByUsername("user");
	}
	
	@Test
	public void testAddAuthorityToGroup() {
		GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_TESTE");
		jdbcUserDetailsManager.addGroupAuthority(TEST_GROUP, authority);
		List<GrantedAuthority> foundAuthorities = jdbcUserDetailsManager.findGroupAuthorities(TEST_GROUP);
		
		assertThat(foundAuthorities, hasSize(2));
		
		for (GrantedAuthority grantedAuthority : foundAuthorities) {
			if ("ROLE_TESTE".equals(grantedAuthority.getAuthority())) {
				assertThat(grantedAuthority, is(equalTo(authority)));
			}
		}
	}
	
	@Test
	public void testAddMemberToGroup() {
		jdbcUserDetailsManager.addUserToGroup(user.getUsername(), TEST_GROUP);
		List<String> foundUsers = jdbcUserDetailsManager.findUsersInGroup(TEST_GROUP);
		assertThat(foundUsers.contains(user.getUsername()), is(true));
	}
	
	@Test
	public void testUserInGroupHasAccessToRoleUserMethod() {
		jdbcUserDetailsManager.addUserToGroup(user.getUsername(), TEST_GROUP);
		userGroupManager.setAuthentication(user.getUsername());
		assertThat(securityTestService.testHasRoleUser(), is(true));
	}
	
	@Test
	public void testUserInGroupHasNoAccessToRoleAdminMethod() {
		jdbcUserDetailsManager.addUserToGroup(user.getUsername(), TEST_GROUP);
		userGroupManager.setAuthentication(user.getUsername());
		expectedException.expect(AccessDeniedException.class);
		securityTestService.testHasRoleAdmin();
	}
	
	@Test
	public void testUserInGroupHasAccessToRoleUserAndRoleGroup() {
		jdbcUserDetailsManager.addUserToGroup(user.getUsername(), TEST_GROUP);
		userGroupManager.setAuthentication(user.getUsername());
		assertThat(securityTestService.testHasRoleUser(), is(true));
		assertThat(securityTestService.testHasRoleGrupo(), is(true));
	}
	
	@After
	public void tierDown() {
		jdbcUserDetailsManager.deleteUser(USER_USER);
		jdbcUserDetailsManager.deleteGroup(TEST_GROUP);
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
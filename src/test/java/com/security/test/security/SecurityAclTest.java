/**
 * 
 */
package com.security.test.security;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.security.model.Menu;
import com.security.service.AclManager;
import com.security.service.MenuService;
import com.security.service.UserGroupManager;
import com.security.test.util.AbstractSecurityTest;

/**
 * @author Thiago
 *
 */
public class SecurityAclTest extends AbstractSecurityTest {
	
	@Autowired private MenuService menuService;
	@Autowired private UserGroupManager userGroupManager;
	@Autowired private SecurityTestService securityTestService;
	@Autowired private JdbcUserDetailsManager jdbcUserDetailsManager;
	@Autowired private AclManager aclManager;
	
	private static final String USER_ADMIN = "admin";
	private static final String USER_USER = "user";
	
	private Menu menu = null;
	
	@Rule public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		userGroupManager.createUserWithAuthoriy(USER_ADMIN, "ROLE_ADMIN");
		userGroupManager.createUserWithAuthoriy(USER_USER, "ROLE_USER");
		
    	Menu p1 = new Menu();
		p1.setName("Menu");
		p1.setPath("/menu");
		menu = menuService.saveOrUpdate(p1);
		
		userGroupManager.setAuthentication(USER_ADMIN);
		aclManager.addPermission(Menu.class, menu.getId(), new PrincipalSid(USER_ADMIN), BasePermission.ADMINISTRATION);
	}

	@After
	public void tearDown() {
		jdbcUserDetailsManager.deleteUser(USER_ADMIN);
		jdbcUserDetailsManager.deleteUser(USER_USER);
		menuService.deleteAll();
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	@Test
	public void testUserHasNoAccessToMenu() {
		boolean isGranted = aclManager.isPermissionGranted(Menu.class, menu.getId(), new PrincipalSid(USER_USER), BasePermission.READ);
		assertThat(isGranted, is(false));
	}
	
	@Test
	public void testAdminHasNoAccessToMenuAsRead() {
		boolean isGranted = aclManager.isPermissionGranted(Menu.class, menu.getId(), new PrincipalSid(USER_ADMIN), BasePermission.READ);
		assertThat(isGranted, is(false));
	}
	
	@Test
	public void testAdminHasAccessToMenuAsAdministration() {
		boolean isGranted = aclManager.isPermissionGranted(Menu.class, menu.getId(), new PrincipalSid(USER_ADMIN), BasePermission.ADMINISTRATION);
		assertThat(isGranted, is(true));
	}
	
	@Test
	public void testAdminHasAccessToMethodHasRoleAdmin() {
		userGroupManager.setAuthentication(USER_ADMIN);
		assertThat(securityTestService.testHasRoleAdmin(), is(true));
	}
	
	@Test
	public void testUserHasNoAccessToMethodHasRoleAdmin() {
		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasRoleAdmin();
	}
	
	@Test
	public void testAdminHasAccessToMethodHasPermissionAdministration() {
		userGroupManager.setAuthentication(USER_ADMIN);
		assertThat(securityTestService.testHasPermissionAdministrationOnMenu(menu), is(true));
	}
	
	@Test
	public void testUserHasNoAccessToMethodHasPermissionAdministration() {
		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionAdministrationOnMenu(menu);
	}
	
	@Test
	public void testUserHasNoAccessToMethodHasPermissionRead() {
		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionReadOnMenu(menu);
	}
	
	@Test
	public void testAdminHasNoAccessToMethodPermissionRead() {
		userGroupManager.setAuthentication(USER_ADMIN);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionReadOnMenu(menu);
	}
	
	@Test
	public void testUserHasAclPermissionBasedOnRole() {
		aclManager.addPermission(Menu.class, menu.getId(), new GrantedAuthoritySid("ROLE_USER"), BasePermission.READ);
		userGroupManager.setAuthentication(USER_USER);
		assertThat(securityTestService.testHasPermissionReadOnMenu(menu), is(true));
	}
	
	@Test
	public void testRemoveAclPermissionFromUser() {
		aclManager.addPermission(Menu.class, menu.getId(), new GrantedAuthoritySid("ROLE_USER"), BasePermission.READ);
		userGroupManager.setAuthentication(USER_USER);
		assertThat(securityTestService.testHasPermissionReadOnMenu(menu), is(true));
		
		userGroupManager.setAuthentication(USER_ADMIN);
		aclManager.removePermission(Menu.class, menu.getId(), new GrantedAuthoritySid("ROLE_USER"), BasePermission.READ);
		
		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionReadOnMenu(menu);
	}

	@Test
	public void testFilterList() {

		menuService.deleteAll();

		for (int i = 0; i < 5; i++) {
			Menu m = new Menu();
			m.setName("menu " + i);
			m.setPath("/menu/" + i);

			Menu newMenu = menuService.saveOrUpdate(m);

			if (i < 3) {
				aclManager.addPermission(Menu.class, newMenu.getId(), new GrantedAuthoritySid("ROLE_ADMIN"), BasePermission.ADMINISTRATION);
			} else {
				aclManager.addPermission(Menu.class, newMenu.getId(), new GrantedAuthoritySid("ROLE_USER"), BasePermission.READ);
			}
		}

		userGroupManager.setAuthentication(USER_ADMIN);
		assertThat(securityTestService.testFilterMenu(menuService.findAll()).size(), is(equalTo(3)));

		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testFilterMenu(menuService.findAll());
	}
	
	@Test
	public void encodePassword() {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder("test");
		String result = encoder.encode(USER_ADMIN);
		assertTrue(encoder.matches(USER_ADMIN, result));
	}
}
/**
 * 
 */
package br.com.cleartech.bda.web.test.security;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import br.com.cleartech.bda.core.domain.security.Menu;
import br.com.cleartech.bda.web.security.service.AclManager;
import br.com.cleartech.bda.web.security.service.MenuService;
import br.com.cleartech.bda.web.security.service.UserGroupManager;
import br.com.cleartech.bda.web.test.util.AbstractSecurityTest;

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
	
	private UserDetails admin = null;
	private UserDetails user = null;
	private Menu eildMenu = null;
	
	@Rule public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		userGroupManager.createUserWithAuthoriy(USER_ADMIN, "ROLE_ADMIN");
		userGroupManager.createUserWithAuthoriy(USER_USER, "ROLE_USER");
		
		admin = jdbcUserDetailsManager.loadUserByUsername(USER_ADMIN);
		user = jdbcUserDetailsManager.loadUserByUsername(USER_USER);
		
    	Menu p1 = new Menu();
		p1.setNome("EILD");
		p1.setPath("eild/consultar");
		eildMenu = menuService.saveOrUpdate(p1);
		
		userGroupManager.setAuthentication(USER_ADMIN);
		aclManager.addSinglePermission(Menu.class, eildMenu.getId(), admin, BasePermission.ADMINISTRATION);
	}
	
	@Test
	public void testUserNaoTemAcessoEild() {
		boolean isGranted = aclManager.isPermissionGrantedForUser(Menu.class, eildMenu.getId(), user, BasePermission.READ);
		assertThat(isGranted, is(false));
	}
	
	@Test
	public void testUserNaoTemAcessoEildComoRead() {
		boolean isGranted = aclManager.isPermissionGrantedForUser(Menu.class, eildMenu.getId(), admin, BasePermission.READ);
		assertThat(isGranted, is(false));
	}
	
	@Test
	public void testUserTemAcessoEildComoAdministrador() {
		boolean isGranted = aclManager.isPermissionGrantedForUser(Menu.class, eildMenu.getId(), admin, BasePermission.ADMINISTRATION);
		assertThat(isGranted, is(true));
	}
	
	@Test
	public void testUserTemAcessoMetodoHasRole() {
		userGroupManager.setAuthentication(USER_ADMIN);
		assertThat(securityTestService.testHasRoleAdmin(), is(true));
	}
	
	@Test
	public void testUserNaoTemAcessoMetodoHasRole() {
		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasRoleAdmin();
	}
	
	@Test
	public void testUserTemAcessoMetodoHasPermissionAdministration() {
		userGroupManager.setAuthentication(USER_ADMIN);
		assertThat(securityTestService.testHasPermissionAdministrationOnMenu(eildMenu), is(true));
	}
	
	@Test
	public void testUserNaoTemAcessoMetodoHasPermissionAdministration() {
		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionAdministrationOnMenu(eildMenu);
	}
	
	@Test
	public void testUserNaoTemAcessoMetodoHasPermissionRead() {
		userGroupManager.setAuthentication(USER_USER);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionReadOnMenu(eildMenu);
	}
	
	@Test
	public void testUserAdminNaoTemAcessoMetodoHasPermissionRead() {
		userGroupManager.setAuthentication(USER_ADMIN);
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionReadOnMenu(eildMenu);
	}
	
	@Test
	public void encodePassword() {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder("bda");
		String result = encoder.encode(USER_ADMIN);
		assertTrue(encoder.matches(USER_ADMIN, result));
	}
	
	@After
	public void tierDown() {
		jdbcUserDetailsManager.deleteUser(USER_ADMIN);
		jdbcUserDetailsManager.deleteUser(USER_USER);
		menuService.deleteAll();
		SecurityContextHolder.getContext().setAuthentication(null);
	}
}
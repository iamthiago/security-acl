/**
 * 
 */
package com.security.test.security;

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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.security.model.Menu;
import com.security.service.AclHelpService;
import com.security.service.MenuService;
import com.security.test.util.AbstractWebTest;

/**
 * @author Thiago
 *
 */
public class SecurityTest extends AbstractWebTest {
	
	@Autowired private AclHelpService aclHelpService;
	@Autowired private JdbcUserDetailsManager userDetailsManager;
	@Autowired private MenuService menuService;
	@Autowired private SecurityTestService securityTestService;
	
	private UserDetails admin = null;
	private UserDetails user = null;
	private Menu eildMenu = null;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Before
	public void setup() {
		admin = userDetailsManager.loadUserByUsername("admin");
		user = userDetailsManager.loadUserByUsername("user");
		
		eildMenu = menuService.findByMenuNome("EILD");
	}
	
	@Test
	public void testUserNaoTemAcessoEild() {
		boolean isGranted = aclHelpService.isPermissionGrantedForUser(Menu.class, eildMenu.getId(), user, BasePermission.READ);
		assertThat(isGranted, is(false));
	}
	
	@Test
	public void testUserNaoTemAcessoEildComoRead() {
		boolean isGranted = aclHelpService.isPermissionGrantedForUser(Menu.class, eildMenu.getId(), admin, BasePermission.READ);
		assertThat(isGranted, is(false));
	}
	
	@Test
	public void testUserTemAcessoEildComoAdministrador() {
		boolean isGranted = aclHelpService.isPermissionGrantedForUser(Menu.class, eildMenu.getId(), admin, BasePermission.ADMINISTRATION);
		assertThat(isGranted, is(true));
	}
	
	@Test
	public void testUserTemAcessoMetodoHasRole() {
		setAuthentication("admin");
		assertThat(securityTestService.testHasRole(), is(true));
	}
	
	@Test
	public void testUserNaoTemAcessoMetodoHasRole() {
		setAuthentication("user");
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasRole();
	}
	
	@Test
	public void testUserTemAcessoMetodoHasPermissionAdministration() {
		setAuthentication("admin");
		assertThat(securityTestService.testHasPermissionAdministration(eildMenu), is(true));
	}
	
	@Test
	public void testUserNaoTemAcessoMetodoHasPermissionAdministration() {
		setAuthentication("user");
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionAdministration(eildMenu);
	}
	
	@Test
	public void testUserNaoTemAcessoMetodoHasPermissionRead() {
		setAuthentication("user");
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionRead(eildMenu);
	}
	
	@Test
	public void testUserAdminNaoTemAcessoMetodoHasPermissionRead() {
		setAuthentication("admin");
		exception.expect(AccessDeniedException.class);
		securityTestService.testHasPermissionRead(eildMenu);
	}
	
	@Test
	public void encodePassword() {
		StandardPasswordEncoder encoder = new StandardPasswordEncoder("bda");
		String result = encoder.encode("admin");
		assertTrue(encoder.matches("admin", result));
	}
	
	@After
	public void tierDown() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	/**
	 * Método utilizado para simular um usuário logado no spring security.
	 * Com isto, você pode efetuar chamadas em métodos, classes seguras, e testar se o usuário está ou não
	 * autorizado a acessar tal método ou classe.
	 * 
	 * @param username Nome do usuário a ser logado
	 */
	private void setAuthentication(String username) {
		UserDetails user = userDetailsManager.loadUserByUsername(username);
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
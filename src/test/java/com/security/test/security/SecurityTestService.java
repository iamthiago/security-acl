/**
 * 
 */
package com.security.test.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.security.model.Menu;

/**
 * 
 * @author Thiago
 *
 */
@Service
public class SecurityTestService {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityTestService.class);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public boolean testHasRoleAdmin() {
		log.info("access granted to hasRole('ROLE_ADMIN')");
		return true;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	public boolean testHasRoleUser() {
		log.info("access granted to hasRole('ROLE_USER')");
		return true;
	}
	
	@PreAuthorize("hasRole('ROLE_GRUPO')")
	public boolean testHasRoleGrupo() {
		log.info("access granted to hasRole('ROLE_GRUPO')");
		return true;
	}
	
	@PreAuthorize("hasPermission(#menu, 'administration')")
	public boolean testHasPermissionAdministrationOnMenu(Menu menu) {
		log.info("access granted to hasPermission(#menu, 'administration')");
		return true;
	}
	
	@PreAuthorize("hasPermission(#menu, 'read')")
	public boolean testHasPermissionReadOnMenu(Menu menu) {
		log.info("access granted to hasPermission(#menu, 'read')");
		return true;
	}
}

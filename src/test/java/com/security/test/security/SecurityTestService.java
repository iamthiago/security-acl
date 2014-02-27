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
 * Classe criada para fazer testes do spring security em métodos seguros
 * 
 * @author Thiago
 *
 */
@Service
public class SecurityTestService {
	
	private static final Logger log = LoggerFactory.getLogger(SecurityTestService.class);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public boolean testHasRole() {
		log.info("access granted to hasRole('ROLE_ADMIN')");
		return true;
	}
	
	@PreAuthorize("hasPermission(#menu, 'administration')")
	public boolean testHasPermissionAdministration(Menu menu) {
		log.info("access granted to hasPermission(#menu, 'administration')");
		return true;
	}
	
	@PreAuthorize("hasPermission(#menu, 'read')")
	public boolean testHasPermissionRead(Menu menu) {
		log.info("access granted to hasPermission(#menu, 'read')");
		return true;
	}
}

/**
 * 
 */
package com.security.service;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;


/**
 * @author Thiago
 *
 */
public interface UserGroupManager {
	
	/**
	 * @param username
	 * @param group
	 */
	public void createAndAuthenticateUser(String username, String group);
	
	/**
	 * @param username
	 * @param authority
	 */
	public void createUserWithAuthoriy(String username, String authority);
	
	/**
	 * @return
	 */
	public List<String> listAllGroups();
	
	/**
	 * @param groupName
	 * @return
	 */
	public List<GrantedAuthority> listGroupAuthorities(String groupName);
	
	/**
	 * @param group
	 * @param roles
	 */
	public void addRolesToGroup(String group, String[] roles);
	
	/**
	 * Efetua a autenticação de um usuário já existente, buscando-o através do <code>loadUserByUsername</code>.
	 * 
	 * @param username Username a ser autenticado.
	 */
	public void setAuthentication(String username);

}
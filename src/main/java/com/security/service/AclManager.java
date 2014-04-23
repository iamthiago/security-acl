/**
 * 
 */
package com.security.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Thiago
 *
 */
public interface AclManager {
	
	/**
	 * adiciona uma unica permissão ao acl.
	 * 
	 * @param clazz Classe que representa o domain do acl
	 * @param identifier identifier Id fo domain
	 * @param user usuario a ser tratado
	 * @param permission tipo da permissão
	 */
	public <T, E extends Serializable> void addSinglePermission(Class<T> clazz, E identifier, UserDetails user, Permission permission);
	
	/**
	 * adiciona uma lista de permissões ao acl.
	 * 
	 * @param clazz Classe que representa o domain do acl
	 * @param identifier identifier Id fo domain
	 * @param user usuario a ser tratado
	 * @param permission tipo da permissão
	 */
	public <T, E extends Serializable> void addListPermission(Class<T> clazz, List<E> identifiers, UserDetails user, Permission permission);
	
	/**
	 * remove uma permissão de acl ao objeto.
	 * 
	 * @param clazz Classe que representa o domain do acl
	 * @param identifier identifier Id fo domain
	 * @param user usuario a ser tratado
	 * @param permission tipo da permissão
	 */
	public <T> void removePermission(Class<T> clazz, Serializable identifier, UserDetails user, Permission permission);
	
	/**
	 * verifica se o usuário tem permissão ou não em determinado objeto de dominio gerenciado pelo acl.
	 * 
	 * @param clazz Classe que representa o domain do acl
	 * @param identifier identifier Id fo domain
	 * @param user usuario a ser tratado
	 * @param permission tipo da permissão
	 * @return retorna true se o usuário tem permissão
	 */
	public <T> boolean isPermissionGrantedForUser(Class<T> clazz, Serializable identifier, UserDetails user, Permission permission);

}

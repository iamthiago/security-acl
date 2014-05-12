/**
 * 
 */
package com.security.service;

import java.io.Serializable;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

/**
 * @author Thiago
 *
 */
public interface AclManager {
	
	/**
	 * @param clazz
	 * @param identifier
	 * @param sid
	 * @param permission
	 */
	public <T> void addPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);
	
	/**
	 * @param clazz
	 * @param identifier
	 * @param sid
	 * @param permission
	 */
	public <T> void removePermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);
	
	/**
	 * @param clazz
	 * @param identifier
	 * @param sid
	 * @param permission
	 * @return
	 */
	public <T> boolean isPermissionGranted(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);
}

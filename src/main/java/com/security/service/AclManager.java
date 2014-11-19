/**
 * 
 */
package com.security.service;

import java.io.Serializable;

import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

/**
 * @author Thiago
 * 
 * Simple interface that manages ACL Permissions
 *
 */
public interface AclManager {
	
	/**
	 * Add a permission for the given object
	 * 
	 * @param clazz Domain class
	 * @param identifier Id from the given domain
	 * @param sid Security Identifier, could be a {@link PrincipalSid} or a {@link GrantedAuthoritySid}
	 * @param permission The permission based on {@link BasePermission}
	 */
	public <T> void addPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);
	
	/**
	 * Remove a permission from the given object
	 * 
	 * @param clazz Domain class
	 * @param identifier Id from the given domain
	 * @param sid Security Identifier, could be a {@link PrincipalSid} or a {@link GrantedAuthoritySid}
	 * @param permission The permission based on {@link BasePermission}
	 */
	public <T> void removePermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);
	
	/**
	 * Check whether the given object has permission
	 * 
	 * @param clazz Domain class
	 * @param identifier Id from the given domain
	 * @param sid Security Identifier, could be a {@link PrincipalSid} or a {@link GrantedAuthoritySid}
	 * @param permission The permission based on {@link BasePermission}
	 * @return true or false
	 */
	public <T> boolean isPermissionGranted(Class<T> clazz, Serializable identifier, Sid sid, Permission permission);

	void deleteAllGrantedAcl();
}

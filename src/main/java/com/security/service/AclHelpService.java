package com.security.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.UnloadedSidException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Thiago
 *
 */

@Service
@Transactional
public class AclHelpService {
	
	private static final Logger log = LoggerFactory.getLogger(AclHelpService.class);
	
	@Autowired private MutableAclService aclService;
	
	/**
	 * adiciona uma unica permissão ao acl.
	 * 
	 * @param clazz Classe que representa o objeto a ser persistido
	 * @param identifier Id do objeto a ser persistido
	 * @param user usuario a receber permissão acl
	 * @param permission tipo da permissão
	 */
	public <T, E extends Serializable> void addSinglePermission(Class<T> clazz, E identifier, UserDetails user, Permission permission) {
		setPermission(clazz, identifier, user, permission);
	}
	
	
	/**
	 * adiciona uma lista de permissões ao acl.
	 * 
	 * @param clazz Classe que representa o objeto a ser persistido
	 * @param identifiers lista de ids do objeto a ser persistido
	 * @param user usuario a receber permissão acl
	 * @param permission tipo da permissão
	 */
	public <T, E extends Serializable> void addListPermission(Class<T> clazz, List<E> identifiers, UserDetails user, Permission permission) {
		for (Serializable identifier : identifiers) {
			setPermission(clazz, identifier, user, permission);
		}
	}
	
	private <T> void setPermission(Class<T> clazz, Serializable identifier, UserDetails user, Permission permission) {
		ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier);
		Sid sid = new PrincipalSid(user.getUsername());
		Permission p = null;
		
		if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			p = BasePermission.ADMINISTRATION;
		} else {
			p = permission;
		}
		
		MutableAcl acl = isNewAcl(identity);
		isGrantedForUser(permission, sid, p, acl);
		aclService.updateAcl(acl);
	}
	
	public <T> void removePermission(Class<T> clazz, Serializable identifier, UserDetails user, Permission permission) {
		
		ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier);
		MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
		
		Sid sid = new PrincipalSid(user.getUsername());
		
		AccessControlEntry[] entries = acl.getEntries().toArray(new AccessControlEntry[acl.getEntries().size()]);
		
		for (int i = 0; i < acl.getEntries().size(); i++) {
			if (entries[i].getSid().equals(sid) && entries[i].getPermission().equals(permission)) {
				acl.deleteAce(i);
			}
		}
		
		aclService.updateAcl(acl);
	}
	
	public <T> boolean isPermissionGrantedForUser(Class<T> clazz, Serializable identifier, UserDetails user, Permission permission) {
		ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier);
		MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
		Sid sid = new PrincipalSid(user.getUsername());
		boolean isGranted = false;
		
		try {
			isGranted = acl.isGranted(Arrays.asList(permission), Arrays.asList(sid), false);
		} catch (NotFoundException e) {
			log.info("Não foi possivel encontrar uma ACE para o usuário " + user.getUsername() + " com a permissão passada");
		} catch (UnloadedSidException e) {
			e.printStackTrace();
		}
		
		return isGranted;
	}
	
	private MutableAcl isNewAcl(ObjectIdentity identity) {
		MutableAcl acl;
		try {
			acl = (MutableAcl) aclService.readAclById(identity);
		} catch (NotFoundException e) {
			acl = aclService.createAcl(identity);
		}
		return acl;
	}

	private void isGrantedForUser(Permission permission, Sid sid, Permission p, MutableAcl acl) {
		try {
			acl.isGranted(Arrays.asList(permission), Arrays.asList(sid), false);
		} catch (NotFoundException e) {
			acl.insertAce(acl.getEntries().size(), p, sid, true);
		}
	}
}
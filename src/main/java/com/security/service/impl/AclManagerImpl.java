/**
 * 
 */
package com.security.service.impl;

import java.io.Serializable;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.acls.model.UnloadedSidException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.service.AclManager;

/**
 * @author Thiago
 *
 */
@Transactional
@Service
public class AclManagerImpl implements AclManager {
	
	private static final Logger log = LoggerFactory.getLogger(AclManagerImpl.class);
	
	@Autowired private MutableAclService aclService;
	@Autowired private JdbcTemplate jdbcTemplate;
	
	@Override
	public <T> void addPermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission) {
		ObjectIdentity identity = new ObjectIdentityImpl(clazz, identifier);
		MutableAcl acl = isNewAcl(identity);
		isPermissionGranted(permission, sid, acl);
		aclService.updateAcl(acl);
	}
	
	@Override
	public <T> void removePermission(Class<T> clazz, Serializable identifier, Sid sid, Permission permission) {
		ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier);
		MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
		
		AccessControlEntry[] entries = acl.getEntries().toArray(new AccessControlEntry[acl.getEntries().size()]);
		
		for (int i = 0; i < acl.getEntries().size(); i++) {
			if (entries[i].getSid().equals(sid) && entries[i].getPermission().equals(permission)) {
				acl.deleteAce(i);
			}
		}
		
		aclService.updateAcl(acl);
	}
	
	@Override
	public <T> boolean isPermissionGranted(Class<T> clazz, Serializable identifier, Sid sid, Permission permission) {
		ObjectIdentity identity = new ObjectIdentityImpl(clazz.getCanonicalName(), identifier);
		MutableAcl acl = (MutableAcl) aclService.readAclById(identity);
		boolean isGranted = false;
		
		try {
			isGranted = acl.isGranted(Arrays.asList(permission), Arrays.asList(sid), false);
		} catch (NotFoundException e) {
			log.info("Unable to find an ACE for the given object", e);
		} catch (UnloadedSidException e) {
			log.error("Unloaded Sid", e);
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

	private void isPermissionGranted(Permission permission, Sid sid, MutableAcl acl) {
		try {
			acl.isGranted(Arrays.asList(permission), Arrays.asList(sid), false);
		} catch (NotFoundException e) {
			acl.insertAce(acl.getEntries().size(), permission, sid, true);
		}
	}

	@Override
	public void deleteAllGrantedAcl() {
		jdbcTemplate.update("delete from acl_entry");
		jdbcTemplate.update("delete from acl_object_identity");
		jdbcTemplate.update("delete from acl_sid");
		jdbcTemplate.update("delete from acl_class");
	}
}

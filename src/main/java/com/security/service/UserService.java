package com.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;

import com.security.dto.UserDTO;
import com.security.model.Authorities;
import com.security.model.Menu;
import com.security.model.Users;
import com.security.repository.UserRepository;

/**
 * @author Thiago
 *
 */

@Service
public class UserService {
	
	@Autowired private UserRepository userRepository;
	@Autowired private AclHelpService aclHelpService;
	@Autowired private JdbcUserDetailsManager userDetailsManager;
	
	public List<UserDTO> listUsers() {
		List<Users> uList = userRepository.findAll();
		List<UserDTO> dtoList = new ArrayList<>();
		
		for (Users u : uList) {
			UserDTO dto = new UserDTO();
			BeanUtils.copyProperties(u, dto);
			dtoList.add(dto);
		}
		
		return dtoList;
	}
	
	public void addUser(UserDTO userDTO) {
		Users user = new Users();
		BeanUtils.copyProperties(userDTO, user);
		
		Authorities a = new Authorities();
		a.setAuthority("ROLE_USER");
		a.setUsername(user);
		
		user.setAuthorities(Arrays.asList(a));
		
		userRepository.save(user);
		
		UserDetails loadedUser = userDetailsManager.loadUserByUsername(user.getUsername());
		
		List<Long> menusId = Arrays.asList(ArrayUtils.toObject(userDTO.getMenus()));
		
		aclHelpService.addListPermission(Menu.class, menusId, loadedUser, BasePermission.READ);
		
	}
	
	public void deleteUser(UserDTO userDTO) {
		Users user = new Users();
		BeanUtils.copyProperties(userDTO, user);
		userRepository.delete(user);
	}
}

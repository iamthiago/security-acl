package com.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.stereotype.Service;

import com.security.model.Menu;
import com.security.repository.MenuRepository;

/**
 * @author Thiago
 *
 */
@Service
public class MenuService {
	
	@Autowired private MenuRepository menuRepository;
	
	@PostFilter("hasPermission(filterObject, 'read') or hasAuthority('ROLE_ADMIN')")
	public List<Menu> listMenu() {
		return menuRepository.findAll();
	}
	
	public List<Menu> listMenuNoSecurity() {
		return menuRepository.findAll();
	}
	
	public Menu findByMenuNome(String nome) {
		return menuRepository.findByNome(nome);
	}
}

package com.security.test.security;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.transaction.annotation.Transactional;

import com.security.model.Menu;
import com.security.repository.MenuRepository;
import com.security.service.AclHelpService;
import com.security.test.util.AbstractWebTest;

/**
 * @author Thiago
 * 
 * Execute essa classe para criação das permissões dos usuarios para efeito de teste em dev.
 *
 */

@Transactional
public class MenuTest extends AbstractWebTest {
	
	@Autowired AclHelpService aclHelpService;
	@Autowired MenuRepository menuRepository;
	
	@Autowired private JdbcUserDetailsManager userDetailsManager;
	
	@Before
	public void setup() {
        populateMenu();
        setAuthentication();
	}

	private void populateMenu() {
		List<Menu> menus = menuRepository.findAll();
        if (menus.isEmpty()) {
        	Menu p1 = new Menu();
    		p1.setNome("Home");
    		p1.setPath("/");
    		
    		Menu p2 = new Menu();
    		p2.setNome("Level 0");
    		Menu m1 = new Menu();
    		m1.setNome("Level 1 - A");
    		m1.setPath("/");
    		m1.setParent(p2);
    		Menu m2 = new Menu();
    		m2.setNome("Level 1 - B");
    		m2.setPath("/");
    		m2.setParent(p2);
    		
    		p2.setMenus(Arrays.asList(m1, m2));
    		
    		Menu p3 = new Menu();
    		p3.setNome("Item");
    		p3.setPath("item");
    		
    		Menu p4 = new Menu();
    		p4.setNome("Management");
    		p4.setPath("user");
    		
    		menuRepository.save(Arrays.asList(p1, p2, p3, p4));
		}
	}
	
	@Test
	public void insertMenuToAdmin() {
		List<Menu> parents = menuRepository.findAll();
		UserDetails user = userDetailsManager.loadUserByUsername("admin");
		
		for (Menu parent : parents) {
			aclHelpService.addSinglePermission(Menu.class, parent.getId(), user, BasePermission.ADMINISTRATION);
		}
	}
	
	@Test
	public void generateMenu() {
		List<Menu> menus = menuRepository.findAll();
		listMenus(menus);
	}
	
	@After
	public void tierDown() {
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	
	private static void listMenus(List<Menu> menus) {
		if (menus != null && menus.size() > 0) {
			for (Menu menu : menus) {
				System.out.println(menu.getNome());
				listMenus(menu.getMenus());
			}
		}
		return;
	}
	
	private void setAuthentication() {
		UserDetails user = userDetailsManager.loadUserByUsername("admin");
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
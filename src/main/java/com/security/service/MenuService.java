package com.security.service;

import java.util.List;

import com.security.model.Menu;

/**
 * @author Thiago
 *
 */
public interface MenuService {
	
	List<Menu> findAll();
	Menu saveOrUpdate(Menu t);
	void delete(Menu t);
	Menu findByMenuNome(String nome);
	void deleteAll();
	List<Menu> testFilterMenu(List<Menu> menus);
}

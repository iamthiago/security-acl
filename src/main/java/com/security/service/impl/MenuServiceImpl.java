package com.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.model.Menu;
import com.security.repository.MenuRepository;
import com.security.service.MenuService;

/**
 * @author Thiago
 *
 */
@Service
@Transactional
public class MenuServiceImpl implements MenuService {
	
	@Autowired private MenuRepository menuRepository;

	@Override
	public List<Menu> findAll() {
		return menuRepository.findAll();
	}

	@Override
	public Menu saveOrUpdate(Menu t) {
		return menuRepository.save(t);
	}

	@Override
	public void delete(Menu t) {
		menuRepository.delete(t);
	}

	@Override
	public Menu findByMenuNome(String nome) {
		return menuRepository.findByName(nome);
	}

	@Override
	public void deleteAll() {
		menuRepository.deleteAll();
	}
}

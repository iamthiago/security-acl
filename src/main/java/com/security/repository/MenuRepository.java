package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.Menu;

/**
 * @author Thiago
 *
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
	
	public Menu findByNome(String nome);

}

package com.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.model.Item;

/**
 * @author Thiago
 *
 */

public interface ItemRepository extends JpaRepository<Item, Long> {

}

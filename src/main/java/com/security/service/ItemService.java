package com.security.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.security.model.Item;
import com.security.repository.ItemRepository;

/**
 * @author Thiago
 *
 */

@Service
@Transactional
public class ItemService {
	
	@Autowired private ItemRepository itemRepository;

	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	public Item saveOrUpdate(Item t) {
		return itemRepository.save(t);
	}

	public void delete(Item t) {
		itemRepository.delete(t);
	}
}

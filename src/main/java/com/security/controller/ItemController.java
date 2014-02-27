package com.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.form.ResultMessage;
import com.security.model.Item;
import com.security.service.ItemService;

/**
 * @author Thiago
 *
 */

@Controller
@RequestMapping("item")
public class ItemController {
	
	@Autowired ItemService itemService;
	
	@RequestMapping(value = "listItems", method = RequestMethod.GET)
	public @ResponseBody List<Item> listItems() {
		return itemService.findAll();
	}
	
	@RequestMapping(value = "addItem", method = RequestMethod.POST)
	public @ResponseBody ResultMessage addItem(@RequestBody @Valid Item item, BindingResult result) {
		if (result.hasErrors()) {
			return new ResultMessage("Erro", result.getFieldError().getField() + " " + result.getFieldError().getDefaultMessage());
		}
		itemService.saveOrUpdate(item);
		return null;
	}
	
	@RequestMapping(value = "deleteItem", method = RequestMethod.POST)
	public String deleteItem(@RequestBody Item item) {
		itemService.delete(item);
		return "redirect:listItems";
	}
}

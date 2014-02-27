/**
 * 
 */
package com.security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.model.Menu;
import com.security.service.MenuService;

/**
 * @author Thiago
 *
 */
@Controller
@RequestMapping("menus")
public class MenuController {
	
	@Autowired private MenuService menuService;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Menu> getMenus() {
		return menuService.listMenu();
	}
}

package com.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.security.dto.UserDTO;
import com.security.form.ResultMessage;
import com.security.model.Menu;
import com.security.service.MenuService;
import com.security.service.UserService;

/**
 * @author Thiago
 *
 */

@Controller
@RequestMapping("user")
public class UserController {
	
	@Autowired private UserService userService;
	@Autowired private MenuService menuService;
	
	@RequestMapping(value = "listMenus", method = RequestMethod.GET)
	public @ResponseBody List<Menu> getMenusForUser() {
		return menuService.listMenuNoSecurity();
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "listUsers", method = RequestMethod.GET)
	public @ResponseBody List<UserDTO> listUsers() {
		return userService.listUsers();
	}
	
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public @ResponseBody ResultMessage addUser(@RequestBody @Valid UserDTO userDto, BindingResult result) {
		if (result.hasErrors()) {
			return new ResultMessage("Erro", result.getFieldError().getField() + " " + result.getFieldError().getDefaultMessage());
		}
		userService.addUser(userDto);
		return null;
	}
	
	@RequestMapping(value = "deleteUser", method = RequestMethod.DELETE)
	public String deleteUser(UserDTO userDto) {
		userService.deleteUser(userDto);
		return "redirect:listUsers";
	}
}

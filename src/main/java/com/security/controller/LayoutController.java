package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/layout")
public class LayoutController {
	
	@RequestMapping("home")
	public String getHomeLayout() {
		return "home";
	}
	
	@RequestMapping("item")
	public String getItemLayout() {
		return "item";
	}
	
	@RequestMapping("user")
	public String getUserLayout() {
		return "user";
	}
}

package com.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Thiago
 *
 */
@Controller
public class ExceptionController {
	
	@RequestMapping(value = "/nullpointer", method = RequestMethod.GET)
	public void handlerException() {
		throw new NullPointerException();
	}
	
	@RequestMapping(value = "/runtime", method = RequestMethod.GET)
	public void handlerRuntimeException() {
		throw new RuntimeException();
	}
}
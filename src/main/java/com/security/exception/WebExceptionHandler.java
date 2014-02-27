package com.security.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Thiago
 * 
 * Classe criada para controlar exceções web do sistema.<br>
 * Caso alguma das exeções abaixo ocorra no sistema, que por algum
 * motivo não foi tratado, o sistema vai logar no console e redirecionar
 * para uma página amigável de erro, na qual você pode consultar o stacktrace.
 * 
 */
@ControllerAdvice
public class WebExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(WebExceptionHandler.class);
	
	@ExceptionHandler(NullPointerException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView nullpointer(Exception exception) {
		return setErrorView(exception);
	}
	
	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView runtime(Exception exception) {
		return setErrorView(exception);
	}

	private ModelAndView setErrorView(Exception exception) {
		log.error(exception.getMessage(), exception.getCause());
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.setViewName("error");
		return mav;
	}
}
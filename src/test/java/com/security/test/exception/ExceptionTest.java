package com.security.test.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.security.test.util.AbstractWebTest;

/**
 * @author Thiago
 *
 */

public class ExceptionTest extends AbstractWebTest {
	
	@Autowired private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	
	@Test
	public void testRunTime() throws Exception {
		mockMvc.perform(get("/runtime"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}
	
	@Test
	public void testNullPointer() throws Exception {
		mockMvc.perform(get("/nullpointer"))
				.andDo(print())
				.andExpect(status().isInternalServerError());
	}
}

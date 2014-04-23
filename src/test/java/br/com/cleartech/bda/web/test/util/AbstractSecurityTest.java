package br.com.cleartech.bda.web.test.util;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-security-test.xml", "classpath:spring-security-test.xml"})
@WebAppConfiguration
public abstract class AbstractSecurityTest {
	
}

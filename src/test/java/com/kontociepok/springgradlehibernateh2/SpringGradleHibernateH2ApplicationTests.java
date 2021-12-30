package com.kontociepok.springgradlehibernateh2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class SpringGradleHibernateH2ApplicationTests {

	@Autowired
	ApplicationContext context;

	@Test
	void contextLoads() {
		int x = 1;
	}

}

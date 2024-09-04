package com.example.healthboy;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HealthboyApplicationTests {

	@Autowired
	private ApplicationContext applicationContext;

	@Test
	void contextLoads() {
		// 애플리케이션 컨텍스트가 로드되었는지 확인
		assertThat(applicationContext).isNotNull();

		// Interceptor Load
		assertThat(applicationContext.containsBean("globalExceptionHandler")).isTrue();
		assertThat(applicationContext.containsBean("loggingInterceptor")).isTrue();
		assertThat(applicationContext.containsBean("jwtRequestInterceptor")).isTrue();
		assertThat(applicationContext.containsBean("routeBasedAuthInterceptor")).isTrue();

		// Controller Load
		assertThat(applicationContext.containsBean("healthController")).isTrue();
		assertThat(applicationContext.containsBean("authController")).isTrue();
		assertThat(applicationContext.containsBean("userController")).isTrue();
		assertThat(applicationContext.containsBean("scheduleController")).isTrue();
		assertThat(applicationContext.containsBean("timeBlockController")).isTrue();

		// Service Load
		assertThat(applicationContext.containsBean("userService")).isTrue();
		assertThat(applicationContext.containsBean("scheduleService")).isTrue();
		assertThat(applicationContext.containsBean("timeBlockService")).isTrue();

		// Repository Load
		assertThat(applicationContext.containsBean("userRepository")).isTrue();
		assertThat(applicationContext.containsBean("scheduleRepository")).isTrue();
		assertThat(applicationContext.containsBean("timeBlockRepository")).isTrue();
	}

}

package com.example.healthboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Remove after add auth process.
public class HealthboyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthboyApplication.class, args);
	}

}

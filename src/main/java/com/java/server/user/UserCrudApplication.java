package com.java.server.user;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class UserCrudApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(UserCrudApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);

		ConfigurableApplicationContext applicationContext = springApplication.run(args);
		applicationContext.isActive();
	}
}

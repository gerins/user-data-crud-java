package com.java.server.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserCrudApplication {

	public static void main(String[] args) {
		System.out.println("hello world");

		SpringApplication.run(UserCrudApplication.class, args);
	}

}

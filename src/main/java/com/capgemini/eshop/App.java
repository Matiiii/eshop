package com.capgemini.eshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "mysql");

		SpringApplication.run(App.class, args);
	}
}

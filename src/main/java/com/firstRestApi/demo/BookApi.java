package com.firstRestApi.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class BookApi {

	public static void main(String[] args) {
		SpringApplication.run(BookApi.class, args);
	}

	@Bean
	CommandLineRunner runner(Environment env) {
		return args -> {
			System.out.println("DB URL = " + env.getProperty("spring.datasource.url"));
			System.out.println("DB User = " + env.getProperty("spring.datasource.username"));
		};
	}
}
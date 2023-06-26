package com.msglearning.javabackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
@Configuration
public class RestServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestServiceApplication.class, args);
	}

	@GetMapping
	public String hello(){
		return "Hello";
	}

}

package com.mediator.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediatorDemoApplication {

	public static void main(String[] args) {
		IO.println("Starting Mediator Demo application...");
		SpringApplication.run(MediatorDemoApplication.class, args);
	}

}

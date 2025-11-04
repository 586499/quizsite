package com.quizsite.quiz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	@Autowired
	private EmailSenderService senderService; 
	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	
		
	}


package com.personal.MyUrlShortener;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootCmdApp implements CommandLineRunner {
	
	@Value("${key1}")
	private String key1;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootCmdApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("this is something");
		System.out.println("key1 is " + key1);
	}
	
	
}

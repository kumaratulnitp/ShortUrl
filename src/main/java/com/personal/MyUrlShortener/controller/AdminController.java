package com.personal.MyUrlShortener.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class AdminController {

	@GetMapping("/hello") 
	public String hello(@RequestParam("name") String name) {
		return "hello " + name;
	}
	
	@GetMapping("/redirect")
	public RedirectView redirect() {
		log.info("redirect called");
		return new RedirectView("hello?name=anonymous");
	}
}

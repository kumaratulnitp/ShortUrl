package com.personal.MyUrlShortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.personal.MyUrlShortener.pojo.ShortUrl;
import com.personal.MyUrlShortener.service.ShortUrlService;

@RestController
public class ShortUrlController {

	@Autowired 
	private ShortUrlService shortUrlService;
	
	@GetMapping("createShortUrl")
	@ResponseBody
	public ResponseEntity<String> createShortUrl(@RequestParam("completeUrl") String completeUrl,
			@RequestParam("userEmail") String email) {
		ShortUrl shortUrlObject = shortUrlService.createShortUrl(completeUrl, 0, email);
		ResponseEntity<String> response = null;
		if (shortUrlObject == null) {
			response = new ResponseEntity<String>("Request failed", HttpStatus.BAD_REQUEST);
		} else {
			response = new ResponseEntity<String>("http://localhost:8080/" + shortUrlObject.getNewUrl(), HttpStatus.ACCEPTED);
		}
		return response;
	}
	
	
	@GetMapping("/{newUrl}")
	public RedirectView redirectToCompleteUrl(@PathVariable("newUrl") String newUrl) {
		String completeUrl = shortUrlService.getUrl(newUrl);
		if (completeUrl != null) {
			return new RedirectView(completeUrl);
		}else {
			return null;
		}
	}
}

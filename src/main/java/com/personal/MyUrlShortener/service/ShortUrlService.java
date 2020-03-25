package com.personal.MyUrlShortener.service;

import com.personal.MyUrlShortener.pojo.ShortUrl;

public interface ShortUrlService {
	String getUrl(String newUrl);
	ShortUrl createShortUrl(String completeUrl, int expiryDays);
	ShortUrl createShortUrl(String completeUrl, int expiryDays, String userEmail);
}

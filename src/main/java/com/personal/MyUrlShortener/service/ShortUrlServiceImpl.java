package com.personal.MyUrlShortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.MyUrlShortener.dao.ShortUrlDao;
import com.personal.MyUrlShortener.dao.UserDao;
import com.personal.MyUrlShortener.pojo.ShortUrl;
import com.personal.MyUrlShortener.pojo.User;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

	private ShortUrlDao shortUrlDao;
	
	private UserDao userDao;
	
	private static final int DEFAULT_EXPIRY_DAYS = 30;
	
	@Autowired
	public ShortUrlServiceImpl(ShortUrlDao shortUrlDao, UserDao userDao) {
		this.shortUrlDao = shortUrlDao;
		this.userDao = userDao;
	}
	
	@Override
	public ShortUrl createShortUrl(String completeUrl, int expiryDays) {
		if (expiryDays == 0) {
			expiryDays = DEFAULT_EXPIRY_DAYS;
		}
		return createShortUrl(completeUrl, expiryDays, -1);
	}
	
	@Override
	public ShortUrl createShortUrl(String completeUrl, int expiryDays, String userEmail) {
		if (expiryDays == 0) {
			expiryDays = DEFAULT_EXPIRY_DAYS;
		}
		int userId = -1;
		if (userEmail != null ) {
			User user = userDao.getUser(userEmail);
			if (user !=null ) {
				userId = user.getId();
			}
		}
		return createShortUrl(completeUrl, expiryDays, userId);
	}
	
	private ShortUrl createShortUrl(String completeUrl, int expiryDays, int userId) {
		ShortUrl shortUrlObject = shortUrlDao.createUrl(completeUrl, expiryDays, -1);
		return shortUrlObject;
	}

	@Override
	public String getUrl(String newUrl) {
		return shortUrlDao.getCompleteUrl(newUrl);
	}

}

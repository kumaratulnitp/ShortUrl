package com.personal.MyUrlShortener.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.personal.MyUrlShortener.dao.CleanUpDao;
import com.personal.MyUrlShortener.dao.ShortUrlDao;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShortUrlCleaner {
	
	@Autowired
	private CleanUpDao cleanUpDao;
	
	@Autowired
	private ShortUrlDao shortUrlDao;
	
	
	/**
	 * Scheduled activity to run cleanup activity of removing expired urls
	 * This will run every 6000 milliseconds
	 */
	@Scheduled(fixedRate = 6000)
	public void cleanupShortUrls() {
		log.info("scheduled cleanup activity started");
		Date currentTime = new Date();
		List<String> urlCleanupList = cleanUpDao.getUrlsToCheckCleanup();
		urlCleanupList = shortUrlDao.deleteUrls(urlCleanupList, currentTime);
		cleanUpDao.updateFirstSlot(urlCleanupList);
		cleanUpDao.reconfigure();
		log.info("scheduled cleanup activity finished");
	}
	
}

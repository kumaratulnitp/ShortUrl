package com.personal.MyUrlShortener.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.personal.MyUrlShortener.pojo.ShortUrl;
import com.personal.MyUrlShortener.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShortUrlDao {
	
	private Map<String, ShortUrl> urls = new ConcurrentHashMap<String, ShortUrl>();
	
	private Calendar calendar = Calendar.getInstance();
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private CleanUpDao cleanUpDao;
	
	public ShortUrl createUrl(String completeUrl, int expiryMinutes, long userId) {
		log.info("create url completeUrl:{} expiryMinutes{} userId{}", completeUrl, expiryMinutes, userId);
		Date createdOn = new Date();
		calendar.setTime(createdOn);
		calendar.add(Calendar.MINUTE, expiryMinutes);
		Date expiryOn = calendar.getTime();
		String newUrl= getNewUrl(completeUrl);
		
		ShortUrl shortUrlObject = ShortUrl.builder()
				.completeUrl(completeUrl)
				.newUrl(newUrl)
				.createdOn(createdOn)
				.expiryOn(expiryOn)
				.userId(userId)
				.active(true)
				.build();
		
		setUrl(shortUrlObject);
		cleanUpDao.populateCleanupBucket(newUrl, expiryMinutes);
		return shortUrlObject;
	}
	
	public boolean deleteUrl(String newUrl, Date currentTime) {
		ShortUrl shortUrlObject = urls.get(newUrl);
		if (shortUrlObject == null) {
			log.info("shortUrlObject is null");
			return false;
		} else if (shortUrlObject.getExpiryOn().before(currentTime)) {
			log.info("shorturlobject cleaned up {}", shortUrlObject);
			urls.remove(newUrl);
			return true;
		}
		return false;
	}
	
	public List<String> deleteUrls(List<String> newUrls, Date currentTime) {
		log.info("deleteUrls");
		if (newUrls == null || newUrls.isEmpty()) {
			return null;
		}
		List<String> undeletedUrls = new ArrayList<>();
		for(String newUrl:newUrls) {
			boolean deleteStatus = deleteUrl(newUrl, currentTime);
			if (deleteStatus == false) {
				undeletedUrls.add(newUrl);
			}
		}
		return undeletedUrls;
	}

	public String getCompleteUrl(String newUrl) {
		ShortUrl shortUrlObject = urls.get(newUrl);
		return shortUrlObject == null ? null : shortUrlObject.getCompleteUrl();
	}
	
	private void setUrl(ShortUrl shortUrlObject) {
		urls.put(shortUrlObject.getNewUrl(), shortUrlObject);
		log.info("short urls {}", urls);
	}
	
	
	/**
	 * This function gets new short url for a complete url
	 * As long as collision occurs, it keeps on trying to find new base64 encoding by altering passed string
	 * @param completeUrl
	 * @return
	 */
	private String getNewUrl(String completeUrl) {
		String newUrl = commonUtil.getBase64EncodedString(completeUrl);
		int i = 0;
		while (urls.containsKey(newUrl)) {
			newUrl = commonUtil.getBase64EncodedString(completeUrl + i++);
		}
		return newUrl;
	}
	
}

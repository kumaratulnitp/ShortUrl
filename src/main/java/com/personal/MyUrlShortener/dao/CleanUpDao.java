package com.personal.MyUrlShortener.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author atulk
 * Keep a list of cleanup url candidates
 * Delete is called for first slot 
 * On Reconfigure of this list, first slot is checked for emptiness,
 * 	if found empty, second slot data is shifted to first slot
 */
@Component
public class CleanUpDao {
	
	private static final String FIRST_SLOT = "first" ;
	
	private static final String SECOND_SLOT = "second" ;
	
	private Map<String, List<String>> cleanUpBucket = new HashMap<String, List<String>>();
	
	public void populateCleanupBucket(String newUrl, int expiryDays) {
		if (expiryDays < 10) {
			List<String> urlList = cleanUpBucket.get(FIRST_SLOT);
			if (urlList == null) {
				urlList = new ArrayList<String>();
			}
			urlList.add(newUrl);
			cleanUpBucket.put(FIRST_SLOT, urlList);
		} else {
			List<String> urlList = cleanUpBucket.get(SECOND_SLOT);
			if (urlList == null) {
				urlList = new ArrayList<String>();
			}
			urlList.add(newUrl);
			cleanUpBucket.put(SECOND_SLOT, urlList);
		}
	}
	
	public void reconfigure() {
		List<String> firstBucket = cleanUpBucket.get(FIRST_SLOT);
		if (firstBucket == null || firstBucket.size() == 0) {
			List<String> secondBucket = cleanUpBucket.get(SECOND_SLOT);
			cleanUpBucket.put(FIRST_SLOT, secondBucket);
		}
	}
	
	public List<String> getUrlsToCheckCleanup() {
		return cleanUpBucket.get(FIRST_SLOT);
	}
	
	public void updateFirstSlot(List<String> firstList) {
		cleanUpBucket.put(FIRST_SLOT, firstList);
	}
}

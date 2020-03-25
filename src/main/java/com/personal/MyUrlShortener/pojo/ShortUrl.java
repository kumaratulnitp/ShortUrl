package com.personal.MyUrlShortener.pojo;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortUrl {
	private String completeUrl;
	private String newUrl;
	private Date createdOn;
	private Date expiryOn;
	private long userId;
	private boolean active;
	//TODO: add redirectCount
}

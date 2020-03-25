package com.personal.MyUrlShortener.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CommonUtil {
	
	private static final String ENCRYPT_ALGORITHM = "MD5";
	
	private MessageDigest md;
	
	public CommonUtil() {
		try {
			md = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getLocalizedMessage());
		} 
	}
	
	public String getBase64EncodedString(String completeUrl) {
        if (md == null) {
        	log.error("md not initialized");
        }
        byte[] myStringBytes = completeUrl.getBytes();
        byte[] myHash = md.digest(myStringBytes);
        Encoder base64 = Base64.getEncoder();
        String newString = base64.encodeToString(myHash).substring(0, 6);
        return newString;
	}
}

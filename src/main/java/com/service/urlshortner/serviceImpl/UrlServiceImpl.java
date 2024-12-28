package com.service.urlshortner.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.service.urlshortner.dto.UrlDto;
import com.service.urlshortner.entity.Url;
import com.service.urlshortner.repo.UrlRepo;
import com.service.urlshortner.service.UrlService;


@Service
public class UrlServiceImpl implements UrlService{
	
	
	@Autowired
	private UrlRepo urlRepo;

	@Override
	public Url generateShortLink(UrlDto urldto) {
		if(StringUtils.isNotEmpty(urldto.getUrl())) {
			String encodedUrl = encodeUrl(urldto.getUrl());
			
			Url url = new Url();
			url.setOriginalUrl(urldto.getUrl());
			url.setShortUrl(encodedUrl);
			url.setCreationDate(LocalDateTime.now());
			url.setExpirationDate(getExpirationDate(urldto.getExpirationDate(),url.getCreationDate()));
			
			Url urlToRet = persistShortLink(url);
			
			if(urlToRet != null) {
				return urlToRet;
			}
			return null;
		}
		return null;
	}

	private LocalDateTime getExpirationDate(String localDateTime, LocalDateTime creationDate) {
		if(StringUtils.isBlank(localDateTime)) {
			return creationDate.plusSeconds(60);
		}
		LocalDateTime date = LocalDateTime.parse(localDateTime);
		return date;
	}

	@SuppressWarnings("deprecation")
	private String encodeUrl(String url) {
		// TODO Auto-generated method stub
		String encodedUrl ="";
		LocalDateTime time = LocalDateTime.now();
		
		encodedUrl = Hashing.murmur3_32()
							.hashString(url.concat(time.toString()), StandardCharsets.UTF_8).toString();
		
		return encodedUrl;
	}

	@Override
	public Url persistShortLink(Url url) {
		return urlRepo.save(url);
	}

	@Override
	public Url getEncodedUrl(String url) {
		return urlRepo.findByShortLink(url);
	}

	@Override
	public void deleteUrl(Url url) {
		urlRepo.delete(url);
		
	}

}

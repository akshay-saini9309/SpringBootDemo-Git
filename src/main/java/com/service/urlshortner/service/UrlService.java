package com.service.urlshortner.service;

import org.springframework.stereotype.Service;

import com.service.urlshortner.dto.UrlDto;
import com.service.urlshortner.entity.Url;

@Service
public interface UrlService {

	public Url generateShortLink(UrlDto urldto);
	public Url persistShortLink(Url url);
	public Url getEncodedUrl(String url);
	public void deleteUrl(Url url);
	
}

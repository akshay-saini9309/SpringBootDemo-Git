package com.service.urlshortner.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.urlshortner.dto.UrlDto;
import com.service.urlshortner.dto.UrlErrorResponseDto;
import com.service.urlshortner.dto.UrlResponseDto;
import com.service.urlshortner.entity.Url;
import com.service.urlshortner.service.UrlService;

@RestController
@RequestMapping("/url")
public class UrlController {
	
	@Autowired
	private UrlService service;
	
	@PostMapping("/generate")
	public ResponseEntity<?> generateShortLink(@RequestBody UrlDto url) {
		Url urlToRet = service.generateShortLink(url);
		if(urlToRet != null) {
			UrlResponseDto dto = new UrlResponseDto();
			dto.setExpirationDate(urlToRet.getExpirationDate());
			dto.setOriginalUrl(urlToRet.getOriginalUrl());
			dto.setShortLink(urlToRet.getShortUrl());
			return new ResponseEntity<UrlResponseDto>(dto,HttpStatus.OK);
		}
		UrlErrorResponseDto dto = new UrlErrorResponseDto();
		dto.setErrorMessage("there is an error");
		dto.setStatus("404");
		return new ResponseEntity<UrlErrorResponseDto>(dto,HttpStatus.OK);
	}

}

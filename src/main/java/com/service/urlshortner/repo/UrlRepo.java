package com.service.urlshortner.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.urlshortner.entity.Url;

@Repository
public interface UrlRepo extends JpaRepository<Url, Long>{

	public Url findByShortLink(String shortLink);
}

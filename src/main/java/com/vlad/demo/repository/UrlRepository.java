package com.vlad.demo.repository;

import com.vlad.demo.Model.Urls;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Urls, Long>{
    Optional<Urls> findByShortUrl(String shortUrl);
    Optional<Urls> findByLongUrl(String longUrl);
}
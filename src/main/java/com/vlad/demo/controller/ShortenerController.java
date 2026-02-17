package com.vlad.demo.controller;

import com.vlad.demo.DTO.UrlRequestDTO;
import com.vlad.demo.DTO.UrlResponseDTO;
import com.vlad.demo.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;

@RestController
public class ShortenerController {
    @Value("${shortener.base-url}")
    private String baseUrl;

    UrlShortenerService service;

    public ShortenerController(UrlShortenerService service) {
        this.service = service;
    }

    @PostMapping("/api/shorten")
    public UrlResponseDTO shorten(@Valid @RequestBody UrlRequestDTO requestDTO){
        String longUrl = requestDTO.originalUrl();
        String shortKey = service.shortenUrl(longUrl);
        String fullShortUrl = baseUrl + shortKey;
        return new UrlResponseDTO(fullShortUrl, longUrl);
    }

    @GetMapping("/{key}")
    public ResponseEntity<Void> redirect(@PathVariable String key){
        String longUrl = service.getLongUrl(key)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "URL not found"));
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(longUrl)).build();
    }
}

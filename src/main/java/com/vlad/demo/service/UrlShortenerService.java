package com.vlad.demo.service;

import com.vlad.demo.model.Urls;
import com.vlad.demo.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlShortenerService {
    String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final UrlRepository urlRepository;

    public UrlShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String longUrl){
        longUrl = longUrl.trim().toLowerCase();

        if (!longUrl.startsWith("http://") && !longUrl.startsWith("https://")) {
            longUrl = "https://" + longUrl;
        }

        Optional<Urls> existing = urlRepository.findByLongUrl(longUrl);
        if(existing.isPresent()){
            return existing.get().getShortUrl();
        }

        String key = generateKey();

        Urls url = new Urls(longUrl, key);
        urlRepository.save(url);

        return key;
    }

    private String generateKey(){
        Random random = new Random();
        String key;
        do{
            StringBuilder shortKey = new StringBuilder();
            for(int i = 0; i < 6; i++){
                int index = random.nextInt(chars.length());
                shortKey.append(chars.charAt(index));
            }
            key = shortKey.toString();
        }while(urlRepository.findByShortUrl(key).isPresent());

        return key;
    }

    public Optional<String> getLongUrl(String key) {
        Optional<Urls> url = urlRepository.findByShortUrl(key);
        if(url.isPresent()) {
            return Optional.of(url.get().getLongUrl());
        } else {
            return Optional.empty();
        }
    }
}

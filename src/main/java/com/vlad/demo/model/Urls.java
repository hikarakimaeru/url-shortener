package com.vlad.demo.model;

import jakarta.persistence.*;

@Entity
public class Urls {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "urls_sequence"
    )
    @SequenceGenerator(
            name = "urls_sequence",
            sequenceName = "urls_id_seq",
            allocationSize = 1
    )
    private Long id;

    private String longUrl;
    private String shortUrl;

    public Urls(){}
    public Urls(String longUrl, String shortUrl){
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public void setLongUrl(String longKey) {
        this.longUrl = longKey;
    }

    public void setShortUrl(String shortKey) {
        this.shortUrl = shortKey;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }
}

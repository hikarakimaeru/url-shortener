package com.vlad.demo.service;

import com.vlad.demo.Model.Urls;
import com.vlad.demo.repository.UrlRepository;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlShortenerServiceTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlShortenerService service;

    @Test
    void shouldReturnExistingShortUrl_WhenUrlAlreadyExists() {
        String inputUrl = "google.com";
        String expectedNormalizedUrl = "https://google.com";
        String existingShortKey = "A1b2C3";
        Urls existingUrlEntity = new Urls(expectedNormalizedUrl, existingShortKey);

        when(urlRepository.findByLongUrl(expectedNormalizedUrl)).thenReturn(Optional.of(existingUrlEntity));

        String resultKey = service.shortenUrl(inputUrl);

        assertEquals(existingShortKey, resultKey, "Должен вернуться уже существующий ключ");

        verify(urlRepository, never()).save(any());
    }

    @Test
    void shouldGenerateNewKey_WhenUrlIsNew() {
        String newUrl = "https://new-site.com";

        when(urlRepository.findByLongUrl(newUrl)).thenReturn(Optional.empty());
        when(urlRepository.findByShortUrl(anyString())).thenReturn(Optional.empty());

        String resultKey = service.shortenUrl(newUrl);

        assertNotNull(resultKey, "Ключ не должен быть null");
        assertEquals(6, resultKey.length(), "Длина ключа должна быть ровно 6 символов");

        verify(urlRepository, times(1)).save(any(Urls.class));
    }

    @Test
    void shouldGiveLongUrlFromShortUrl(){
        String key = "fxiSpQ";
        String url = "https://hello.com";

        Urls entity = new Urls(url,key);
        Optional<Urls> existingUrlEntity = Optional.of(entity);

        when(urlRepository.findByShortUrl(key)).thenReturn(existingUrlEntity);

        Optional<String> longUrl = service.getLongUrl(key);

        assertNotNull(longUrl);
        assertTrue(longUrl.isPresent());
        assertEquals(existingUrlEntity.get().getLongUrl(), longUrl.get());
    }

    @Test
    void ShouldReturnEmptyWhenKeyDoesNotExist(){
        String key = "none";

        when(urlRepository.findByShortUrl(key)).thenReturn(Optional.empty());

        Optional<String> longUrl = service.getLongUrl(key);

        assertFalse(longUrl.isPresent());
    }
}
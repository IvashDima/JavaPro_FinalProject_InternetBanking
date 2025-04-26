package org.example.springbank.retrievers;

import org.example.springbank.json.Rate;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RateRetriever {

    private static final String URL =
            "http://data.fixer.io/api/latest?access_key=240ec6cbacfbd2d5e8b6cd4d11cf3510";

    @Cacheable("rates") // Redis
    public Rate getRate() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Rate> response = restTemplate.getForEntity(URL, Rate.class);
            return response.getBody();
        } catch (Exception ex) {
            return new Rate();
        }
    }
}

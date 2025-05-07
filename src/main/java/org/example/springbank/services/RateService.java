package org.example.springbank.services;

import org.example.springbank.json.Rate;
import org.example.springbank.models.ExchangeRate;
import org.example.springbank.repositories.ExchangeRateRepository;
import org.example.springbank.retrievers.RateRetriever;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class RateService {
    private final RateRetriever rateRetriever;
    private final ExchangeRateRepository exchangeRateRepository;

    public RateService(RateRetriever rateRetriever, ExchangeRateRepository exchangeRateRepository) {
        this.rateRetriever = rateRetriever;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public ExchangeRate getTodayRate() {
        LocalDate today = LocalDate.now();
        return exchangeRateRepository.findTopByOrderByCreatedAtDesc()
                .filter(rate -> rate.getCreatedAt().toLocalDate().equals(today))
                .orElseGet(() -> {
                    try {
                        Rate externalRate = rateRetriever.getRate();
                        ExchangeRate newRate = new ExchangeRate();
                        newRate.setEurToUah(externalRate.getRates().uah());
                        newRate.setEurToUsd(externalRate.getRates().usd());
                        return exchangeRateRepository.save(newRate);
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to fetch external rate", e);
                    }
                });
    }

    public ExchangeRate forceRefreshRate() {
        try {
            Rate externalRate = rateRetriever.getRate();
            ExchangeRate newRate = new ExchangeRate();
            newRate.setEurToUah(externalRate.getRates().uah());
            newRate.setEurToUsd(externalRate.getRates().usd());
            return exchangeRateRepository.save(newRate);
        } catch (Exception e) {
            throw new RuntimeException("Manual rate refresh failed", e);
        }
    }
}


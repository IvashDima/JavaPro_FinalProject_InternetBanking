package org.example.springbank.controllers;

import org.example.springbank.json.Rate;
import org.example.springbank.models.ExchangeRate;
import org.example.springbank.retrievers.RateRetriever;
import org.example.springbank.services.RateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @GetMapping("/rate")
    public ExchangeRate rate(HttpServletRequest request) { // Jackson
        return rateService.forceRefreshRate();
    }
}

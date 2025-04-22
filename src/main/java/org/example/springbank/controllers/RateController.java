package org.example.springbank.controllers;

import org.example.springbank.json.Rate;
import org.example.springbank.retrievers.RateRetriever;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RateController {
    private final RateRetriever rateRetriever;

    public RateController(RateRetriever rateRetriever) {
        this.rateRetriever = rateRetriever;
    }

    @GetMapping("/rate")
    public Rate rate(HttpServletRequest request) { // Jackson
        return rateRetriever.getRate();
    }
}

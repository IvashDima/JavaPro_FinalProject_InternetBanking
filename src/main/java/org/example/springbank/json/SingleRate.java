package org.example.springbank.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SingleRate(@JsonProperty("UAH") double uah, @JsonProperty("USD") double usd) {}

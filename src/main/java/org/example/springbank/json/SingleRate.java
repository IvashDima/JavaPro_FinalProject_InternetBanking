package org.example.springbank.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

public record SingleRate (
        @JsonProperty("UAH") double uah,
        @JsonProperty("USD") double usd
) {}

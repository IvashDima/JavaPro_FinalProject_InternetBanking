package org.example.springbank.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SingleRate {
    @JsonProperty("UAH")
    private double uah;

//    @JsonProperty("USD")
//    private double usd;
}

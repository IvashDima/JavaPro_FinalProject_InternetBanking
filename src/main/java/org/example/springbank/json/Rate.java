package org.example.springbank.json;

import lombok.Data;

@Data
public class Rate {
    private String date;
    private SingleRate rates;
}

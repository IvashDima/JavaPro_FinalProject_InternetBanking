package org.example.springbank.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ExchangeRates")
public class ExchangeRate extends BaseEntity {

    private double eurToUah;
    private double eurToUsd;

    public ExchangeRate(double eurToUah, double eurToUsd) {
        this.eurToUah = eurToUah;
        this.eurToUsd = eurToUsd;
    }
}

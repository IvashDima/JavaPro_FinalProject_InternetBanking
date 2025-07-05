package org.example.springbank.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.example.springbank.enums.CurrencyType;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "CurrencyRates")
public class CurrencyRate extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    @Column(nullable = false)
    private double rate;

    public CurrencyRate(CurrencyType currency, double rate) {
        this.rate = rate;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" + "currency='" + currency + "', " + "rate=" + rate + "}";
    }
}

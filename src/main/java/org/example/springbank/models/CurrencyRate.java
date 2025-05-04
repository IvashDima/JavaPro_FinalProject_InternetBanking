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
public class CurrencyRate extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    @Column(nullable = false)
    private double rate;

    public CurrencyRate(CurrencyType currency, double rate){
        this.rate = rate;
        this.currency = currency;
    }

    @Override
    public String toString(){
        return "CurrencyRate{" +
//                "id="+id+", " +
                "currency='"+currency+"', " +
                "rate="+rate+
                "}";
    }
}

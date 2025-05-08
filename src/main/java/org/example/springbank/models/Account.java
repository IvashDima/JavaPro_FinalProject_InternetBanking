package org.example.springbank.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.springbank.enums.CurrencyType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Accounts")
public class Account extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> sTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> rTransactions = new ArrayList<>();

    public Account(Client client, double balance, CurrencyType currency){
        this.client = client;
        this.balance = balance;
        this.currency = currency;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }
    public void withdraw(double amount) {
        this.balance -= amount;
    }

    @Override
    public String toString(){
        return "Account by " +
                "client '"+client.getName()+" "+client.getSurname()+"'"
                ;
    }
}

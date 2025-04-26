package org.example.springbank.models;

import org.example.springbank.enums.CurrencyType;

import javax.persistence.*;

@Entity
@Table(name = "Loans")
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private double balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyType currency;

    public Loan() {}

    public Loan(Client client, double balance, CurrencyType currency){
        this.client = client;
        this.balance = balance;
        this.currency = currency;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        this.balance += amount;
    }

    public void withdraw(double amount) {
        this.balance -= amount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    @Override
    public String toString(){
        return "Loan{" +
                "loanId="+id+", " +
                "client='"+client.getName()+" "+client.getSurname()+"'"+
              ", " +"balance="+balance+
              ", " +"currency="+currency+
                "}";
    }

}

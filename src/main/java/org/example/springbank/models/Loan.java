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

    public Loan(Client client, double balance, CurrencyType currency){
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
        return "Loan{" +
                "loanId="+id+", " +
                "client='"+client.getName()+" "+client.getSurname()+"'"+
              ", " +"balance="+balance+
              ", " +"currency="+currency+
                "}";
    }

}
